package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WymiaryDTO;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 06.06.13
 * Time: 08:15
 * To change this template use File | Settings | File Templates.
 */
public class MeasurementsTimeControl extends LinearLayout {
    Button btnTimePicker;
    Button btnTimeNow;
    Spinner cmbTimeType;
    WymiaryDTO wymiary;

    public MeasurementsTimeControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.measurements_time_control, this);

        btnTimeNow = (Button)findViewById(R.id.btnTimeNow);
        btnTimePicker = (Button)findViewById(R.id.btnTimePicker);

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT")) ;
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        wymiary.time.dateTime = new DateTime(c);
                        Button btn = (Button) view;
                        btn.setText(wymiary.time.dateTime.toString(DateTimeFormat.shortTime()));
                    }
                }, wymiary.time.dateTime.getHourOfDay(),wymiary.time.dateTime.getMinuteOfHour(), true).show();
            }
        });
        btnTimeNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                wymiary.time.dateTime = DateTime.now();
                btnTimePicker.setText(wymiary.time.dateTime.toString(DateTimeFormat.shortTime()));
            }
        });
        createTimeType();
    }

    public void fill(WymiaryDTO wymiary,boolean showNowButton)
    {
        this.wymiary=wymiary;
        btnTimePicker.setText(wymiary.time.dateTime.toString(DateTimeFormat.shortTime()));
        cmbTimeType.setSelection(wymiary.time.timeType.getCode());
        btnTimeNow.setVisibility(showNowButton?View.VISIBLE:View.GONE);
    }

    private void createTimeType() {
        cmbTimeType= (Spinner) findViewById(R.id.cmb_time_type);
        final String array_spinner[]=new String[5];
        array_spinner[0]=this.getResources().getString(R.string.not_set);
        array_spinner[1]= getResources().getString(R.string.TimeType_OnEmptyStomach);
        array_spinner[2]= getResources().getString(R.string.TimeType_BeforeWorkout);
        array_spinner[3]= getResources().getString(R.string.TimeType_AfterWorkout);
        array_spinner[4]= getResources().getString(R.string.TimeType_BeforeSleep);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( getContext(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbTimeType.setAdapter(adapter);

        cmbTimeType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        wymiary.time.timeType= WS_Enums.TimeType.values()[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }

    public void setReadOnly(boolean isReadOnly) {
        btnTimePicker.setEnabled(!isReadOnly);
        btnTimeNow.setEnabled(!isReadOnly);
        cmbTimeType.setEnabled(!isReadOnly);
    }
}
