package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.WorkoutPlanSetsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAListView;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlanEntry;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 05.07.13
 * Time: 07:09
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlanEntryActivity extends BANormalActivityBase
{
    TabHost tabHost;
    BAListView lstSets;
    TextView tbEmptyMessages;
    TextView tbRestTime;
    TextView tbDescription;
    TrainingPlanEntry entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_workout_plan_entry, null);
        setMainContent(inflate);

        tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();



        TabHost.TabSpec spec1=tabHost.newTabSpec(getString(R.string.workout_plan_entry_tab_sets));
        spec1.setContent(R.id.pnlWorkoutPlanEntrySets);
        spec1.setIndicator(getString(R.string.workout_plan_entry_tab_sets));


        TabHost.TabSpec spec2=tabHost.newTabSpec(getString(R.string.workout_plan_entry_tab_info));
        spec2.setIndicator(getString(R.string.workout_plan_entry_tab_info));
        spec2.setContent(R.id.pnlWorkoutPlanEntryDetails);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);

        lstSets= (BAListView) findViewById(R.id.lstSets);
        tbEmptyMessages =(TextView) findViewById(R.id.tbEmptyMessages);
        tbRestTime =(TextView) findViewById(R.id.tbRestTime);
        tbDescription =(TextView) findViewById(R.id.tbDescription);

        Intent intent=getIntent();
        entry= (TrainingPlanEntry) intent.getSerializableExtra("Item");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(entry.restSeconds!=null)
        {
            tbRestTime.setText(Integer.toString(entry.restSeconds)+" s");
        }
        else
        {
            tbRestTime.setText(R.string.not_set_bracket);
        }

        if(!TextUtils.isEmpty(entry.comment))
        {
            tbDescription.setText(entry.comment);
        }
        getSupportActionBar().setSubtitle(entry.exercise.getDisplayText().toUpperCase());

        WorkoutPlanSetsAdapter adapter = new WorkoutPlanSetsAdapter(this,R.id.tbReps,entry.sets);
        lstSets.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lstSets.setVisibility(entry.sets.size()>0?View.VISIBLE:View.GONE);
        tbEmptyMessages.setVisibility(entry.sets.size()>0?View.GONE:View.VISIBLE);
    }
}
