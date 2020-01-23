package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.MyPlaceCache;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.MyPlacesAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.TimerControl;
import com.quasardevelopment.bodyarchitect.client.util.ActivitiesSettings;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.IWsdl2CodeEvents;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.MyPlaceLightDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

import java.util.ArrayList;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 24.05.13
 * Time: 07:51
 * To change this template use File | Settings | File Templates.
 */
public class StrengthTrainingOptionsActivity extends SimpleEntryObjectActivityBase implements IWsdl2CodeEvents {
    Spinner cmbMyPlaces;
    EditText txtComment;
    android.support.v7.widget.SwitchCompat tsReportStatus;
    ProgressDialog progressDlg;
    TimerControl ctrlTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_strength_training_options, null);
        setMainContent(inflate);

        cmbMyPlaces= (Spinner) findViewById(R.id.strenght_training_myplaces);
        txtComment=(EditText)findViewById(R.id.strenght_training_txt_comment);
        tsReportStatus =(android.support.v7.widget.SwitchCompat)findViewById(R.id.entry_activity_report_status);
        ctrlTimer= (TimerControl) findViewById(R.id.ctrlTimer);

        getSupportActionBar().setSubtitle(R.string.strength_training_activity_title);
        if(getEditMode())
        {
            cmbMyPlaces.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                        {
                            StrengthTrainingEntryDTO strength=getEntry();
                            strength.myPlace= (MyPlaceLightDTO) cmbMyPlaces.getAdapter().getItem(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {  }
                    });
            tsReportStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    getEntry().reportStatus=checked? WS_Enums.ReportStatus.ShowInReport: WS_Enums.ReportStatus.SkipInReport;
                } });

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
        }

        tsReportStatus.setTextOn(getString(R.string.entry_object_activity_show_in_reports_show));
        tsReportStatus.setTextOff(getString(R.string.entry_object_activity_show_in_reports_hide));
        updateReadOnly();
    }


    @Override
    protected void onResume() {
        super.onResume();

        MyPlaceCache cache= ObjectsReposidory.getCache().getMyPlaces();
        StrengthTrainingEntryDTO strength=getEntry();

        txtComment.setText(strength.comment);
        tsReportStatus.setChecked(strength.reportStatus.equals(WS_Enums.ReportStatus.ShowInReport));

        if (!getEditMode())
        {//when we browser other user calendar we shouldn't retrieve my places
            ArrayList<MyPlaceLightDTO> items = new ArrayList<MyPlaceLightDTO>();
            items.add(((StrengthTrainingEntryDTO)getEntry()).myPlace);
            MyPlacesAdapter adapter = new MyPlacesAdapter(this,items);
            cmbMyPlaces.setAdapter(adapter);
            return;
        }

        ctrlTimer.setIsStarted(getEditMode() && ActivitiesSettings.isGlobalTimerEnabled());

        if(cache.isLoaded())
        {
            //Parcelable state = lvExercises.onSaveInstanceState();
            fillMyPlaces();
            //lvExercises.onRestoreInstanceState(state);
        }
        else
        {
            progressDlg= BAMessageBox.ShowProgressDlg(this, R.string.progress_retrieving_items);
            cache.LoadAsync(this);
        }
    }

    void updateReadOnly()
    {
        boolean isReadOnly=!getEditMode();
        txtComment.setEnabled(!isReadOnly);
        cmbMyPlaces.setEnabled(!isReadOnly && ApplicationState.getCurrent().isPremium());
        tsReportStatus.setClickable(!isReadOnly);
    }

    void closeProgress()
    {
        if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    void fillMyPlaces()
    {
        final StrengthTrainingEntryDTO strength=getEntry();
        MyPlaceCache cache=ObjectsReposidory.getCache().getMyPlaces();
        final MyPlacesAdapter adapter = new MyPlacesAdapter(this,cache.getItems().values());
        adapter.setDropDownViewResource(R.layout.my_place_item);

        cmbMyPlaces.setAdapter(adapter);
        MyPlaceLightDTO itemToSelect= Helper.SingleOrDefault(filter(new Predicate<MyPlaceLightDTO>() {
            public boolean apply(MyPlaceLightDTO item) {
                return strength.myPlace==null && item.isDefault || (strength.myPlace!=null && item.globalId.equals(strength.myPlace.globalId));
            }
        }, cache.getItems().values()));


        cmbMyPlaces.setSelection(adapter.getPosition(itemToSelect));

    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        fillMyPlaces();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        BAMessageBox.ShowToast(R.string.err_unhandled);
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        closeProgress();
    }
}
