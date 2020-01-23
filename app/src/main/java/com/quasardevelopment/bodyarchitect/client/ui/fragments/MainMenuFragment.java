package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.activities.*;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 31.05.13
 * Time: 18:42
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main_menu, container, false);
        TextView btnPeople= (TextView) rootView.findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PeopleActivity.class);
                startActivity(intent);
            }
        });


        TextView btnTipsTricks= (TextView) rootView.findViewById(R.id.btnTipsTricks);
        btnTipsTricks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TipsTricksActivity.class);
                startActivity(intent);
            }
        });


        TextView btnWorkoutPlans= (TextView) rootView.findViewById(R.id.btnWorkoutPlans);
        btnWorkoutPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WorkoutPlansActivity.class);
                startActivity(intent);
            }
        });

        TextView btnBodyInstructor= (TextView) rootView.findViewById(R.id.btnBodyInstructor);
        btnBodyInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!UpgradeAccountFragment.EnsureAccountType(getActivity(), WS_Enums.AccountType.Instructor))
                {
                     return;
                }
                Intent intent = new Intent(getActivity(), BodyInstructorActivity.class);
                startActivity(intent);
            }
        });

        TextView btnMoreFeatures= (TextView) rootView.findViewById(R.id.btnMoreFeatures);
        btnMoreFeatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MoreInfoActivity.class);
                startActivity(intent);
            }
        });

        TextView btnAbout= (TextView) rootView.findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
