package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.WorkoutPlanEntryActivity;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.WorkoutPlanDetailsExpandableListAdapter;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlan;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlanEntry;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 04.07.13
 * Time: 19:41
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlanInfoDetailsFragment extends Fragment implements TitleProvider {
    ExpandableListView lvPlan;
    TrainingPlan plan;

    @Override
    public String getTitle() {
        return MyApplication.getAppContext().getResources().getString(R.string.workout_plan_details_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_workout_plan_info_details, container, false);
        lvPlan= (ExpandableListView) rootView.findViewById(R.id.lvPlan);

        lvPlan.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                TrainingPlanEntry item= (TrainingPlanEntry) lvPlan.getExpandableListAdapter().getChild(groupPosition,childPosition);
                Intent intent = new Intent(getActivity(), WorkoutPlanEntryActivity.class);
                intent.putExtra("Item",item);
                startActivity(intent);
                return false;
            }
        });
        return rootView;
    }

    public void Fill(TrainingPlan plan) {
        this.plan=plan;
    }

    @Override
    public void onResume() {
        super.onResume();

        WorkoutPlanDetailsExpandableListAdapter adapter = new WorkoutPlanDetailsExpandableListAdapter(getActivity(),plan.days);
        lvPlan.setAdapter(adapter);
    }
}
