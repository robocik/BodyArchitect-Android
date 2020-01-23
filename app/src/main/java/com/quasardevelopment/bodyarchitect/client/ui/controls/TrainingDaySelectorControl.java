package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.KeyType;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.activities.GPSTrackerActivity;
import com.quasardevelopment.bodyarchitect.client.ui.activities.MeasurementsActivity;
import com.quasardevelopment.bodyarchitect.client.ui.activities.StrengthTrainingActivity;
import com.quasardevelopment.bodyarchitect.client.ui.activities.SupplementsActivity;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.Functions;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import org.joda.time.DateTime;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 17.04.13
 * Time: 09:51
 * To change this template use File | Settings | File Templates.
 */
public class TrainingDaySelectorControl extends LinearLayout implements  View.OnCreateContextMenuListener {
    private final LinearLayout progressPane;
    private final TextView tbProgress;
    private DateTime currentDate;
    LinearLayout pnlSyncNeeded;
    TableLayout pnlMain;
    LinearLayout  pnlContent;
    Functions.IAction onTrainingDayRetrieved;
    TileButton btnGpsButton;

    public TrainingDaySelectorControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.training_day_selector_control, this);

        pnlSyncNeeded=(LinearLayout)findViewById(R.id.pnlSyncNeeded);
        pnlMain= (TableLayout)findViewById(R.id.pnlMain);
        progressPane = (LinearLayout)findViewById(R.id.progressPane);
        tbProgress = (TextView)findViewById(R.id.tbProgress);
        pnlContent= (LinearLayout) findViewById(R.id.contentPane);
    }


    public void setOnTrainingDayRetrieved(Functions.IAction callback)
    {
         onTrainingDayRetrieved=callback;
    }
    public void Fill(DateTime date)
    {
        isOpening=false;
        currentDate = date;
        retrieveEntries(DateTimeHelper.ToMonth(date));
        //fillToday();


        fillToday();
    }

    void showProgress(boolean show)
    {
         if(show)
        {
            progressPane.setVisibility(View.VISIBLE);
            pnlContent.setVisibility(View.GONE);
            tbProgress.setText(R.string.progress_retrieving_entries);
        }
        else
        {
            progressPane.setVisibility(View.GONE);
            pnlContent.setVisibility(View.VISIBLE);
        }
    }

    private void retrieveEntries(DateTime monthDate)
    {
        if (!ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isMonthLoaded(monthDate) && !ApplicationState.getCurrent().isOffline)
        {
            getCurrentTrainingDays(monthDate);

        }
    }
    private void getCurrentTrainingDays(DateTime monthDate)
    {
//        pnlMain.ShowProgress(true, ApplicationStrings.TrainingDaySelectorControl_ProgressRetrieveEntries, true, false);

        showProgress(true);

        this.setEnabled(false);
        ApplicationState.getCurrent().RetrieveMonthAsync(monthDate, ApplicationState.getCurrent().getCurrentBrowsingTrainingDays(),new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {
            }

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data) {
                fillToday();
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
            }

            @Override
            public void Wsdl2CodeEndedRequest() {
                showProgress(false);
                setEnabled(true);
                if(onTrainingDayRetrieved!=null)
                {
                    onTrainingDayRetrieved.Action();
                }
            }
        });


    }

    void cleanUp()
    {
        pnlMain.removeAllViews();
    }


    void fillToday()
    {
        cleanUp();
        if (ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().containsKey(currentDate))
        {
            TrainingDayInfo info = ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(currentDate);

            fillTrainingDay(info.getTrainingDay());
            pnlSyncNeeded.setVisibility(info.isModified()?VISIBLE:GONE);
        }
        else
        {
            pnlSyncNeeded.setVisibility(GONE);
            if (ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isMine())
            {
                fillEmptyBoxes(null);
            }
        }
    }

    void fillTrainingDay(TrainingDayDTO day)
    {

        for (EntryObjectDTO entryObjectDto : day.objects)
        {
            if (isSupported(entryObjectDto))
            {
                addButton(entryObjectDto.getClass(),entryObjectDto);

            }
        }
        if (day.isMine())
        {
            fillEmptyBoxes(day);
        }

    }

    private void fillEmptyBoxes(TrainingDayDTO day) {


        if (day==null || filter(new Predicate<EntryObjectDTO>() {
                                            public boolean apply(EntryObjectDTO item) {
                                                return item.getClass().equals(StrengthTrainingEntryDTO.class);
                                            } }, day.objects) .size() == 0)
        {
            addButton(StrengthTrainingEntryDTO.class);
        }

        if (day == null || filter(new Predicate<EntryObjectDTO>() {
            public boolean apply(EntryObjectDTO item) {
                return item.getClass().equals(SizeEntryDTO.class);
            } }, day.objects) .size() == 0)
        {
            addButton(SizeEntryDTO.class);
        }

        if (day == null || filter(new Predicate<EntryObjectDTO>() {
                                public boolean apply(EntryObjectDTO item) {
                                    return item.getClass().equals(SuplementsEntryDTO.class);
                                } }, day.objects) .size() == 0)
        {
            addButton(SuplementsEntryDTO.class);
        }

        if (day == null || filter(new Predicate<EntryObjectDTO>() {
                            public boolean apply(EntryObjectDTO item) {
                                return item.getClass().equals(GPSTrackerEntryDTO.class);
                            } }, day.objects) .size() == 0)
        {
            addButton(GPSTrackerEntryDTO.class);
        }
    }

    private void addButton(Class entryObjectType)
    {
        addButton(entryObjectType,null);
    }
    private void addButton(Class entryObjectType,EntryObjectDTO entryObjectDto)
    {
//        Button btn = new Button(this.getContext());
//        btn.setText(entryObjectDto.getClass().getName());
//        btn.setBackgroundColor(0xFF00FF00);
//        addView(btn);
        //TileButton btn = new TileButton(getContext());
        //android:background="?android:selectableItemBackground"
        TileButton btn = new TileButton(getContext());

        btn.setTag(entryObjectDto);
        //btn.setImageDrawable(btn.getResources().getDrawable(R.drawable.strength_training_tile));
        //btn.setBackgroundResource(0);
//        btn.setBackgroundColor(Color.GRAY);
       // btn.setScaleType(ImageView.ScaleType.FIT_XY);
        btn.setOnCreateContextMenuListener(this);
        if (prepareButton(btn, entryObjectType))
        {
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    tileButtonClick(view,false);
                }
            });

            int height = getResources().getDimensionPixelSize(R.dimen.tilebutton);

            TableRow.LayoutParams vp =new TableRow.LayoutParams(height,height);
            int margin= getResources().getDimensionPixelSize(R.dimen.tilebutton_margin);
            vp.setMargins(margin,margin,margin,margin);
            btn.setLayoutParams(vp);

            int count=pnlMain.getChildCount();
            TableRow row2 =null;

            if(count==0)
            {
                row2 = new TableRow(this.getContext());
                pnlMain.addView(row2);
            }
            else
            {
                row2=(TableRow)pnlMain .getChildAt(count-1);

            }

            int currentOrientation=getResources().getConfiguration().orientation;

            if(currentOrientation== Configuration.ORIENTATION_PORTRAIT && row2.getChildCount()>=2)
            {
                row2 = new TableRow(this.getContext());

                pnlMain.addView(row2);
            }
            row2.addView(btn,vp);

            if (entryObjectType== GPSTrackerEntryDTO.class) {
                btnGpsButton=btn;
            }

        }


    }

    //isOpening is used to prevent clicking very very fast on tile buttons (second click is performed before the first click show a new activity). In this case we got exceptions
    boolean isOpening;

    private void tileButtonClick(View view,boolean forceNew) {
        try {
            if(isOpening)
            {
                return;
            }
            isOpening=true;
            openEntry(view, forceNew,false);
        } catch (Exception e) {
            e.printStackTrace();
            isOpening=false;
            BAMessageBox.ShowToast(R.string.trainingday_selector_err_create_entry);
        }
    }

    public void openGps(boolean checkRuntimePermissions)
    {
        try {
            openEntry(btnGpsButton,false,checkRuntimePermissions);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    private void openEntry(View view,boolean forceNew,boolean checkRuntimePermissions) throws InstantiationException, IllegalAccessException {
        Object obj=view.getTag();
        PrepareTrainingDay();
        if (obj instanceof EntryObjectDTO && !forceNew)
        {//open existing entry

            ApplicationState.getCurrent().CurrentEntryId = new LocalObjectKey(((EntryObjectDTO)obj));
        }
        else
        {
            addNewEntry(obj);
        }
        GoToPage(obj, (Activity) getContext(),checkRuntimePermissions);
    }



    static Class getEntryType(Object tag)
    {
        if (tag instanceof EntryObjectDTO)
        {
            return tag.getClass();
        }
        else
        {
            return (Class)tag;
        }
    }
/*
    */
    public final static int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 10;

    public static void GoToPage(Object tag,Activity ctx,boolean checkRuntimePermissions)
    {
        Class entryType = getEntryType(tag);
        Intent intent = null;
        if (entryType.equals(StrengthTrainingEntryDTO.class))
        {
            intent=new Intent(ctx, StrengthTrainingActivity.class)  ;
//            this.GetParent<PhoneApplicationPage>().Navigate("/Pages/StrengthWorkoutPage.xaml");
        }
        else if (entryType.equals(SuplementsEntryDTO.class))
        {
            intent=new Intent(ctx, SupplementsActivity.class)  ;
//            this.GetParent<PhoneApplicationPage>().Navigate("/Pages/SupplementsPage.xaml");
        }
        else if (entryType.equals(SizeEntryDTO.class))
        {
            intent=new Intent(ctx, MeasurementsActivity.class)  ;
//            this.GetParent<PhoneApplicationPage>().Navigate("/Pages/MeasurementsPage.xaml");
        }
        else if (entryType.equals(GPSTrackerEntryDTO.class))
        {
            if(checkRuntimePermissions)
            {
                boolean res=ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED;
                if (!res) {
                    ActivityCompat.requestPermissions(ctx,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                    );
                    return;
                }
            }

            intent=new Intent(ctx, GPSTrackerActivity.class)  ;
//            this.GetParent<PhoneApplicationPage>().Navigate("/Pages/GPSTrackerPage.xaml");
        }
        else if (entryType.equals(BlogEntryDTO.class))
        {
//            this.GetParent<PhoneApplicationPage>().Navigate("/Pages/BlogPage.xaml");
        }

        ctx.startActivity(intent);
    }


    private void addNewEntry(Object tag) throws IllegalAccessException, InstantiationException {
        EntryObjectDTO newEntry = (EntryObjectDTO) getEntryType(tag).newInstance();
        ApplicationState.getCurrent().getTrainingDay().getTrainingDay().objects.add(newEntry);
        newEntry.trainingDay = ApplicationState.getCurrent().getTrainingDay().getTrainingDay();
        fillNewEntry(newEntry);
        ApplicationState.getCurrent().CurrentEntryId = new LocalObjectKey(newEntry.instanceId, KeyType.InstanceId);
    }

    private void fillNewEntry(EntryObjectDTO newEntry)
    {
        StrengthTrainingEntryDTO strength = newEntry instanceof StrengthTrainingEntryDTO?(StrengthTrainingEntryDTO)newEntry:null;
        SizeEntryDTO size = newEntry instanceof SizeEntryDTO?(SizeEntryDTO)newEntry:null;
        SuplementsEntryDTO supple = newEntry instanceof SuplementsEntryDTO?(SuplementsEntryDTO)newEntry:null;
        BlogEntryDTO blog = newEntry instanceof BlogEntryDTO?(BlogEntryDTO)newEntry:null;
        GPSTrackerEntryDTO gps = newEntry instanceof GPSTrackerEntryDTO?(GPSTrackerEntryDTO)newEntry:null;

        if (strength!=null)
        {
            strength.startTime = DateTimeHelper.removeTimeZone(DateTime.now());
        }
        else if (size!=null)
        {
            size.wymiary = new WymiaryDTO();
            size.wymiary.time.dateTime = DateTimeHelper.removeTimeZone(DateTime.now());
        }
        else if (supple!=null)
        {

        }
        else if (blog!=null)
        {
//            #if DEBUG
//            throw new Exception("Blog cannot be created in WP7");
//            #endif
        }
        else if (gps!=null)
        {

        }
    }

    boolean prepareButton(TileButton button, Class obj)
    {
//        var hub = (HubTile)button.Content;
        if (button.getTag() == null)
        {
            //button.setAlpha(0.4f); in API 11 and above
            button.setIsEmpty(true);
            button.setTag(obj);
        }


        if (obj.equals(StrengthTrainingEntryDTO.class))
        {
            button.setImageResource(R.drawable.strength_training_tile);
            //hub.Title = ApplicationStrings.TrainingDaySelectorControl_StrengthTraining;
            //if (button.Tag != null)
            {
                //hub.Background = EntryObjectColors.StrengthTraining;
                //hub.Message = getStrengthTrainingMessage((StrengthTrainingEntryDTO) button.Tag);
            }
        }else if (obj== SizeEntryDTO.class)
        {
            button.setImageResource(R.drawable.sizes_tile);
//            hub.Title = ApplicationStrings.TrainingDaySelectorControl_Measurements;
//            if (button.Tag != null)
//            {
//                hub.Background = EntryObjectColors.Measurements;
//                hub.Message = getMeasurementsMessage((SizeEntryDTO) button.Tag);
//            }
        }else if (obj== BlogEntryDTO.class)
        {
            button.setImageResource(R.drawable.blog_tile);
//            hub.Title = ApplicationStrings.TrainingDaySelectorControl_Blog;
//            if (button.Tag != null)
//            {
//                hub.Background = EntryObjectColors.Blog;
//                hub.Message = getBlogMessage((BlogEntryDTO) button.Tag);
//            }
        }else if (obj== SuplementsEntryDTO.class)
        {
            button.setImageResource(R.drawable.supple_tile);
//            hub.Title = ApplicationStrings.TrainingDaySelectorControl_Supplements;
//            if (button.Tag != null)
//            {
//                hub.Background = EntryObjectColors.Supplements;
//                hub.Message = getSupplementsMessage((SuplementsEntryDTO) button.Tag);
//            }
        }else if (obj== GPSTrackerEntryDTO.class)
        {
            button.setImageResource(R.drawable.cardio_tile);
//            hub.Title = ApplicationStrings.TrainingDaySelectorControl_GPSTracker;
//            if (button.Tag != null)
//            {
//                hub.Background = EntryObjectColors.GPSTracker;
//                hub.Message = getGpsTrackerMessage((GPSTrackerEntryDTO)button.Tag);
//            }
        }
        else
        {
            return false;
        }
//        Object tag= button.getTag();
//        if (tag instanceof EntryObjectDTO && ((EntryObjectDTO)tag).status == WS_Enums.EntryObjectStatus.Planned)
//        {
//            //button.setAlpha(0.7f);
//            button.setAlpha(200);
//        }
        return true;
    }

    boolean isSupported(EntryObjectDTO obj)
    {
        return !(obj instanceof A6WEntryDTO);
    }

    public void PrepareTrainingDay()
    {
        if (ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isMine())
        {
//            boolean crash = ApplicationState.getCurrent().Crash;
//            ApplicationState.getCurrent().Crash = false;
            //final boolean[] result = new boolean[1];
            //try to restore crashed entry only if user is opening exactly the same day (compare date)
//            if (ApplicationState.getCurrent().getTrainingDay() != null &&/* crash &&*/ currentDate == ApplicationState.getCurrent().getTrainingDay().getTrainingDay().trainingDate)
//            {
//                BAMessageBox.Ask(R.string.training_day_selector_control_qcrash_saver_restore_entry, new Functions.IAction() {
//                    @Override
//                    public void Action() {
//                        result[0]=true;
//                    } },this.getContext());
//
//                if(result[0])
//                {
//                    return;
//                }
//
//            }
            ApplicationState.getCurrent().timerStartTime=null;
            if (!ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().containsKey(currentDate))
            {
                TrainingDayDTO day = new TrainingDayDTO();
                day.trainingDate = currentDate;
                day.profileId = ApplicationState.getCurrent().getSessionData().profile.globalId;
                if (ApplicationState.getCurrent().CurrentViewCustomer != null)
                {
                    day.customerId = ApplicationState.getCurrent().CurrentViewCustomer.globalId;
                }
                ApplicationState.getCurrent().setTrainingDay(new TrainingDayInfo(day));
                return;
            }
        }

        if (ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().containsKey(currentDate))
        {
            TrainingDayInfo copy=Helper.Copy(ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(currentDate))   ;
            ApplicationState.getCurrent().setTrainingDay(copy);
        }
        else
        {
            //BAMessageBox.ShowError("Nie powinien tutaj wejsc. PrepareTrainingDay");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        if(ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isMine())
        {
            MenuItem item = contextMenu.add(R.string.trainingday_selector_menu_add_entry);
            final View btn=(View)view;
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    tileButtonClick(btn,true);
                    return true;
                }
            });
        }
    }
}
