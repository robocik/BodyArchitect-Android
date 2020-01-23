package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import android.widget.Switch;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 20.07.13
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */
public class SettingsStrengthTrainingFragment extends android.support.v4.app.Fragment implements TitleProvider {

    Spinner cmbSortExercises;
    Spinner cmbCopySetsData;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getString(R.string.settings_strength_training_fragment_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_settings_strength_training, container, false);
        cmbSortExercises = (Spinner) rootView.findViewById(R.id.cmbSortExercises);
        cmbCopySetsData = (Spinner) rootView.findViewById(R.id.cmbCopySetsData);

        android.support.v7.widget.SwitchCompat twCopyValuesForNewSet = (android.support.v7.widget.SwitchCompat) rootView.findViewById(R.id.twCopyValuesForNewSet);
        twCopyValuesForNewSet.setChecked(Settings.getCopyValuesFromNewSet());
        twCopyValuesForNewSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Settings.setCopyValuesFromNewSet(checked);
            }
        });

        android.support.v7.widget.SwitchCompat twSupersetAsOne = (android.support.v7.widget.SwitchCompat) rootView.findViewById(R.id.twSupersetAsOne);
        twSupersetAsOne.setChecked(Settings.getTreatSuperSetsAsOne());
        twSupersetAsOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Settings.setTreatSuperSetsAsOne(checked);
            }
        });

        android.support.v7.widget.SwitchCompat tsStartTimer = (android.support.v7.widget.SwitchCompat) rootView.findViewById(R.id.tsStartTimer);
        tsStartTimer.setChecked(Settings.getStartTimer());
        tsStartTimer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Settings.setStartTimer(checked);
            }
        });

        createSortExercises();
        createCopySetsData();
        return rootView;
    }

    private void createSortExercises() {
        final String array_spinner[]=new String[2];
        array_spinner[0]= getString(R.string.settings_strength_training_fragment_sort_exercises_by_name);
        array_spinner[1]= getString(R.string.settings_strength_training_fragment_sort_exercises_by_shortcut);

        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( getActivity(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbSortExercises.setAdapter(adapter);
        cmbSortExercises.setSelection(Settings.getSortExercises());

        cmbSortExercises.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        Settings.setSortExercises(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }

    private void createCopySetsData() {
        final String array_spinner[]=new String[3];
        array_spinner[0]= getString(R.string.settings_strength_training_fragment_copy_all_data);
        array_spinner[1]= getString(R.string.settings_strength_training_fragment_copy_without_reps_weights);
        array_spinner[2]= getString(R.string.settings_strength_training_fragment_copy_exercises_only);

        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( getActivity(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbCopySetsData.setAdapter(adapter);
        cmbCopySetsData.setSelection(Settings.getCopyStrengthTrainingMode().getCode());

        cmbCopySetsData.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        Settings.setCopyStrengthTrainingMode(Settings.CopyStrengthTrainingMode.values()[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }
}
