package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.controls.TrainingDaySelectorControl;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 22.04.13
 * Time: 07:56
 * To change this template use File | Settings | File Templates.
 */
public class TrainingDaySelectorActivity extends BANormalActivityBase
{
    TrainingDaySelectorControl dayCtrl;
    DateTime selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_training_day_selector, null);
        setMainContent(inflate);

        Intent intent=getIntent();
        selectedDate=(DateTime)intent.getSerializableExtra("SelectedDate");
        if(selectedDate==null)
        {
            selectedDate=DateTime.now(ISOChronology.getInstanceUTC());
        }
        dayCtrl=(TrainingDaySelectorControl)findViewById(R.id.dayCtrl);
        DateTimeFormatter frm= DateTimeFormat.longDate();
        getSupportActionBar().setSubtitle(selectedDate.toString(frm).toUpperCase());

    }

    @Override
    protected void onResume() {
        super.onResume();
        dayCtrl.Fill(selectedDate);
    }

    /*this method is a second part of opening GPS Entry (which was started in TrainingDaySelectorControl.GoToPage). If user don't have permissions to GPS then we neeed to ask about them.
* And here we back from the permission dialog*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case TrainingDaySelectorControl.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    dayCtrl.openGps(false);

                } else {

                    //Toast.makeText(this,"Needs permissions", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
