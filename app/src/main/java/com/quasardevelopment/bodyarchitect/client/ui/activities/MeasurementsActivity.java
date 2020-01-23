package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.controls.MeasurementsControl;
import com.quasardevelopment.bodyarchitect.client.ui.controls.MeasurementsTimeControl;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SizeEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 29.05.13
 * Time: 06:59
 * To change this template use File | Settings | File Templates.
 */
public class MeasurementsActivity extends EntryObjectActivityBase {

    private android.support.v7.widget.SwitchCompat tsEntryStatus;
    private TextView tbTrainingDateHeader;
    private android.support.v7.widget.SwitchCompat tsReportStatus;
    private EditText txtComment;
    MeasurementsControl ctrlSizes;
    MeasurementsTimeControl sizesTimeCtrl;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_measurements, null);
        setMainContent(inflate);

        tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();


        TabHost.TabSpec spec1=tabHost.newTabSpec(getString(R.string.measurements_activity_header_size));
        spec1.setContent(R.id.measurements_activity_tab_sizes);
        spec1.setIndicator(getString(R.string.measurements_activity_header_size));


        TabHost.TabSpec spec2=tabHost.newTabSpec(getString(R.string.measurements_activity_header_info));
        spec2.setIndicator(getString(R.string.strength_training_activity_header_info));
        spec2.setContent(R.id.measurements_activity_tab_info);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);


        sizesTimeCtrl= (MeasurementsTimeControl) findViewById(R.id.sizesTimeCtrl);
        tbTrainingDateHeader = (TextView)findViewById(R.id.entry_activity_header_date);


        tsEntryStatus = (android.support.v7.widget.SwitchCompat)findViewById(R.id.entry_activity_entry_status);


        tsEntryStatus.setTextOn(getString(R.string.status_done));
        tsEntryStatus.setTextOff(getString(R.string.status_planned));
        tsEntryStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                getEntry().status = checked ? WS_Enums.EntryObjectStatus.Done : WS_Enums.EntryObjectStatus.Planned;
            }
        });
        tsReportStatus = (android.support.v7.widget.SwitchCompat)findViewById(R.id.entry_activity_report_status);
        txtComment = (EditText)findViewById(R.id.txt_comment);
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
        tsReportStatus.setTextOn(getString(R.string.entry_object_activity_show_in_reports_show));
        tsReportStatus.setTextOff(getString(R.string.entry_object_activity_show_in_reports_hide));
        tsReportStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                getEntry().reportStatus=checked? WS_Enums.ReportStatus.ShowInReport: WS_Enums.ReportStatus.SkipInReport;
            }
        });

        ctrlSizes= (MeasurementsControl) findViewById(R.id.sizesCtrl);
        getSupportActionBar().setSubtitle(R.string.measurements_activity_title);
        Connect(tabHost);
        setReadOnly();
    }



    private void setReadOnly() {
        boolean isReadOnly=!getEditMode();
        tsReportStatus.setClickable(!isReadOnly);
        tsEntryStatus.setClickable(!isReadOnly);
        txtComment.setEnabled(!isReadOnly);
        ctrlSizes.setReadOnly(isReadOnly);
        sizesTimeCtrl.setReadOnly(isReadOnly);
    }

    public SizeEntryDTO getEntry()
    {
        return super.getEntry();
    }

    @Override
    protected void show(boolean reload)
    {
        SizeEntryDTO sizes=getEntry();
        txtComment.setText(sizes.comment);
        tsEntryStatus.setChecked(sizes.status == WS_Enums.EntryObjectStatus.Done);
        tsReportStatus.setChecked(sizes.reportStatus == WS_Enums.ReportStatus.ShowInReport);
        DateTimeFormatter formatter = DateTimeFormat.longDate();
        tbTrainingDateHeader.setText(sizes.trainingDay.trainingDate.toString(formatter));
        sizesTimeCtrl.fill(sizes.wymiary,sizes.trainingDay.isMine());
        ctrlSizes.fill(sizes.wymiary, ApplicationState.getCurrent().getCurrentBrowsingTrainingDays());
    }
}
