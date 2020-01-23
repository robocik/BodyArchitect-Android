package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.accounts.NetworkErrorException;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.LicenceException;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.OldDataException;
import com.quasardevelopment.bodyarchitect.client.model.Exceptions.ValidationException;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.OfflineModeManager;
import com.quasardevelopment.bodyarchitect.client.util.*;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.quasardevelopment.bodyarchitect.client.wcf.envelopes.WcfResult;
import com.splunk.mint.Mint;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 24.04.13
 * Time: 07:44
 * To change this template use File | Settings | File Templates.
 */
public abstract class EntryObjectActivityBase  extends SimpleEntryObjectActivityBase
{
    ProgressDialog progressDlg;
    boolean isClosing;
    android.view.MenuItem mnuSave;
    android.view.MenuItem mnuDelete;
    TabHost tabHost;
    android.view.MenuItem mnuRefresh;

    public void Connect(TabHost tab)
    {
        tabHost=tab;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(ApplicationState.getCurrent().getTrainingDay()!=null)
        {
            show(false);
        }
    }

    protected abstract void show(boolean reload);

    protected void ValidateBeforeSave(Functions.IAction1<Boolean> validationResult )
    {
        validationResult.Action(true);
    }

    private void saveToDb()
    {
        saveToDb(false);
    }

    private void saveToDb(final boolean goBack)
    {
        if(ApplicationState.getCurrent()==null)
        {
            return;
        }
        if(!getEditMode())
        {
            BAMessageBox.ShowError(R.string.err_unhandled,this);
            return;
        }
        ValidateBeforeSave(new Functions.IAction1<Boolean>() {
            @Override
            public void Action(Boolean isValid) {
                if(isValid)
                {
                    doSaveToDb(goBack);
                }
            }
        });

    }

    protected void BeforeSaving() {

    }

    protected void SavingCompleted() throws Exception {

    }

    private void doSaveToDb(final boolean goBack) {
        showProgress(true,R.string.progress_saving);

        BeforeSaving();
        final DateTime date= ApplicationState.getCurrent().getTrainingDay().getTrainingDay().trainingDate;
        new Thread(new Runnable() {
            @Override
            public void run() {
                TrainingDayInfo copy= Helper.Copy(ApplicationState.getCurrent().getTrainingDay());
                copy.setIsModified(true);
                ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().put(copy.getTrainingDay().trainingDate, copy);
                ApplicationState.getCurrent().SaveState(false);
            }
        }).start();


        new AsyncTask<Void, Void, WcfResult<SaveTrainingDayResult>>(){
            @Override
            protected void onPreExecute() {

            };
            @Override
            protected WcfResult<SaveTrainingDayResult> doInBackground(Void... params) {
                BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService();

                WcfResult<SaveTrainingDayResult> res = new WcfResult<SaveTrainingDayResult>();
                res.Tag=getEntry();

                try
                {
                    SaveTrainingDayResult result=service.SaveTrainingDay(ApplicationState.getCurrent().getTrainingDay().getTrainingDay());
                    if(result.trainingDay!=null)
                    {
                        TrainingDayDTO savedDay=result.trainingDay;
                        InstanceIdResolver.FillInstanceId(savedDay, ApplicationState.getCurrent().getTrainingDay().getTrainingDay());
                        savedDay.trainingDate=date;
                        TrainingDayInfo tdi=ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(savedDay.trainingDate);
                        tdi.setTrainingDay(savedDay);
                        tdi.setIsModified(false);

                        ApplicationState.getCurrent().setTrainingDay(Helper.Copy(tdi));
                        //if we use instance id for determining current entry then after save we should always use globalid
                        EntryObjectDTO entryObj=getEntry();
                        if(entryObj!=null)
                        {
                            ApplicationState.getCurrent().CurrentEntryId = new LocalObjectKey(entryObj);
                        }

                        ApplicationState.getCurrent().SetModifiedFlag();
                        SavingCompleted();

                        res.Result=result;

                    }
                    else
                    {
                        if (!goBack)
                        {//user remove all data from the trainig day so on the server this day is removed. so on the client we "mark" this item as a new item
                            TrainingDayDTO day=ApplicationState.getCurrent().getTrainingDay().getTrainingDay();
                            day.globalId = Helper.emptyGuid();
                            EntryObjectDTO entryObj=getEntry();
//                            ApplicationState.getCurrent().getTrainingDay().getTrainingDay().globalId = Helper.emptyGuid();
                            entryObj.globalId = Helper.emptyGuid();
                            entryObj.version=0;
                            ApplicationState.getCurrent().CurrentEntryId = new LocalObjectKey(entryObj);
                        }
                        ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().remove(date);
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    res.Exception=ex;
                }
                return res;

            }
            @Override
            protected void onPostExecute(WcfResult<SaveTrainingDayResult> result)
            {
                showProgress(false,0);

                if(result.Exception==null)
                {
                    if(result.Result!=null && result.Result.newRecords.size()>0)
                    {
                        BAMessageBox.ShowToast(R.string.strength_training_activity_new_records);
                    }
                    if(ApplicationState.getCurrent().getTrainingDay()!=null && !goBack && !isClosing)
                    {
                        EntryObjectDTO entryBeforeSave= (EntryObjectDTO) result.Tag;
                        if (getEntry() == null && !ApplicationState.getCurrent().getTrainingDay().getTrainingDay().objects.contains(entryBeforeSave))
                        {//this is invoked when for example user saved empty supplements entry so on the server this entry is removed and therefore after save Entry returns null. so we must restore the entry before saving operation (this is a special case)
                            ApplicationState.getCurrent().getTrainingDay().getTrainingDay().objects.add(entryBeforeSave);
                            entryBeforeSave.trainingDay = ApplicationState.getCurrent().getTrainingDay().getTrainingDay();
                            entryBeforeSave.globalId = Helper.emptyGuid();
                            entryBeforeSave.version=0;
                        }
                        show(true);
                    }
                    if(goBack)
                    {
                        ApplicationState.getCurrent().setTrainingDay(null);
                        finish();
                    }
                }
                else
                {
                    if(isClosing)
                    {
                        return;
                    }
                    if(result.Exception instanceof LicenceException)
                    {
                        BAMessageBox.ShowToast(R.string.err_licence);
                        return;
                    }
                    if(result.Exception instanceof OldDataException)
                    {
                        BAMessageBox.ShowError(R.string.err_olddata,EntryObjectActivityBase.this);
                        return;
                    }
                    if(result.Exception instanceof ValidationException)
                    {
                        BAMessageBox.ShowValidationError((ValidationException)result.Exception);
                        return;
                    }
//                    if(ApplicationState.IsDebugMode)
//                    {
//                        BAMessageBox.ShowError(result.Exception.getMessage(),EntryObjectActivityBase.this);
//                    }
                    Mint.logException(result.Exception);
                    BAMessageBox.ShowError(R.string.err_save_training_day,EntryObjectActivityBase.this);
                }
            }
        }.execute();


    }

    protected boolean isModified()
    {
        if (ApplicationState.getCurrent() == null || ApplicationState.getCurrent().getTrainingDay() == null || !getEditMode())
        {
            return false;
        }
        TrainingDayInfo orgDay=ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(ApplicationState.getCurrent().getTrainingDay().getTrainingDay().trainingDate);
        if (orgDay == null)
        {
            return true;
        }

        return Helper.isModified(ApplicationState.getCurrent().getTrainingDay().getTrainingDay(),orgDay.getTrainingDay());
    }

    protected boolean BeforeClose()
    {
        return true;
    }

    protected void ReturnBack()
    {

    }
    @Override
    public void onBackPressed()
    {
        if(isModified())
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setTitle(R.string.html_app_name);
            dlgAlert.setMessage(R.string.confirm_exit);
            dlgAlert.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    if(BeforeClose())
                    {
                        ReturnBack();
                        isClosing=true;
                        ApplicationState.getCurrent().setTrainingDay(null);
                        finish();
                    }
                }
            });
            dlgAlert.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        else
        {
            super.onBackPressed();
        }

    }

    protected void showProgress(boolean show,int messageId)
    {
        if(show)
        {
            progressDlg= BAMessageBox.ShowProgressDlg(this, messageId);
        }
        else   if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    protected boolean canSave=true;

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if(getEditMode() && tabHost.getCurrentTab()!=2)
        {
            if(canSave)
            {
                mnuSave=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_save);
                mnuSave.setIcon(getResources().getDrawable(R.drawable.content_save)) ;
                MenuItemCompat.setShowAsAction(mnuSave,MenuItem.SHOW_AS_ACTION_ALWAYS);
//                mnuSave.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

            if(!ApplicationState.getCurrent().isOffline)
            {
                mnuDelete=menu.add(Menu.NONE,1,Menu.NONE,R.string.menu_delete);
                MenuItemCompat.setShowAsAction(mnuDelete,MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//                mnuDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            }
        }
        if(!ApplicationState.getCurrent().isOffline)
        {
            mnuRefresh=menu.add(Menu.NONE,1,Menu.NONE,R.string.menu_refresh);
            MenuItemCompat.setShowAsAction(mnuRefresh,MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//            mnuRefresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuSave)
        {
            saveToDb();
        }
        if(item==mnuRefresh)
        {
            refreshEntry();
        }
        if(item==mnuDelete)
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setTitle(R.string.html_app_name);
            dlgAlert.setMessage(R.string.entry_object_activity_no_previous_entries);
            dlgAlert.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    deleteEntry(getEntry());
                }
            });
            dlgAlert.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {}
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

        }
        return true;
    }

    private void refreshEntry()
    {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {}

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data)
            {
                final TrainingDayDTO result = (TrainingDayDTO) Data;
                if (ApplicationState.getCurrent().getTrainingDay() == null || isClosing)
                {
                    return;
                }

                if(getEditMode())
                {

                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(EntryObjectActivityBase.this);
                    dlgAlert.setTitle(R.string.html_app_name);
                    dlgAlert.setMessage(R.string.entry_object_activity_question_conflict);
                    dlgAlert.setPositiveButton(R.string.entry_object_activity_conflict_use_server,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            refreshEntryImplementation(result,true);
                        }
                    });
                    dlgAlert.setNegativeButton(R.string.entry_object_activity_conflict_use_local,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton)
                        {
                            refreshEntryImplementation(result,false);
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                else
                {
                    refreshEntryImplementation(result,true);
                }

            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                Mint.logException(ex);
                BAMessageBox.ShowToast(R.string.err_unhandled);
            }

            @Override
            public void Wsdl2CodeEndedRequest() {
                showProgress(false,0);
            }
        });
        WorkoutDayGetOperation data = new WorkoutDayGetOperation();
        data.workoutDateTime = ApplicationState.getCurrent().getTrainingDay().getTrainingDay().trainingDate;
        data.userId = ApplicationState.getCurrent().getTrainingDay().getTrainingDay().profileId;
        data.customerId = ApplicationState.getCurrent().getTrainingDay().getTrainingDay().customerId;
        data.operation = WS_Enums.GetOperation.Current;

        showProgress(true,R.string.progress_retrieving_entries);
        try {
            service.GetTrainingDayAsync(data, new RetrievingInfo());
        }
        catch(NetworkErrorException ex)
        {
            if (ApplicationState.getCurrent().isOffline)
            {
                BAMessageBox.ShowToast(R.string.err_offline_mode);
            }
            else
            {
                BAMessageBox.ShowToast(R.string.err_network_problem);
            }
        }
        catch (Exception ex)
        {
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }
    }

    void refreshEntryImplementation(TrainingDayDTO day,final boolean useServerVersion)
    {
        OfflineModeManager manager = new OfflineModeManager(ApplicationState.getCurrent().getMyDays(), ApplicationState.getCurrent().getSessionData().profile.globalId);
        manager.MergeNew(day, ApplicationState.getCurrent(),false,new Functions.IFunc1<OfflineModeManager.ModificationType, Boolean>() {
            @Override
            public Boolean Func(OfflineModeManager.ModificationType p)
            {
                return useServerVersion;
            }
        });
        ApplicationState.getCurrent().SetModifiedFlag();
        show(true);
    }

    public static boolean EnsureRemoveEntryTypeFromToday(final Class entryType)
    {
        DateTime today=DateTimeHelper.toDate(DateTime.now(DateTimeZone.UTC));
        if (ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().containsKey(today))
        {//if today already has training day object then we should use it
            TrainingDayInfo day = Helper.Copy(ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(today));


//            List<EntryObjectDTO> entriesWithTheSameType= filter(new Predicate<EntryObjectDTO>() {
//                public boolean apply(EntryObjectDTO item) {
//                    return !item.status.equals(WS_Enums.EntryObjectStatus.System) && item.getClass().equals(entryType) ;
//                }
//            }, day.getTrainingDay().objects);
//
//            if (entriesWithTheSameType.size()>0)
//            {
//                //inform user that he has the same type of entry in today and if he continue, then we add a new entry to today (without overwriting existing one)
//                if (BAMessageBox.Ask(ApplicationStrings.EntryObjectPageBase_QCopyToTodayOverwrite) == MessageBoxResult.Cancel)
//                {
//                    return false;
//                }
//
//            }
            ApplicationState.getCurrent().setTrainingDay(day);
        }
        else
        {
            ApplicationState.getCurrent().setTrainingDay(new TrainingDayInfo(new TrainingDayDTO()));
            ApplicationState.getCurrent().getTrainingDay().getTrainingDay().profileId = ApplicationState.getCurrent().getSessionData().profile.globalId;
            if(ApplicationState.getCurrent().CurrentViewCustomer!=null)
            {
                ApplicationState.getCurrent().getTrainingDay().getTrainingDay().customerId =ApplicationState.getCurrent().CurrentViewCustomer.globalId;
            }
            ApplicationState.getCurrent().getTrainingDay().getTrainingDay().trainingDate = today;
        }
        return true;
    }

    protected void deleteEntry(final EntryObjectDTO dto)
    {
        if (!dto.isNew())
        {
            //send request to the db only when we removed persisted entry
            ApplicationState.getCurrent().getTrainingDay().getTrainingDay().objects.remove(dto);
            ApplicationState.getCurrent().getTrainingDay().CleanUpGpsCoordinates();
            saveToDb(true);
        }
    else
    {
        if (getEditMode() && ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().containsKey(dto.trainingDay.trainingDate))
        {
            //this situation is when entry is added in offline mode
            TrainingDayInfo day = ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(dto.trainingDay.trainingDate);

            if(day.getTrainingDay().objects.size()==1)//only this entry
            {
                ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().remove(dto.trainingDay.trainingDate);
            }
            else
            {
                EntryObjectDTO itemToRemove =Helper.SingleOrDefault(filter(new Predicate<EntryObjectDTO>() {
                    public boolean apply(EntryObjectDTO item) {
                        return item.instanceId.equals(dto.instanceId);
                    }
                }, day.getTrainingDay().objects));
                if (itemToRemove != null)
                {
                    day.getTrainingDay().objects.remove(itemToRemove);
                }
                else
                {//don't know if this else is invoked. everything should be catch by if block
                    day.getTrainingDay().objects.remove(dto);
                }
                ApplicationState.getCurrent().getTrainingDay().CleanUpGpsCoordinates();

            }
            finish();
        }

    }
}

}
