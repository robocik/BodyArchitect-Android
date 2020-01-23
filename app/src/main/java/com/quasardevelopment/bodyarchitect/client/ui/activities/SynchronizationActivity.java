package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.ObjectNotFoundException;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.OldDataException;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDaysHolder;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.OfflineModeManager;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.SynchronizationItem;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.SynchronizationItemsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAListView;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Functions;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.util.InstanceIdResolver;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.quasardevelopment.bodyarchitect.client.wcf.envelopes.WcfResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 22.07.13
 * Time: 07:03
 * To change this template use File | Settings | File Templates.
 */
public class SynchronizationActivity extends BANormalActivityBase {
    Spinner cmbMergeBehavior;
    BAListView lstListView;
    TextView tbEmptyMessage;
    Button btnSynchronize;
    SynchronizationItemsAdapter adapter;
    ProgressBar progress;
    private AsyncTask<Void, Void, WcfResult<Void>> synchronizationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_synchronization, null);
        setMainContent(inflate);

        getSupportActionBar().setSubtitle(R.string.synchronization_title);
        btnSynchronize= (Button) findViewById(R.id.btnSynchronize);
        lstListView= (BAListView) findViewById(R.id.lstListView);
        tbEmptyMessage= (TextView) findViewById(R.id.tbEmptyMessage);
        progress= (ProgressBar) findViewById(R.id.progress);
        cmbMergeBehavior= (Spinner) findViewById(R.id.cmbMergeBehavior);
        btnSynchronize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isInProgress())
                {
                    synchronizationTask.cancel(false);
                    synchronizationTask=null;
                }
                else
                {
                    btnSynchronize.setText(android.R.string.cancel);
                    synchronize(SynchronizationItem.MergeAction.values()[cmbMergeBehavior.getSelectedItemPosition()]);

                }

            }
        });

        createMergeBehavior();
    }

    boolean isInProgress()
    {
        return progress.getVisibility()==View.VISIBLE && synchronizationTask!=null;
    }

    private void synchronize(final SynchronizationItem.MergeAction mergeAction) {
        if(ApplicationState.getCurrent().isOffline)
        {
            BAMessageBox.ShowToast("Cannot synchronize in offline mode");
            return;
        }



        synchronizationTask=new AsyncTask<Void, Void, WcfResult<Void>>(){
            @Override
            protected void onPreExecute() {
                cmbMergeBehavior.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
            };
            @Override
            protected WcfResult<Void> doInBackground(Void... params) {
                WcfResult<Void> result = new WcfResult<Void>();
                try
                {
                    ArrayList<SynchronizationItem> items = adapter.getItems();
                    for(int i=0;i<items.size();i++)
                    {
                        if(isCancelled())
                        {
                            return result;
                        }
                        SynchronizationItem item=items.get(i);
                        item.State = SynchronizationItem.MergeState.Processing;
                        notifySyncGui(null);
                        save(item,true,mergeAction);
                        ApplicationState.getCurrent().SetModifiedFlag();
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    result.Exception=ex;
                }
                return result;

            }
            @Override
            protected void onPostExecute(WcfResult<Void> result)
            {
                cmbMergeBehavior.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                fill();
                if(result.Exception!=null)
                {
                    BAMessageBox.ShowToast(R.string.err_unhandled);
                }
            }
        }.execute();
    }

    protected  void save(final SynchronizationItem day, boolean firstTime,SynchronizationItem.MergeAction mergeAction)
    {
        if (day.Type.equals(day.Type.TrainingDay))
        {
            boolean shouldMerge = false;
            try
            {
                saveTrainingDay(day);
            }
            catch (OldDataException ex)
            {
                day.Day.setIsConflict( true);
                if (!mergeAction .equals(SynchronizationItem.MergeAction.InConflictSkip) && firstTime)
                {
                    //now we must get the copy from db and merge it
                    shouldMerge = true;
                    //return;
                }
                else
                {
                    day.State = SynchronizationItem.MergeState.Error;
                }

                //when we skip in confict or this is the second save attempt the we basically mark this item as IsConflict and leave it
            }
            catch(Exception ex)
            {
                day.State = SynchronizationItem.MergeState.Error;
            }
            notifySyncGui(null);
            if (shouldMerge)
            {
                merge(day,mergeAction);
            }
        }
        else
        {
            if (day.GPSEntry != null) //null is when this gps entry has been removed on the server
            {
                try
                {
                    uploadGPSCoordinates(day, firstTime);
                }
                catch (ObjectNotFoundException ex)
                {
                    day.State = SynchronizationItem.MergeState.Finished;
                    notifySyncGui(day);
                }
                catch (Exception ex)
                {
                    day.Day.setIsModified(true);
                    day.State = SynchronizationItem.MergeState.Error;
                    notifySyncGui(null);
                }

            }
            else
            {
                uploadGPSResult(null, day, firstTime);
            }
        }
    }

    void notifySyncGui(final SynchronizationItem itemToRemove)
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(itemToRemove!=null)
                {
                    adapter.remove(itemToRemove);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    void uploadGPSCoordinates(SynchronizationItem day, boolean firstTime) throws Exception {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService();
        GPSTrackerEntryDTO result=service.GPSCoordinatesOperation(day.GPSEntry.globalId, WS_Enums.GPSCoordinatesOperationType.UpdateCoordinates, day.GPSBag.getPoints());
        if (result != null)
        {
            result.instanceId = day.GPSEntry.instanceId;
            day.GPSEntry.globalId = result.globalId;
        }
        uploadGPSResult(result, day, firstTime);
    }

    protected void uploadGPSResult(final GPSTrackerEntryDTO res, final SynchronizationItem item,boolean firstTime)
    {
        if (res != null)
        {
            UUID customerId = item.Day.getTrainingDay().customerId;
            TrainingDaysHolder holder = ApplicationState.getCurrent().GetTrainingDayHolder(customerId);

            item.Day.getGpsCoordinates(res).IsSaved = true;

            item.State = SynchronizationItem.MergeState.Finished;

            TrainingDayInfo dayInCache= Helper.SingleOrDefault(filter(new Predicate<TrainingDayInfo>() {
                public boolean apply(TrainingDayInfo item) {
                    return filter(new Predicate<EntryObjectDTO>() {
                        public boolean apply(EntryObjectDTO entry) {
                            return entry.globalId.equals(res.globalId);
                        }
                    }, item.getTrainingDay().objects).size()==1;
                }
            }, holder.getTrainingDays().values()));

            dayInCache.Update(res);
        }
        item.State = SynchronizationItem.MergeState.Finished;
        notifySyncGui(item);
    }

    void merge(SynchronizationItem day,SynchronizationItem.MergeAction mergeAction)
    {
        try
        {
            MergeTrainingDayFromDb(day,mergeAction);
        }
        catch (Exception ex)
        {
            day.State = SynchronizationItem.MergeState.Error;
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    void MergeTrainingDayFromDb(SynchronizationItem item,SynchronizationItem.MergeAction mergeAction) throws Exception {
        WorkoutDayGetOperation data = new WorkoutDayGetOperation();
        data.workoutDateTime = item.Day.getTrainingDay().trainingDate;
        data.customerId = item.Day.getTrainingDay().customerId;
        data.operation = WS_Enums.GetOperation.Current;
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService();
        TrainingDayDTO trainingDay = service.GetTrainingDay(data, new RetrievingInfo());
        MergeResult(trainingDay, item,mergeAction);

    }
void MergeResult(TrainingDayDTO day, SynchronizationItem item,final SynchronizationItem.MergeAction mergeAction)
    {

        UUID customerId = item.Day.getTrainingDay().customerId;
        TrainingDaysHolder holder = ApplicationState.getCurrent().GetTrainingDayHolder(customerId);
        OfflineModeManager manager = new OfflineModeManager( ApplicationState.getCurrent().getMyDays(),ApplicationState.getCurrent().getSessionData().profile.globalId);
        ApplicationState.getCurrent().setTrainingDay(item.Day);

        manager.MergeNew(day, ApplicationState.getCurrent(),true,new Functions.IFunc1<OfflineModeManager.ModificationType, Boolean>() {
            @Override
            public Boolean Func(OfflineModeManager.ModificationType p)
            {
                return mergeAction.equals(SynchronizationItem.MergeAction.UseServer);
            }
        });

        if (holder.getTrainingDays().containsKey(ApplicationState.getCurrent().getTrainingDay().getTrainingDay().trainingDate) && holder.getTrainingDays().get(ApplicationState.getCurrent().getTrainingDay().getTrainingDay().trainingDate).isModified())
        {
            save(item, false,mergeAction);
        }
        else
        {
            item.State = SynchronizationItem.MergeState.Finished;
            notifySyncGui(null);
        }

        ApplicationState.getCurrent().setTrainingDay(null);
    }
    

    void saveTrainingDay(SynchronizationItem day) throws Exception {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService();
        SaveTrainingDayResult res = service.SaveTrainingDay(day.Day.getTrainingDay());

        applySave(res, day);
    }

    void applySave(SaveTrainingDayResult result,final SynchronizationItem item)
    {
        TrainingDayDTO newDay = result.trainingDay;
        UUID customerId = item.Day.getTrainingDay().customerId;
        TrainingDaysHolder holder = ApplicationState.getCurrent().GetTrainingDayHolder(customerId);
        TrainingDayInfo newInfo=null;
        if (newDay != null)
        {
            final TrainingDayInfo oldInfo = holder.getTrainingDays().get(newDay.trainingDate);
            InstanceIdResolver.FillInstanceId(newDay, oldInfo.getTrainingDay(), true);

            List<SynchronizationItem> gpsEntriesWithCoordinates= filter(new Predicate<SynchronizationItem>() {
                public boolean apply(SynchronizationItem item) {
                    return item.Day.equals(oldInfo)  && item.Type.equals(SynchronizationItem.ItemType.GPSCoordinates);
                }
            }, adapter.getItems());
            //now check if gps entry has been removed on the server and if yes remove also gps coordinates uploading
            for(final SynchronizationItem source: gpsEntriesWithCoordinates)
            {
                EntryObjectDTO oldEntry= Helper.FirstOrDefault(filter(new Predicate<EntryObjectDTO>() {
                    public boolean apply(EntryObjectDTO item) {
                        return item instanceof GPSTrackerEntryDTO && Helper.isEmpty(source.GPSEntry.globalId)  &&((GPSTrackerEntryDTO) item).globalId.equals(source.GPSEntry.globalId) || ((GPSTrackerEntryDTO)item).exercise.globalId.equals(source.GPSEntry.exercise.globalId) && ((GPSTrackerEntryDTO) item).startDateTime.equals(source.GPSEntry.startDateTime) && ((GPSTrackerEntryDTO) item).endDateTime.equals(source.GPSEntry.endDateTime);
                    }
                }, newDay.objects));

//                var oldEntry = newDay.Objects.OfType< GPSTrackerEntryDTO >().Where(x => (source.GPSEntry.GlobalId!=Guid.Empty && x.GlobalId == source.GPSEntry.GlobalId) || (x.Exercise.GlobalId == source.GPSEntry.Exercise.GlobalId && x.StartDateTime == source.GPSEntry.StartDateTime && x.EndDateTime == source.GPSEntry.EndDateTime)).FirstOrDefault();
                if (oldEntry == null)
                {
                    source.GPSEntry = null;
                }
            }
            newInfo = holder.SetTrainingDay(newDay);
            newInfo.setIsModified(false);
        }
        else
        {
            holder.getTrainingDays().remove(item.Day.getTrainingDay().trainingDate);
        }
        item.Day.setIsConflict(false);
        item.Day = newInfo;
        item.State = SynchronizationItem.MergeState.Finished;
        notifySyncGui(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fill();
    }

    private void fill() {
        ArrayList<TrainingDayInfo> itemsToSync = ApplicationState.getCurrent().getLocalModified();
        btnSynchronize.setText(R.string.synchronization_button_sync);
        if(itemsToSync.size()==0)
        {
            tbEmptyMessage.setVisibility(View.VISIBLE);
            lstListView.setVisibility(View.GONE);
            btnSynchronize.setEnabled(false);
        }
        else
        {
            btnSynchronize.setEnabled(true);
            if(adapter==null)
            {
                adapter = new SynchronizationItemsAdapter(this, R.id.tbDateTime,itemsToSync);
                lstListView.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();
            tbEmptyMessage.setVisibility(View.GONE);
        }
    }

    private void createMergeBehavior() {
        final String array_spinner[]=new String[3];
        array_spinner[0]=getString(R.string.synchronization_mode_skip_conflict);
        array_spinner[1]= getString(R.string.synchronization_mode_server_conflict);
        array_spinner[2]= getString(R.string.synchronization_mode_local_conflict);

        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbMergeBehavior.setAdapter(adapter);
        cmbMergeBehavior.setSelection(0);
    }
}
