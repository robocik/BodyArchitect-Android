package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.activities.WorkoutPlansActivity;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WorkoutPlanSearchCriteria;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 03.07.13
 * Time: 07:39
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlansSearchFragment extends DialogFragment
{
    private Spinner cmbTrainingType;
    private Spinner cmbPurpose;
    private Spinner cmbDifficult;
    RadioButton rbTopRated;
    RadioButton rbNewest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_workoutplans_search, container, false);
        cmbTrainingType= (Spinner) rootView.findViewById(R.id.cmbTrainingType);
        cmbPurpose= (Spinner) rootView.findViewById(R.id.cmbPurpose);
        cmbDifficult= (Spinner) rootView.findViewById(R.id.cmbDifficult);
        rbTopRated= (RadioButton) rootView.findViewById(R.id.rbTopRated);
        rbNewest= (RadioButton) rootView.findViewById(R.id.rbNewest);

        getDialog().setTitle(R.string.html_app_name);
        Button btnOK= (Button) rootView.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WorkoutPlanSearchCriteria criteria = new WorkoutPlanSearchCriteria();
                criteria.searchGroups.add(WS_Enums.WorkoutPlanSearchCriteriaGroup.Other);
                if(cmbTrainingType.getSelectedItemPosition()>0)
                {
                    criteria.workoutPlanType.add(WS_Enums.TrainingType.values()[cmbTrainingType.getSelectedItemPosition()-1]);
                }
                if(cmbPurpose.getSelectedItemPosition()>0)
                {
                    criteria.purposes.add(WS_Enums.WorkoutPlanPurpose.values()[cmbPurpose.getSelectedItemPosition()-1]);
                }
                if(cmbDifficult.getSelectedItemPosition()>0)
                {
                    criteria.difficults.add(WS_Enums.TrainingPlanDifficult.values()[cmbDifficult.getSelectedItemPosition()-1]);
                }
                criteria.sortOrder = rbNewest.isChecked() ? WS_Enums.SearchSortOrder.Newest : WS_Enums.SearchSortOrder.HighestRating;
                ((WorkoutPlansActivity)getActivity()).DoSearch(criteria);
                dismiss();
            }
        });
        Button btnCancel= (Button) rootView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        createTrainingType();
        createPurpose();
        createDifficult();



        //getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE); - causes problem with width of dialog
        return rootView;
    }


    private void createTrainingType() {
        final String array_spinner[]=new String[8];
        array_spinner[0]=getString(R.string.workout_plans_search_all_types);
        array_spinner[1]= EnumLocalizer.Translate(WS_Enums.TrainingType.Split);
        array_spinner[2]= EnumLocalizer.Translate(WS_Enums.TrainingType.FBW);
        array_spinner[3]= EnumLocalizer.Translate(WS_Enums.TrainingType.HIT);
        array_spinner[4]= EnumLocalizer.Translate(WS_Enums.TrainingType.ABW);
        array_spinner[5]= EnumLocalizer.Translate(WS_Enums.TrainingType.PushPull);
        array_spinner[6]= EnumLocalizer.Translate(WS_Enums.TrainingType.ACT);
        array_spinner[7]= EnumLocalizer.Translate(WS_Enums.TrainingType.Other);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this.getActivity(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbTrainingType.setAdapter(adapter);
        cmbTrainingType.setSelection(0);
    }

    private void createPurpose() {
        final String array_spinner[]=new String[7];
        array_spinner[0]=getString(R.string.workout_plans_search_any_purpose);
        array_spinner[1]= EnumLocalizer.Translate(WS_Enums.WorkoutPlanPurpose.NotSet);
        array_spinner[2]= EnumLocalizer.Translate(WS_Enums.WorkoutPlanPurpose.Strength);
        array_spinner[3]= EnumLocalizer.Translate(WS_Enums.WorkoutPlanPurpose.Mass);
        array_spinner[4]= EnumLocalizer.Translate(WS_Enums.WorkoutPlanPurpose.FatLost);
        array_spinner[5]= EnumLocalizer.Translate(WS_Enums.WorkoutPlanPurpose.Definition);
        array_spinner[6]= EnumLocalizer.Translate(WS_Enums.WorkoutPlanPurpose.Other);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this.getActivity(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbPurpose.setAdapter(adapter);
        cmbPurpose.setSelection(0);
    }
    private void createDifficult() {
        final String array_spinner[]=new String[5];
        array_spinner[0]=getString(R.string.workout_plans_search_any);
        array_spinner[1]= EnumLocalizer.Translate(WS_Enums.TrainingPlanDifficult.NotSet);
        array_spinner[2]= EnumLocalizer.Translate(WS_Enums.TrainingPlanDifficult.Beginner);
        array_spinner[3]= EnumLocalizer.Translate(WS_Enums.TrainingPlanDifficult.Advanced);
        array_spinner[4]= EnumLocalizer.Translate(WS_Enums.TrainingPlanDifficult.Professional);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this.getActivity(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbDifficult.setAdapter(adapter);
        cmbDifficult.setSelection(0);
    }
}
