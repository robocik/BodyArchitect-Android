package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.Manifest;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.content.*;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.GPSPoint;
import com.quasardevelopment.bodyarchitect.client.model.GPSPointsBag;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.controls.MoodButton;
import com.quasardevelopment.bodyarchitect.client.ui.controls.MoodChangedListener;
import com.quasardevelopment.bodyarchitect.client.ui.services.GPSTrackerService;
import com.quasardevelopment.bodyarchitect.client.util.*;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.ExerciseDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.GPSTrackerEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 10.07.13
 * Time: 07:19
 * To change this template use File | Settings | File Templates.
 *
 * http://stackoverflow.com/questions/6181704/good-way-of-getting-the-users-location-in-android
 */
public class GPSTrackerActivity extends EntryObjectActivityBase  {

    EditText txtComment;
    MoodButton ctrlMood;
    android.support.v7.widget.SwitchCompat tsReportStatus;

    GPSTrackerService gpsService;

    ImageView imgWeather;
    TextView tbSummaryHeader ;
    TextView tbSummaryWeatherLabel ;
    TextView tbSummaryTemperature ;
    TextView tbSummaryTemperatureType ;
    TextView tbSummaryDurationLabel;
    TextView tbSummaryDuration;
    TextView tbSummaryMaxSpeedLabel;
    TextView tbSummaryMaxSpeed;
    TextView tbSummaryMaxSpeedType;
    TextView tbSummaryAvgSpeedLabel;
    TextView tbSummaryAvgSpeed;
    TextView tbSummaryAvgSpeedType;
    TextView tbSummaryDistanceLabel;
    TextView tbSummaryDistance;
    TextView tbSummaryDistanceType;
    TextView tbSummaryAvgPaceLabel;
    TextView tbSummaryAvgPace;
    TextView tbSummaryAvgPaceType;
    TextView tbSummaryExercise;
    TextView tbSummaryCalories;
    TextView tbSummaryCaloriesLabel;

    private TextView tbHeader;
    ImageView imgGpsSignal;
    LinearLayout pnlWorkoutData;
    private TextView tbDuration;
    private TextView tbDistance;
    private TextView tbCalories;
    private TextView tbDistanceType;
    private TextView tbSpeed;
    private TextView tbSpeedType;


    Button btnChooseActivity;

    ImageButton btnStart;


    TabHost.TabSpec tpSummary;
    TabHost.TabSpec tpWorkout;
    TabHost.TabSpec tpInfo;

    private android.view.MenuItem mnuShowMap;
    private LocalBroadcastManager mBroadcastManager;
    private IntentFilter mBroadcastFilter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setSubtitle(R.string.gps_tracker_title);
        View inflate = getLayoutInflater().inflate(R.layout.activity_gps_tracker, null);
        setMainContent(inflate);

        createConnection();
        tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        tpWorkout=tabHost.newTabSpec(getString(R.string.gps_tracker_activity_header_workout));
        tpWorkout.setContent(R.id.tab_workout);
        tpWorkout.setIndicator(getString(R.string.gps_tracker_activity_header_workout));


        tpInfo=tabHost.newTabSpec(getString(R.string.gps_tracker_activity_header_info));
        tpInfo.setIndicator(getString(R.string.gps_tracker_activity_header_info));
        tpInfo.setContent(R.id.tab_info);

        tpSummary=tabHost.newTabSpec(getString(R.string.gps_tracker_activity_header_summary));
        tpSummary.setIndicator(getString(R.string.gps_tracker_activity_header_summary));
        tpSummary.setContent(R.id.tab_summary);

        tsReportStatus= (android.support.v7.widget.SwitchCompat) findViewById(R.id.entry_activity_report_status);
        tsReportStatus.setTextOn(getString(R.string.entry_object_activity_show_in_reports_show));
        tsReportStatus.setTextOff(getString(R.string.entry_object_activity_show_in_reports_hide));
        tsReportStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                getEntry().reportStatus=checked? WS_Enums.ReportStatus.ShowInReport: WS_Enums.ReportStatus.SkipInReport;
            } });
        txtComment= (EditText)findViewById(R.id.txtComment);
        txtComment.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String comment=s.toString();
                getEntry().comment= !TextUtils.isEmpty(comment)?comment:null;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        ctrlMood= (MoodButton) findViewById(R.id.ctrlMood);
        ctrlMood.addListener(new MoodChangedListener() {
            @Override
            public void moodChanged(WS_Enums.Mood mood) {
                ((GPSTrackerEntryDTO)getEntry()).mood = mood;
            }
        });
        tbSummaryHeader= (TextView)findViewById(R.id.tbSummaryHeader);
        tbSummaryTemperature= (TextView)findViewById(R.id.tbSummaryTemperature);
        tbSummaryTemperatureType= (TextView)findViewById(R.id.tbSummaryTemperatureType);
        tbSummaryWeatherLabel= (TextView)findViewById(R.id.tbSummaryWeatherLabel);
        imgWeather= (ImageView)findViewById(R.id.imgWeather);
        tbSummaryCalories = (TextView)findViewById(R.id.tbSummaryCalories);
        tbSummaryCaloriesLabel = (TextView)findViewById(R.id.tbSummaryCaloriesLabel);
        tbSummaryDuration = (TextView)findViewById(R.id.tbSummaryDuration);
        tbSummaryDurationLabel = (TextView)findViewById(R.id.tbSummaryDurationLabel);
        tbSummaryDistance = (TextView)findViewById(R.id.tbSummaryDistance);
        tbSummaryDistanceType = (TextView)findViewById(R.id.tbSummaryDistanceType);
        tbSummaryDistanceLabel = (TextView)findViewById(R.id.tbSummaryDistanceLabel);
        tbSummaryMaxSpeed = (TextView)findViewById(R.id.tbSummaryMaxSpeed);
        tbSummaryMaxSpeedType = (TextView)findViewById(R.id.tbSummaryMaxSpeedType);
        tbSummaryMaxSpeedLabel = (TextView)findViewById(R.id.tbSummaryMaxSpeedLabel);
        tbSummaryAvgSpeed = (TextView)findViewById(R.id.tbSummaryAvgSpeed);
        tbSummaryAvgSpeedType = (TextView)findViewById(R.id.tbSummaryAvgSpeedType);
        tbSummaryAvgSpeedLabel = (TextView)findViewById(R.id.tbSummaryAvgSpeedLabel);
        tbSummaryAvgPace = (TextView)findViewById(R.id.tbSummaryAvgPace);
        tbSummaryAvgPaceType = (TextView)findViewById(R.id.tbSummaryAvgPaceType);
        tbSummaryAvgPaceLabel = (TextView)findViewById(R.id.tbSummaryAvgPaceLabel);
        tbSummaryExercise = (TextView)findViewById(R.id.tbSummaryExercise);

        tbHeader = (TextView)findViewById(R.id.tbHeader);
        tbDuration = (TextView)findViewById(R.id.tbDuration);
        tbDistance = (TextView)findViewById(R.id.tbDistance);
        tbDistanceType = (TextView)findViewById(R.id.tbDistanceType);
        tbSpeed = (TextView)findViewById(R.id.tbSpeed);
        tbSpeedType = (TextView)findViewById(R.id.tbSpeedType);

        tbSpeedType.setText(EnumLocalizer.SpeedType());
        tbDistanceType.setText(EnumLocalizer.DistanceType());

        tbCalories = (TextView)findViewById(R.id.tbCalories);

        btnStart = (ImageButton) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gpsService.TimerHasBeenStarted  )
                {
                    if(!gpsService.isGPSReady())
                    {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(GPSTrackerActivity.this);
                        dialog.setMessage(R.string.gps_tracker_question_gps_not_ready);
                        dialog.setTitle(R.string.html_app_name);
                        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                gpsService.gpsStartStopImplementation();
                            }
                        });
                        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {}
                        });
                        dialog.show();

                        return;
                    }
                }
                gpsService.gpsStartStopImplementation();

            }
        });
        imgGpsSignal = (ImageView)findViewById(R.id.imgGpsSignal);
        pnlWorkoutData= (LinearLayout) findViewById(R.id.pnlWorkoutData);
        btnChooseActivity= (Button) findViewById(R.id.btnChooseActivity);
        btnChooseActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(GPSTrackerActivity.this, ExerciseSelectorActivity.class);
                myIntent.putExtra("ExerciseType", WS_Enums.ExerciseType.Cardio);
                startActivityForResult(myIntent, 1);
            }
        });

        Connect(tabHost);
        setReadOnly();

        mBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    void setReadOnly()
    {
        boolean readOnly=!getEditMode();
        txtComment.setEnabled(!readOnly);
        tsReportStatus.setClickable(!readOnly);
        ctrlMood.setEnabled(!readOnly);
    }

    void updateTime()
    {
        GPSTrackerEntryDTO entry=getEntry();
        if (entry.startDateTime == null)
        {
            tbDuration.setText(DateTimeHelper.fromSeconds(0));
//            tbTimer.Text = TimeSpan.Zero.ToString();
        }
        else
        {
            tbDuration.setText(DateTimeHelper.fromSeconds((int)(double)gpsService.getCurrentDuration()));

            if (entry.calories!=null)
            {
                tbCalories.setText(Helper.ToDisplayText(entry.calories));
            }
        }

        showCurrentGpsData();
    }

    //we show gps data every second but we add a point every 4 seconds
    private void showCurrentGpsData()
    {
        if (gpsService.isGPSReady())
        {
            GPSTrackerEntryDTO entry=getEntry();
            tbDistance.setText(Helper.ToDisplayDistanceText(entry.distance));
        }

    }

    private void showCurrentSpeed()
    {
        if (gpsService.getCurrentSpeed()!=null)
        {
            tbSpeed.setText(Helper.ToDisplaySpeedText((double) (float) gpsService.getCurrentSpeed()));
        }
        else
        {
            tbSpeed.setText("-"); //todo: change this to something else
        }

    }


    @Override
    protected void show(boolean reload) {
        ensureNewEntry();
    }

    void prepareUI()
    {
        GPSTrackerEntryDTO gpsEntry=getEntry();
        if(gpsEntry==null)
        {
            return;
        }
        btnChooseActivity.setText(gpsEntry.exercise!=null?gpsEntry.exercise.name:getString(R.string.gps_tracker_button_exercise));
        pnlWorkoutData.setVisibility(gpsEntry.exercise!=null?View.VISIBLE:View.GONE);

        btnStart.setImageDrawable(!gpsService.IsPause?getResources().getDrawable(R.drawable.av_stop):getResources().getDrawable(R.drawable.av_play));
        showCurrentSpeed();
        if(!gpsService.IsPause)
        {
            updateTime();
        }
        updateButtons();
        updateGpsSignal(gpsService.isGPSReady());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                UUID exerciseId=(UUID)data.getSerializableExtra("ItemId");
                ExerciseDTO exercise= ObjectsReposidory.getCache().getExercises().GetItem(exerciseId);
                GPSTrackerEntryDTO gpsEntry=getEntry();
                gpsEntry.exercise=exercise;

                if(gpsService!=null)
                {
                    prepareUI();
                }

                //addExercise(exercise);
            }
        }
//        else if(requestCode==GooglePlayServicesHelper.GOOGLE_PLAY_SERVICE_PROBLEM)
//        {
//            showMap();
//        }
    }//onActivityResult




    boolean ensureLocationService()
    {

        if(!isGpsAvailable() ){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.html_app_name);
            dialog.setMessage(R.string.gps_tracker_question_gps_disabled);
            dialog.setPositiveButton(R.string.gps_tracker_question_gps_disabled_button_change_settings, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                    startActivity(myIntent);
                }
            });
            dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    finish();
                }
            });
            dialog.show();
            return false;

        }
        return true;
    }

    public boolean isGpsAvailable()
    {
        LocationManager lm = null;
        if(lm==null)
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            boolean res= lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            return res;
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    protected boolean BeforeClose() {
        cleanUp();
        return true;
    }

    void cleanUp()
    {
        ActivitiesSettings.setGlobalTimerEnabled(false);
        //ApplicationState.getCurrent().isTimerEnabled = false;
        if(gpsService!=null)
        {
            unbindService(mConnection);
            gpsService=null;
        }
        if(updateViewReceiver!=null)
        {
            mBroadcastManager.unregisterReceiver(updateViewReceiver);
            updateViewReceiver=null;
        }

    }

    boolean isEmptyEntry()
    {
        final GPSTrackerEntryDTO entry=getEntry();
        GPSPointsBag existingCoordinates = ApplicationState.getCurrent().getTrainingDay().getGpsCoordinates(entry);
        boolean isAlreadySavedInLocal=ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isSaved(entry);
        boolean emptyEntry = entry.isNew() && !isAlreadySavedInLocal || (getEntry().status.equals(WS_Enums.EntryObjectStatus.Planned) && DateTimeHelper.isToday(getEntry().trainingDay.trainingDate) && existingCoordinates == null);
        return emptyEntry;
    }

    void ensureNewEntry()
    {
        final GPSTrackerEntryDTO entry=getEntry();

        boolean emptyEntry= isEmptyEntry();
        if (emptyEntry)
        {
            if(!ensureLocationService())
            {
                return;
            }

            GPSPointsBag existingCoordinates = ApplicationState.getCurrent().getTrainingDay().getGpsCoordinates(entry);
            if(existingCoordinates==null)
            {
                ApplicationState.getCurrent().getTrainingDay().SetGpsCoordinates(entry, new ArrayList<GPSPoint>(),false);
            }
            if(gpsService==null)
            {
                Intent service = new Intent(this, GPSTrackerService.class);
                bindService(service,mConnection,Context.BIND_AUTO_CREATE);

            }
            if(mBroadcastFilter==null )
            {
                mBroadcastFilter = new IntentFilter(Constants.RefreshViewFilter);
                mBroadcastManager.registerReceiver( updateViewReceiver, mBroadcastFilter);
            }


        }
        else
        {
            //when user press save after the training, he can no longer continue it so we can stop timer and gps
            cleanUp();
        }


        tabHost.clearAllTabs();
        if(emptyEntry)
        {
            tabHost.addTab(tpWorkout);
            tabHost.addTab(tpInfo);

            DateTimeFormatter formatter = DateTimeFormat.longDate();
            tbHeader.setText(entry.trainingDay.trainingDate.toString(formatter));

        }
        else
        {
            tabHost.addTab(tpSummary);
            tabHost.addTab(tpInfo);

            fillInfo();
        }



        txtComment.setText(entry.comment);
        tsReportStatus.setChecked(entry.reportStatus.equals(WS_Enums.ReportStatus.ShowInReport));
        ctrlMood.setMood(entry.mood);
    }

    private void fillInfo() {
        GPSTrackerEntryDTO entry=getEntry();

        DateTimeFormatter formatter = DateTimeFormat.longDate();
        tbSummaryHeader.setText(entry.trainingDay.trainingDate.toString(formatter));

        if(entry.hasDuration())
        {
            tbSummaryDuration.setText(DateTimeHelper.fromSeconds((int)(double)entry.duration));
        }
        else {
            tbSummaryDurationLabel.setVisibility(View.GONE);
            tbSummaryDuration.setVisibility(View.GONE);
        }
        if(entry.hasDistance())
        {
            tbSummaryDistance.setText(Helper.ToDisplayDistanceText(entry.distance));
            tbSummaryDistanceType.setText(EnumLocalizer.DistanceType());
        }
        else {
            tbSummaryDistance.setVisibility(View.GONE);
            tbSummaryDistanceType.setVisibility(View.GONE);
            tbSummaryDistanceLabel.setVisibility(View.GONE);
        }
        tbSummaryExercise.setText(entry.exercise.name);
        if(entry.calories!=null)
        {
            tbSummaryCalories.setText(Helper.ToDisplayText(entry.calories));
        }
        else {
            tbSummaryCaloriesLabel.setVisibility(View.GONE);
            tbSummaryCalories.setVisibility(View.GONE);
        }

        if(entry.hasMaxSpeed())
        {
            tbSummaryMaxSpeed.setText(Helper.ToDisplaySpeedText(entry.maxSpeed));
            tbSummaryMaxSpeedType.setText(EnumLocalizer.SpeedType());
        }
        else {
            tbSummaryMaxSpeed.setVisibility(View.GONE);
            tbSummaryMaxSpeedType.setVisibility(View.GONE);
            tbSummaryMaxSpeedLabel.setVisibility(View.GONE);
        }

        if(entry.hasAvgSpeed())
        {
            tbSummaryAvgSpeed.setText(Helper.ToDisplaySpeedText(entry.avgSpeed));
            tbSummaryAvgSpeedType.setText(EnumLocalizer.SpeedType());
            tbSummaryAvgPace.setText(WilksFormula.PaceToString( Helper.ToDisplayPace(entry.avgSpeed), true));
            tbSummaryAvgPaceType.setText(EnumLocalizer.PaceType());
        }
        else {
            tbSummaryAvgSpeed.setVisibility(View.GONE);
            tbSummaryAvgSpeedType.setVisibility(View.GONE);
            tbSummaryAvgSpeedLabel.setVisibility(View.GONE);
            tbSummaryAvgPace.setVisibility(View.GONE);
            tbSummaryAvgPaceType.setVisibility(View.GONE);
            tbSummaryAvgPaceLabel.setVisibility(View.GONE);
        }

        if(entry.hasWeather())
        {
            int imageResource = getResources().getIdentifier("drawable/"+WeatherIcon.GetIcon(entry.weather.condition), null, getPackageName());
            imgWeather.setImageDrawable(getResources().getDrawable(imageResource));
            tbSummaryTemperatureType.setText(EnumLocalizer.TemperatureType());
            if(entry.weather.temperature!=null)
            {
                tbSummaryTemperature.setText(Helper.ToDisplayText(entry.weather.temperature));
            }

        }
        else {
            tbSummaryTemperature.setVisibility(View.GONE);
            tbSummaryTemperatureType.setVisibility(View.GONE);
            imgWeather.setVisibility(View.GONE);
            tbSummaryWeatherLabel.setVisibility(View.GONE);
        }
    }

    private void updateGpsSignal(boolean gpsAvailable)
    {
        imgGpsSignal.setImageDrawable(gpsAvailable ? getResources().getDrawable(R.drawable.gps_signal) : getResources().getDrawable(R.drawable.gps_no_signal));
    }

    void updateButtons()
    {
        GPSTrackerEntryDTO entry=getEntry();
        canSave=entry.exercise != null && gpsService.IsPause && (!entry.isNew() || entry.hasDuration());
        supportInvalidateOptionsMenu();
    }


    @Override
    protected void SavingCompleted() throws Exception {
        final GPSTrackerEntryDTO entry=getEntry();
        if (entry==null || gpsService==null || gpsService.getPoints() == null || gpsService.getPoints().size() == 0)
        {
            return ;
        }

        //here GPSEntry should be saved so instead InstanceId we can use GlobalId
        ApplicationState.getCurrent().getTrainingDay().CleanUpGpsCoordinates();
        ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(entry.trainingDay.trainingDate).CleanUpGpsCoordinates();

        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService();
        GPSTrackerEntryDTO result=service.GPSCoordinatesOperation(entry.globalId, WS_Enums.GPSCoordinatesOperationType.UpdateCoordinatesWithCorrection,gpsService.getPoints());
        result.instanceId = entry.instanceId;
        TrainingDayInfo tdi = ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().get(entry.trainingDay.trainingDate);
        GPSPointsBag gpsBag = tdi.getGpsCoordinates(result);
        gpsBag.IsSaved = true;
        tdi.Update(result);
        ApplicationState.getCurrent().setTrainingDay(Helper.Copy(tdi));
        ApplicationState.getCurrent().CurrentEntryId = new LocalObjectKey(result);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        GPSTrackerEntryDTO entry=getEntry();
        if( ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isSaved(entry))
        {
            mnuShowMap=menu.add(Menu.NONE,2,Menu.NONE,R.string.gps_tracker_menu_show_map);
//            mnuShowMap.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
            MenuItemCompat.setShowAsAction(mnuShowMap,MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item==mnuShowMap)
        {
            showMap();
        }

        return true;
    }

    void showMap()
    {
        GPSTrackerEntryDTO entry=getEntry();
        if (isEmptyEntry())
        {
            BAMessageBox.ShowToast(R.string.gps_tracker_err_show_map_must_save);
            return;
        }
        GPSPointsBag pointsBag = ApplicationState.getCurrent().getTrainingDay().getGpsCoordinates(entry);
        if (!entry.hasCoordinates && (pointsBag == null || pointsBag.getPoints().size()==0))
        {
            BAMessageBox.ShowToast(R.string.gps_tracker_err_show_map_no_points);
            return;
        }
        Intent myIntent = new Intent(this, MapActivity.class);
        myIntent.putExtra("Item",getEntry());
        startActivityForResult(myIntent, 1);
    }



    void createConnection()
    {
        mConnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName className, IBinder binder) {

                gpsService = ((GPSTrackerService.MyBinder) binder).getService();
                prepareUI();
            }

            public void onServiceDisconnected(ComponentName className) {
                gpsService = null;
            }

        };
    }

    private ServiceConnection mConnection;


    BroadcastReceiver updateViewReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            prepareUI();

        }
    };
}
