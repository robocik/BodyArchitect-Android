package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingItemDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 28.05.13
 * Time: 08:49
 * To change this template use File | Settings | File Templates.
 */
public class StrengthTrainingItemOptionsActivity extends SimpleEntryObjectActivityBase
{
    StrengthTrainingItemDTO selectedItem;

    Spinner cmbDoneWay;
    EditText txtComment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        LocalObjectKey itemId=(LocalObjectKey)getIntent().getSerializableExtra("ItemId");
        StrengthTrainingEntryDTO entry=getEntry();

        selectedItem= Helper.getItem(itemId, entry.entries);

        super.onCreate(savedInstanceState);
        View inflate = getLayoutInflater().inflate(R.layout.activity_strength_training_item_options, null);
        setMainContent(inflate);

        getSupportActionBar().setSubtitle(R.string.strength_training_activity_title);
        cmbDoneWay= (Spinner) findViewById(R.id.strenght_training_doneway);
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

        createDoneWay();

        setReadOnly();
    }

    private void createDoneWay() {

        final String array_spinner[]=new String[5];
        array_spinner[0]= EnumLocalizer.Translate(WS_Enums.ExerciseDoneWay.Default);
        array_spinner[1]= EnumLocalizer.Translate(WS_Enums.ExerciseDoneWay.Barbell);
        array_spinner[2]= EnumLocalizer.Translate(WS_Enums.ExerciseDoneWay.Dumbbell);
        array_spinner[3]= EnumLocalizer.Translate(WS_Enums.ExerciseDoneWay.Cable);
        array_spinner[4]=EnumLocalizer.Translate(WS_Enums.ExerciseDoneWay.Machine);

        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbDoneWay.setAdapter(adapter);

        cmbDoneWay.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        selectedItem.doneWay= WS_Enums.ExerciseDoneWay.values()[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cmbDoneWay.setSelection(selectedItem.doneWay.getCode());
        txtComment.setText(selectedItem.comment);
    }

    void setReadOnly()
    {
        boolean readOnly=!getEditMode();
        txtComment.setEnabled(!readOnly);
        cmbDoneWay.setEnabled(!readOnly && ApplicationState.getCurrent().isPremium());
    }
}
