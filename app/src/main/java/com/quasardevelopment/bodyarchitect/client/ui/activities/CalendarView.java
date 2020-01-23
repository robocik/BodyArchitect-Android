package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.TrainingDayInfo;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.CalendarAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.CalendarDaysOfWeekAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.TrainingDaySelectorControl;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.UpgradeAccountFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

public class CalendarView extends BANormalActivityBase implements GestureOverlayView.OnGesturePerformedListener,IWsdl2CodeEvents {

    public Calendar month;
    public CalendarAdapter adapter;
    private GestureLibrary gLib;
    //public ArrayList<String> items; // container to store some random calendar items
    volatile ProgressDialog progressDlg;


    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture)
    {
        ArrayList<Prediction> predictions = gLib.recognize(gesture);
        for (Prediction prediction : predictions) {
            if (prediction.score > 1.0) {
                if(prediction.name.equals("swipeLeft"))
                {
                    onLeftSwipe();
                }
                else if(prediction.name.equals("swipeRight"))
                {
                    onRightSwipe();
                }
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GestureOverlayView gestureOverlayView = new GestureOverlayView(this);
        gestureOverlayView.addOnGesturePerformedListener(this);
        gestureOverlayView.setGestureColor(Color.TRANSPARENT);
        gestureOverlayView.setUncertainGestureColor(Color.TRANSPARENT);
        View inflate = getLayoutInflater().inflate(R.layout.calendar_view, null);
        gestureOverlayView.addView(inflate);
        setMainContent(gestureOverlayView);
        //setContentView(R.layout.calendar_view);


        month = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        month.set(Calendar.HOUR_OF_DAY, 0);
        month.set(Calendar.MINUTE, 0);
        month.set(Calendar.SECOND, 0);
        month.set(Calendar.MILLISECOND, 0);
        //month.set(Calendar.DAY_OF_MONTH,1);

//        DateTime monthDate=DateTime.now(ISOChronology.getInstanceUTC());


        //onNewIntent(getIntent());

        //items = new ArrayList<String>();
        adapter = new CalendarAdapter(this, month);

        gLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gLib.load()) {
            finish();
        }

        //gestureLib = GestureLibraries.fromRawResource(this, R.raw.gestures);

        GridView gvDaysOfWeek = (GridView) findViewById(R.id.gvDaysOfWeek);
        gvDaysOfWeek.setAdapter(new CalendarDaysOfWeekAdapter());
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);



        TextView title  = (TextView) findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

        TextView previous  = (TextView) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onRightSwipe();
            }
        });

        TextView next  = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onLeftSwipe();
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView date = (TextView)v.findViewById(R.id.date);
                if(date instanceof TextView && !date.getText().equals("")) {
                    String day = date.getText().toString();

                    DateTime selectedDate= new DateTime(month.get(Calendar.YEAR),month.get(Calendar.MONTH)+1,Integer.parseInt(day),0,0,ISOChronology.getInstanceUTC());


                    if (DateTimeHelper.isFuture(selectedDate) && (!ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isMine() ||  !UpgradeAccountFragment.EnsureAccountType(CalendarView.this)))
                    {
                        return;
                    }
                    if (ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isMine() || ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays().containsKey(selectedDate))
                    {
                        Intent myIntent = new Intent(CalendarView.this, TrainingDaySelectorActivity.class);
                        myIntent.putExtra("SelectedDate",selectedDate);
                        CalendarView.this.startActivity(myIntent);
                    }
                    // return chosen date as string format
//                    intent.putExtra("date", android.text.format.DateFormat.format("yyyy-MM", month)+"-"+day);
//                    setResult(RESULT_OK, intent);
//                    finish();
                }

            }
        });


        getSupportActionBar().setSubtitle(R.string.calendar_activity_title);

    }

    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        DateTime monthDate=new DateTime(month,ISOChronology.getInstanceUTC());
        monthDate=DateTimeHelper.ToMonth(monthDate);
        fillCalendar(monthDate);
    }

    @Override
    public void onBackPressed()
    {
        ApplicationState.getCurrent().setCurrentBrowsingTrainingDays(null);
        super.onBackPressed();
    }


    private void getCurrentTrainingDays(final DateTime monthDate)
    {
        if(progressDlg!=null)
        {
            return;
        }
        progressDlg= BAMessageBox.ShowProgressDlg(this, R.string.progress_retrieving_entries);

        ApplicationState.getCurrent().RetrieveMonthAsync(monthDate,ApplicationState.getCurrent().getCurrentBrowsingTrainingDays()
        ,new IWsdl2CodeEvents() {
            @Override
            public void Wsdl2CodeStartedRequest() {}

            @Override
            public void Wsdl2CodeFinished(String methodName, Object Data) {
                fillCalendarData(monthDate,ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays());
                if(progressDlg!=null)
                {
                    progressDlg.dismiss();
                    progressDlg=null;
                }
            }

            @Override
            public void Wsdl2CodeFinishedWithException(Exception ex) {
                if(progressDlg!=null)
                {
                    progressDlg.dismiss();
                    progressDlg=null;
                }
                BAMessageBox.ShowToast(R.string.calendar_view_err_retrieve_entries);
            }

            @Override
            public void Wsdl2CodeEndedRequest() {}
        });
    }

    private void fillCalendar(DateTime monthDate)
    {
        if (!ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().isMonthLoaded(monthDate))
        {
            if (ApplicationState.getCurrent().isOffline)
            {
                fillCalendarData(monthDate, ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays());
                BAMessageBox.ShowToast(R.string.calendar_view_err_retrieve_entries);
            }
            else
            {
                getCurrentTrainingDays(monthDate);
            }

        }
        else
        {
            fillCalendarData(monthDate,ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays());
        }

    }

    private void fillCalendarData(DateTime monthDate,HashMap<DateTime, TrainingDayInfo> days)
    {
        adapter.refreshDays();
        adapter.setItems(days);
        adapter.notifyDataSetChanged();
        TextView title  = (TextView) findViewById(R.id.title);
        title.setText(android.text.format.DateFormat.format("MMMM yyyy", monthDate.toDate()));
//        var list = new List<ISupportCalendarItem>();
//        foreach (var item in days)
//        {
//            list.Add(new CalendarItem(item.Key,item.Value));
//        }
//        mainCalendar.DatesSource = list;
//        mainCalendar.RefreshInfo();
    }

//    public void refreshCalendar()
//    {
//        TextView title  = (TextView) findViewById(R.id.title);
//
//        adapter.refreshDays();
//        adapter.notifyDataSetChanged();
//
//        title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
//    }

//    public void onNewIntent(Intent intent) {
//        String date = intent.getStringExtra("date");
//        String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
//        month.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
//    }

//    public Runnable calendarUpdater = new Runnable() {
//
//        @Override
//        public void run() {
//            items.clear();
//            // format random values. You can implement a dedicated class to provide real values
//            for(int i=0;i<31;i++) {
//                Random r = new Random();
//
//                if(r.nextInt(10)>6)
//                {
//                    items.add(Integer.toString(i));
//                }
//            }
//
//            adapter.setItems(items);
//            adapter.notifyDataSetChanged();
//        }
//    };

    private void onLeftSwipe()
    {
        //int days = chronology.dayOfMonth().getMaximumValue(date); joda implementation
        if(month.get(Calendar.MONTH)== month.getActualMaximum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR)+1),month.getActualMinimum(Calendar.MONTH),1);
        } else {
            month.set(Calendar.MONTH,month.get(Calendar.MONTH)+1);
        }
//                refreshCalendar();
        fillCalendar(new DateTime(month, ISOChronology.getInstanceUTC()));
    }

    private void onRightSwipe()
    {
        if(month.get(Calendar.MONTH)== month.getActualMinimum(Calendar.MONTH)) {
            month.set((month.get(Calendar.YEAR)-1),month.getActualMaximum(Calendar.MONTH),1);
        } else {
            month.set(Calendar.MONTH,month.get(Calendar.MONTH)-1);
        }
//                refreshCalendar();
        fillCalendar(new DateTime(month, ISOChronology.getInstanceUTC()));
    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        DateTime monthDate=new DateTime(month,ISOChronology.getInstanceUTC());
        monthDate=DateTimeHelper.ToMonth(monthDate);
        fillCalendarData(monthDate,ApplicationState.getCurrent().getCurrentBrowsingTrainingDays().getTrainingDays());
//                if(progressDlg!=null)
//                {
//                    progressDlg.dismiss();
//                    progressDlg=null;
//                }
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}
