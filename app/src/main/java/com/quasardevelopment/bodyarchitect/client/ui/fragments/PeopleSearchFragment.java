package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.activities.PeopleActivity;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.UserSearchCriteria;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 11.06.13
 * Time: 07:42
 * To change this template use File | Settings | File Templates.
 */
public class PeopleSearchFragment  extends DialogFragment
{
    Spinner cmbSortOrder;
    Spinner cmbGender;

    PeopleActivity peopleActivity;

    public PeopleSearchFragment() {
    }

    public void setPeopleActivity(PeopleActivity peopleActivity) {
       this.peopleActivity=peopleActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_people_search, container, false);
        cmbGender= (Spinner) rootView.findViewById(R.id.cmbGender);
        cmbSortOrder= (Spinner) rootView.findViewById(R.id.cmbSortOrder);

        Button btnOK= (Button) rootView.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSearchCriteria criteria = new UserSearchCriteria();
                criteria.userSearchGroups.add(WS_Enums.UserSearchGroup.Others);
                switch (cmbSortOrder.getSelectedItemPosition())
                {
                    case 0:
                       criteria.sortOrder= WS_Enums.UsersSortOrder.ByLastLoginDate;
                        break;
                    case 1:
                        criteria.sortOrder= WS_Enums.UsersSortOrder.ByTrainingDaysCount;
                        break;
                    case 2:
                        criteria.sortOrder= WS_Enums.UsersSortOrder.ByName;
                        break;
                }
                if(cmbGender.getSelectedItemPosition()==0 || cmbGender.getSelectedItemPosition()==1)
                {
                    criteria.genders.add(WS_Enums.Gender.Male);
                }
                if(cmbGender.getSelectedItemPosition()==0 || cmbGender.getSelectedItemPosition()==2)
                {
                    criteria.genders.add(WS_Enums.Gender.Female);
                }
                peopleActivity.DoSearch(criteria);
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

        createSortOrder();
        createGender();

        getDialog().setTitle(R.string.html_app_name);
        return rootView;
    }

    private void createSortOrder() {
        final String array_spinner[]=new String[3];
        array_spinner[0]=getString(R.string.people_search_fragment_order_by_login_date);
        array_spinner[1]= getString(R.string.people_search_fragment_order_by_entries_count);
        array_spinner[2]= getString(R.string.people_search_fragment_order_by_name);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this.getActivity(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbSortOrder.setAdapter(adapter);
        cmbSortOrder.setSelection(1);
    }

    private void createGender() {
        final String array_spinner[]=new String[3];
        array_spinner[0]=getString(R.string.workout_plans_search_any);
        array_spinner[1]= getString(R.string.Gender_Male);
        array_spinner[2]= getString(R.string.Gender_Female);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this.getActivity(),android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbGender.setAdapter(adapter);
        cmbGender.setSelection(0);
    }
}
