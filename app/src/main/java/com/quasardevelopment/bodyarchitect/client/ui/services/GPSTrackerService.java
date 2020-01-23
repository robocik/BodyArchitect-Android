package com.quasardevelopment.bodyarchitect.client.ui.services;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.CustomersCache;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.GPSPoint;
import com.quasardevelopment.bodyarchitect.client.model.IPerson;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.activities.GPSTrackerActivity;
import com.quasardevelopment.bodyarchitect.client.util.*;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.CustomerDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.GPSTrackerEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WeatherDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.envelopes.WcfResult;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 17.07.13
 * Time: 20:51
 * To change this template use File | Settings | File Templates.
 */
public class GPSTrackerService extends Service implements LocationListener, GpsStatus.Listener {
    static final int GPS_UPDATE_INTERVAL = 1000; //1 sec
    static final int GPS_SERVICE_ID = 56012;
    private int autoPauseCount;
    Float currentSpeed;

    private boolean weatherRetrievingStarted = false;
    Location previousPosition;
    private boolean gpsSource = true;
    private DateTime timerStartDateTime;
    private final IBinder mBinder = new MyBinder();
    LocationManager locationManager;
    private long mLastLocationMillis;
    private Location mLastLocation;
    private boolean isGPSFix;
    public boolean TimerHasBeenStarted;
    public boolean IsPause = true;
    Timer timer;


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {

        Notification notification = createNotification();

        startForeground(GPS_SERVICE_ID, notification);
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.



        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_UPDATE_INTERVAL, 0, this);
            locationManager.addGpsStatusListener(this);
        }
        // Register the listener with the Location Manager to receive location updates


    }

    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, GPSTrackerActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String durationString = getString(R.string.gps_tracker_notification_text);


        return new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(durationString)
                .setSmallIcon(R.drawable.ba_logo)
                .setContentIntent(pendingIntent)
                .build();
    }


    @Override
    public boolean onUnbind(Intent intent) {
        if (locationManager != null) {
            try {
                locationManager.removeUpdates(this);
                locationManager.removeGpsStatusListener(this);
            }
            catch (SecurityException ex)
            {
            }
            locationManager=null;
        }
        if (timer!=null)
        {
            timer.cancel();
            timer.purge();
            timer=null;
        }
        return super.onUnbind(intent);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY ;    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) return;

        mLastLocationMillis = SystemClock.elapsedRealtime();
        mLastLocation = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderEnabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onProviderDisabled(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public class MyBinder extends Binder {
        public GPSTrackerService getService() {
            return GPSTrackerService.this;
        }
    }

    public boolean isGPSReady()
    {
//        if (!isGpsAvailable() )
//        {
//            return false;
//        }
        //Location location = getLocation();

        //return location!=null && location.getAltitude()!=0 && location.getAccuracy() < 70;
        return isGPSFix;

    }


    boolean isRefreshingGpsStatus;
    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//                if (mLastLocation != null)
//                    isGPSFix = (SystemClock.elapsedRealtime() - mLastLocationMillis) < GPS_UPDATE_INTERVAL*2;
//
//                if (isGPSFix) { // A fix has been acquired.
//                    // Do something.
//                } else { // The fix has been lost.
//                    // Do something.
//                }
                if (mLastLocation != null)
                {
                    if((SystemClock.elapsedRealtime() - mLastLocationMillis) < GPS_UPDATE_INTERVAL*2)
                    {
                        isGPSFix = true;
                        isRefreshingGpsStatus=false;
                    }
                    else
                    {
                        if(isRefreshingGpsStatus)
                        {
                            isGPSFix = false;
                        }
                        if ( isGPSFix && locationManager!=null)
                        {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GPS_UPDATE_INTERVAL, 0, this);
                            isRefreshingGpsStatus=true;
                        }

                    }
                }

                break;
            case GpsStatus.GPS_EVENT_FIRST_FIX:
                // Do something.
                isGPSFix = true;

                break;
        }

        refreshView();
//        if(oldGpsFix!=isGPSFix)
//        {
//
//            updateGpsSignal(isGPSFix);
//        }
    }

    public Location getLocation()
    {
        return mLastLocation;
    }

    public boolean IsAutoPause()
    {
//        return timer.IsEnabled && IsPause;
        return timer!=null && IsPause;
    }

    public Float getCurrentSpeed()
    {
        return currentSpeed;
    }

    private short slowSpeedCount = 0;
    private void slowSpeedCorrection()
    {
        currentSpeed = null;
        if (isGPSReady())
        {
            Location myPosition=getLocation();
            currentSpeed = myPosition.getSpeed();

            if (previousPosition != null)
            {//sprawdz czy gdy sie nie poruszamy to czy predkosc wynosi 0. jesli nie to oblicz ja a nie bierz z gps
                float distance = myPosition.distanceTo(previousPosition);
                if (distance == 0)
                {
                    slowSpeedCount++;
                }
                else
                {
                    slowSpeedCount = 0;
                }
            }
            else
            {
                slowSpeedCount = 0;
            }

            if (slowSpeedCount >= 3)
            {
                currentSpeed = 0.0f;
            }
        }
    }

    public void gpsStartStopImplementation()
    {
        if (IsPause)
        {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timerTick();
                }
            }, 0, 1000);
        }
        else
        {
            timer.cancel();
            timer.purge();
            timer=null;
        }
        startTimer(IsPause);
        TimerHasBeenStarted = true;
    }

    void checkAutoPause()
    {
        if (!Settings.isAutoPause() || !isGPSReady())
        {//auto pause is disabled
            //if gps is lost then we shouldn't change anything in workout - if it was started then it will be
            return;
        }
        if (currentSpeed == 0)
        {
            if (IsPause)
            {
                return;
            }
            autoPauseCount++;
            if (autoPauseCount >= 5)
            {
                //we should start auto pause after 5 seconds of standing user
                startTimer(false);
            }
            return;
        }
        if (IsAutoPause())
        {
            startTimer(true);
        }
        autoPauseCount = 0;
    }

    private void timerTick() {

        GPSTrackerEntryDTO entry=getEntry();
        if(entry==null)
        {
            return;
        }
        slowSpeedCorrection();
        checkAutoPause();
        if (!IsPause)
        {
            addGpsPoint();
            if (entry.startDateTime != null)
            {
                calculateCalories();

            }
        }

        if(isGPSReady())
        {
            Location myPosition = getLocation();
            calculateDistance(myPosition);
        }


        refreshView();
//        showCurrentSpeed();
    }

    private void refreshView() {
        Intent msgIntent = new Intent(Constants.RefreshViewFilter);
        LocalBroadcastManager.getInstance(this).sendBroadcast(msgIntent);
    }

    void calculateDistance(Location myPosition)
    {
        if (IsPause)
        {
            //timer.IsEnabled means that there is no pause
            return;
        }

        if (previousPosition != null)
        {
            GPSTrackerEntryDTO entry=getEntry();
            entry.distance += myPosition.distanceTo(previousPosition);
        }
        previousPosition = myPosition;

    }

    void calculateCalories()
    {
        GPSTrackerEntryDTO entry = getEntry();
        if (ApplicationState.getCurrent().getTrainingDay().getTrainingDay().customerId!=null)
        {
            CustomersCache cache = ObjectsReposidory.getCache().getCustomers();
            CustomerDTO customer=cache.GetItem(ApplicationState.getCurrent().getTrainingDay().getTrainingDay().customerId);
            CalculateCaloriesBurned(entry, (float) (double) getCurrentDuration(), customer);
        }
        else
        {
            CalculateCaloriesBurned(entry,(float)(double)getCurrentDuration(), ApplicationState.getCurrent().getProfileInfo());
        }
    }

    public void CalculateCaloriesBurned(GPSTrackerEntryDTO gpsEntry,float duration, IPerson person)
    {
        gpsEntry.calories = GPSHelper.CalculateCalories(gpsEntry.exercise.met, (double) duration, person);
    }

    public GPSTrackerEntryDTO getEntry()
    {
        return ApplicationState.getCurrent().getTrainingDay().getTrainingDay().getTypedEntry(ApplicationState.getCurrent().CurrentEntryId);
    }

    void addGpsPoint()
    {
        //todo:remove this
        if(!Constants.IsDebugMode)
        {
            gpsSource=true;
        }

        GPSPoint lastPoint = Helper.LastOrDefault(getPoints());
        Double currentDuration = this.getCurrentDuration();
        if (lastPoint == null || currentDuration - lastPoint.Duration >= 4)
        {
            //by default we add points every 4 seconds
            //even if gpsSource is false but we have connection to the gps then use it
            if (this.isGPSReady() || gpsSource)
            {
                addCurrentGpsPoint();
            }
            else if(!gpsSource)
            {
                Random rand = new Random();
                getPoints().add(new GPSPoint((float) rand.nextDouble(), (float) rand.nextDouble(), 5, 6, (float) (double) getCurrentDuration()));
                RetrieveWeather(new GPSPoint(50.9313049f, 17.2975941f, 3, 4, 1));
            }
        }

    }



    private void startPause()
    {
        GPSTrackerEntryDTO entry=getEntry();
        entry.endDateTime = DateTime.now();
        addCurrentGpsPoint();
        addNotAvailablePoint(true);
        Seconds totalSeconds=Seconds.secondsBetween(timerStartDateTime,DateTime.now());
        entry.duration += totalSeconds.getSeconds();

        getCurrentDuration();
        timerStartDateTime = DateTime.now();
    }

    private void startTimer(boolean start)
    {
//        btnStart.setImageDrawable(start?getResources().getDrawable(R.drawable.stop):getResources().getDrawable(R.drawable.start));

        if(start)
        {
            GPSTrackerEntryDTO entry=getEntry();
            timerStartDateTime = DateTime.now();
            //if user started workout (timer) then set application to run under lock screen

            if (entry.startDateTime == null)
            {
                entry.startDateTime = DateTime.now();
            }
            if(entry.duration==null)
            {
                entry.duration = 0.0;
            }
            if (entry.distance == null)
            {
                entry.distance = 0.0;
            }
            entry.status = WS_Enums.EntryObjectStatus.Done;

            addCurrentGpsPoint();
            autoPauseCount = 0;
        }
        else
        {
            startPause();
        }
        IsPause = !start;

        refreshView();
        //updateButtons();
    }

    private void RetrieveWeather(final GPSPoint location) {
        if (ApplicationState.getCurrent().isOffline || !ApplicationState.getCurrent().isPremium())
        {//weather conditions are retrieved only for premium or instructor accounts. also skip weather retrieving in offline mode
            return;
        }
        GPSTrackerEntryDTO entry=getEntry();
        boolean hasWeather = entry.weather != null && entry.weather.temperature != null;
        if (weatherRetrievingStarted || hasWeather)
        {//run retrieving weather only once
            return;
        }
        weatherRetrievingStarted = true;
        //retrieve weather for user current location

        new AsyncTask<Void, Void, WcfResult<WeatherDTO>>(){
            @Override
            protected void onPreExecute() {
            };
            @Override
            protected WcfResult<WeatherDTO> doInBackground(Void... params) {
                WcfResult<WeatherDTO> param = new WcfResult<WeatherDTO>();
                try {
                    WorldWeatherOnline weatherService = new WorldWeatherOnline();
                    param.Result=weatherService.requestWeatherForecast(location.Latitude,location.Longitude,1);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                return param;
            }
            @Override
            protected void onPostExecute(WcfResult<WeatherDTO> result)
            {
                GPSTrackerEntryDTO entry=getEntry();
                if(result.Result!=null)
                {
                    entry.weather=result.Result;
                }
            }
        }.execute();
    }

    public Double getCurrentDuration()
    {
        GPSTrackerEntryDTO entry=getEntry();
        Seconds totalSeconds=Seconds.secondsBetween(timerStartDateTime, DateTime.now());
        //entry.duration += totalSeconds.getSeconds();
//        return viewModel.Entry.Duration.Value +(decimal) (DateTime.Now - timerStartDateTime).TotalSeconds;
        return entry.duration + totalSeconds.getSeconds();
    }

    void addNotAvailablePoint(boolean isPause)
    {
        GPSPoint lastPoint = Helper.LastOrDefault(getPoints());
        if (lastPoint != null)
        {
            if (!lastPoint.IsNotAvailable() && !isPause)
            {
                getPoints().add(GPSPoint.CreateNotAvailable(getCurrentDuration().floatValue()));
            }
            else if (!lastPoint.IsPause() && isPause)
            {
                getPoints().add(GPSPoint.CreatePause(getCurrentDuration().floatValue()));
            }

        }
    }

    private void addCurrentGpsPoint()
    {
        if (IsPause)
        {
            return;
        }
        if (isGPSReady() )
        {

            Location myPosition = getLocation();
            Double duration=getCurrentDuration();
            if(myPosition==null || duration==null)
            {
                  return;
            }
            GPSPoint location = new GPSPoint((float) myPosition.getLatitude(),
                    (float) myPosition.getLongitude(),
                    (float) myPosition.getAltitude(), currentSpeed!=null?currentSpeed:0.0f,
                    duration.floatValue());
            getPoints().add(location);
            RetrieveWeather(location);
        }
        else
        {
//mark last point as lost connection
            addNotAvailablePoint(false);
        }
    }

    public List<GPSPoint> getPoints()
    {
        return ApplicationState.getCurrent().getTrainingDay().getGpsCoordinates(getEntry()).getPoints();
    }
}
