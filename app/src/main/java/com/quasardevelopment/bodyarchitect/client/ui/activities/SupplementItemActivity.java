package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SuplementItemDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SuplementsEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 28.05.13
 * Time: 07:13
 * To change this template use File | Settings | File Templates.
 */
public class SupplementItemActivity extends SimpleEntryObjectActivityBase
{
    protected SuplementItemDTO selectedItem;
    EditText txtComment;
    EditText txtName;
    Spinner cmbTimeType;
    Button btnTime;
    EditText txtDosage;
    Spinner cmbDosageType;
    TextView tbSupplementType;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LocalObjectKey itemId=(LocalObjectKey)getIntent().getSerializableExtra("ItemId");
        SuplementsEntryDTO entry=getEntry();
        selectedItem= Helper.getItem(itemId,entry.items);

        View inflate = getLayoutInflater().inflate(R.layout.activity_supplement_item, null);
        setMainContent(inflate);

        getSupportActionBar().setSubtitle(R.string.supplements_activity_title);
        tbSupplementType= (TextView) findViewById(R.id.supplement_item_type);
        cmbDosageType= (Spinner) findViewById(R.id.supplement_item_dosage_type);
        txtDosage= (EditText) findViewById(R.id.supplement_item_dosage);
        cmbTimeType= (Spinner) findViewById(R.id.supplement_item_time_type);
        btnTime= (Button) findViewById(R.id.supplement_item_time);
        txtName= (EditText) findViewById(R.id.supplement_item_name);
        txtComment= (EditText) findViewById(R.id.txt_comment);

        txtComment.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String comment=s.toString();
                selectedItem.comment= !TextUtils.isEmpty(comment)?comment:null;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        txtName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String name=s.toString();
                selectedItem.name= !TextUtils.isEmpty(name)?name:null;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        txtDosage.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                selectedItem.dosage=TextUtils.isEmpty(s.toString())?0:Helper.parseDouble(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        createTimeType();
        createDosageType();
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new TimePickerDialog(SupplementItemActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        selectedItem.time.dateTime = new DateTime(c);
                        Button btn = (Button) view;
                        btn.setText(selectedItem.time.dateTime.toString(DateTimeFormat.shortTime()));
                    }
                }, selectedItem.time.dateTime.getHourOfDay(), selectedItem.time.dateTime.getMinuteOfHour(), true).show();
            }
        });
        setReadOnly();
    }

    private void createTimeType() {

        final String array_spinner[]=new String[5];
        array_spinner[0]= getString(R.string.not_set);
        array_spinner[1]= EnumLocalizer.Translate(WS_Enums.TimeType.OnEmptyStomach);
        array_spinner[2]= EnumLocalizer.Translate(WS_Enums.TimeType.BeforeWorkout);
        array_spinner[3]= EnumLocalizer.Translate(WS_Enums.TimeType.AfterWorkout);
        array_spinner[4]=EnumLocalizer.Translate(WS_Enums.TimeType.BeforeSleep);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbTimeType.setAdapter(adapter);

        cmbTimeType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        selectedItem.time.timeType= WS_Enums.TimeType.values()[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }

    private void createDosageType() {

        final String array_spinner[]=new String[5];
        array_spinner[0]= EnumLocalizer.Translate(WS_Enums.DosageType.Grams);
        array_spinner[1]= EnumLocalizer.Translate(WS_Enums.DosageType.Tablets);
        array_spinner[2]= EnumLocalizer.Translate(WS_Enums.DosageType.Units);
        array_spinner[3]= EnumLocalizer.Translate(WS_Enums.DosageType.Servings);
        array_spinner[4]=EnumLocalizer.Translate(WS_Enums.DosageType.MiliGrams);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbDosageType.setAdapter(adapter);

        cmbDosageType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        selectedItem.dosageType= WS_Enums.DosageType.values()[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        btnTime.setText(selectedItem.time.dateTime.toString(DateTimeFormat.shortTime()));
        cmbTimeType.setSelection(selectedItem.time.timeType.getCode());
        txtComment.setText(selectedItem.comment);
        txtName.setText(selectedItem.name);

        txtDosage.setText(Helper.ToDisplayText(selectedItem.dosage));
        tbSupplementType.setText(selectedItem.suplement.name);
        cmbDosageType.setSelection(selectedItem.dosageType.getCode());

    }

    void setReadOnly()
    {
        boolean readOnly=!getEditMode();
        txtComment.setEnabled(!readOnly);
        txtName.setEnabled(!readOnly);
        txtDosage.setEnabled(!readOnly);
        btnTime.setEnabled(!readOnly);
        cmbDosageType.setEnabled(!readOnly);
        cmbTimeType.setEnabled(!readOnly);
    }
}
