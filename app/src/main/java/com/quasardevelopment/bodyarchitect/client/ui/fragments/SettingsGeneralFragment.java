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
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 20.07.13
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public class SettingsGeneralFragment extends Fragment implements TitleProvider {

    Spinner cmbLanguages;
    Spinner cmbRetrieveMonths;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getString(R.string.settings_general_fragment_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_settings_general, container, false);
        cmbLanguages = (Spinner) rootView.findViewById(R.id.cmbLanguage);
        cmbRetrieveMonths = (Spinner) rootView.findViewById(R.id.cmbRetrieveMonths);
        android.support.v7.widget.SwitchCompat tsAutoPause = (android.support.v7.widget.SwitchCompat) rootView.findViewById(R.id.twAutoPause);
        tsAutoPause.setChecked(Settings.isAutoPause());
        tsAutoPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Settings.setAutoPause(checked);
            }
        });
        createLanguage();
        createRetrieveMonths();
        return rootView;
    }

    private void createLanguage() {
        final String array_spinner[]=new String[3];
        array_spinner[0]= getString(R.string.settings_language_default);
        array_spinner[1]= "English";
        array_spinner[2]= "Polski";
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( getActivity(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbLanguages.setAdapter(adapter);
        String lang= Settings.getLanguage();
        if(lang.equalsIgnoreCase(Locale.US.toString()))
        {
            cmbLanguages.setSelection(1);
        }
        else if(lang.equalsIgnoreCase("pl_pl"))
        {
            cmbLanguages.setSelection(2);
        }
        else
        {
            cmbLanguages.setSelection(0);
        }


        cmbLanguages.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        String language="";
                        if(position==1)
                        {
                            language=Locale.US.toString();
                        }
                        else if(position==2)
                        {
                            language="pl_pl";
                        }

                        if(!language.equalsIgnoreCase(Settings.getLanguage()))
                        {
                            Settings.setLanguage(language);
                            ObjectsReposidory.clear();
                            if(!Locale.getDefault().toString().equalsIgnoreCase(language))
                            {
                                BAMessageBox.ShowInfo(R.string.settings_language_change_info, getActivity());
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }

    private void createRetrieveMonths() {
        final String array_spinner[]=new String[6];
        array_spinner[0]= "1";
        array_spinner[1]= "2";
        array_spinner[2]= "3";
        array_spinner[3]= "4";
        array_spinner[4]= "5";
        array_spinner[5]= "6";
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( getActivity(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbRetrieveMonths.setAdapter(adapter);
        cmbRetrieveMonths.setSelection(Settings.getNumberOfMonthToRetrieve()-1);


        cmbRetrieveMonths.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected( AdapterView<?> parent,View view,int position,long id)
                    {
                        Settings.setNumberOfMonthToRetrieve(position+1);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {  }
                });
    }
}
