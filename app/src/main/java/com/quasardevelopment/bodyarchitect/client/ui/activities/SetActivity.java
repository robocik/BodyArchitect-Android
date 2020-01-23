package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.UpgradeAccountFragment;
import com.quasardevelopment.bodyarchitect.client.util.ActivitiesSettings;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 08.05.13
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */
public class SetActivity extends SetActivityBase
{

    CheckBox chkRestPause;
    CheckBox chkSuperSlow;
    TextView tbSetTitle;
    EditText txtReps;
    EditText txtWeight;
    Spinner cmbSetType;
    Spinner cmbDropSet;
    ToggleButton btnTimerExpander;
    LinearLayout pnlTimer;
    TextView tbTimerDescription;



    @Override
    protected void setReadOnly()
    {
        super.setReadOnly();
        boolean readOnly=!getEditMode();
        chkRestPause.setClickable(!readOnly);
        chkSuperSlow.setClickable(!readOnly);
        txtReps.setEnabled(!readOnly);
        txtWeight.setEnabled(!readOnly);
        cmbSetType.setEnabled(!readOnly);
        cmbDropSet.setEnabled(!readOnly);
        tbTimerDescription.setVisibility(readOnly?View.GONE:View.VISIBLE);
    }

    @Override
    protected void buildUI()
    {

        View inflate = getLayoutInflater().inflate(R.layout.activity_set, null);
        setMainContent(inflate);

        createSetType();
        createDropSet();
        chkRestPause = (CheckBox) findViewById(R.id.strenght_training_set_chk_restpause);
        chkRestPause.setChecked(selectedItem.isRestPause);
        chkRestPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UpgradeAccountFragment.EnsureAccountType(SetActivity.this)) {
                    chkRestPause.setChecked(selectedItem.isRestPause);
                    return;
                }
                selectedItem.isRestPause = chkRestPause.isChecked();
            }
        });

        chkSuperSlow = (CheckBox) findViewById(R.id.strenght_training_set_chk_superslow);
        chkSuperSlow.setChecked(selectedItem.isSuperSlow);
        chkSuperSlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UpgradeAccountFragment.EnsureAccountType(SetActivity.this)) {
                    chkSuperSlow.setChecked(selectedItem.isSuperSlow);
                    return;
                }
                selectedItem.isSuperSlow = chkSuperSlow.isChecked();
            }
        });


        tbSetTitle = (TextView) findViewById(R.id.strength_training_set_title);

        tbSetTitle.setText(String.format(getString(R.string.set_activity_serie_header), selectedItem.strengthTrainingItem.series.indexOf(selectedItem)+1));


        pnlTimer = (LinearLayout) findViewById(R.id.strength_training_set_timer_panel);
        tbTimerDescription= (TextView) findViewById(R.id.timer_description);
        btnTimerExpander = (ToggleButton) findViewById(R.id.strength_training_set_timer_expander);

        btnTimerExpander.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                pnlTimer.setVisibility(isChecked?View.VISIBLE:View.GONE);
                ActivitiesSettings.setTimerExpanded(isChecked);
            }
        });
        btnTimerExpander.setChecked(ActivitiesSettings.getTimerExpanded());

        createRepsWeight();
        super.buildUI();

    }

    private void createRepsWeight() {
        txtReps = (EditText) findViewById(R.id.strength_training_set_reps);
        if(selectedItem.repetitionNumber!=null)
        {
            txtReps.setText(String.format("%.0f",selectedItem.repetitionNumber));
        }
        if(selectedItem.tag instanceof SerieDTO)
        {
            txtReps.setHint(String.format("%.0f",((SerieDTO)selectedItem.tag).repetitionNumber));
        }

        txtReps.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                selectedItem.repetitionNumber = TextUtils.isEmpty(s.toString())?null:Helper.parseDouble(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        txtWeight = (EditText) findViewById(R.id.strength_training_set_weight);

        txtWeight.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                selectedItem.weight=TextUtils.isEmpty(s.toString())?null:Helper.FromDisplayWeight(Helper.parseDouble(s.toString()));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        if(selectedItem.weight!=null)
        {
            txtWeight.setText(Helper.ToDisplayWeightText(selectedItem.weight));
        }
        if(selectedItem.tag instanceof SerieDTO)
        {
            txtWeight.setHint(Helper.ToDisplayWeightText(((SerieDTO) selectedItem.tag).weight));
        }

        TextView tbWeightType = (TextView) findViewById(R.id.strength_training_set_weight_type);
        tbWeightType.setText(EnumLocalizer.WeightType());
    }

    private void createSetType() {
        cmbSetType = (Spinner) findViewById(R.id.strenght_training_set_combobox_settype);
        final String array_spinner[]=new String[5];
        array_spinner[0]=getString(R.string.SetType_Long_Normalna);
        array_spinner[1]= getString(R.string.SetType_Long_Rozgrzewkowa);
        array_spinner[2]= getString(R.string.SetType_Long_PrawieMax);
        array_spinner[3]= getString(R.string.SetType_Long_Max);
        array_spinner[4]= getString(R.string.SetType_Long_MuscleFailure);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbSetType.setAdapter(adapter);
        cmbSetType.setSelection(selectedItem.setType.getCode());
        cmbSetType.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        selectedItem.setType= WS_Enums.SetType.values()[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }

    private void createDropSet() {
        cmbDropSet = (Spinner) findViewById(R.id.strenght_training_set_combobox_dropset);
        final String array_spinner[]=new String[5];
        array_spinner[0]= EnumLocalizer.Translate(WS_Enums.DropSetType.None);
        array_spinner[1]= EnumLocalizer.Translate(WS_Enums.DropSetType.IDropSet);
        array_spinner[2]= EnumLocalizer.Translate(WS_Enums.DropSetType.IIDropSet);
        array_spinner[3]= EnumLocalizer.Translate(WS_Enums.DropSetType.IIIDropSet);
        array_spinner[4]= EnumLocalizer.Translate(WS_Enums.DropSetType.IVDropSet);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbDropSet.setAdapter(adapter);
        cmbDropSet.setSelection(selectedItem.dropSet.getCode());
        cmbDropSet.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        selectedItem.dropSet= WS_Enums.DropSetType.values()[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }
}
