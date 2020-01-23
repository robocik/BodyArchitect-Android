package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.TitleProvider;
import com.quasardevelopment.bodyarchitect.client.ui.activities.WorkoutPlanInfoActivity;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.WorkoutPlanAdapter;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlan;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 01.07.13
 * Time: 09:59
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlansFragment extends Fragment implements TitleProvider
{
    public static final String TITLE="title";
    public static final String EMPTY_MESSAGE="empty_message";
    String title;
    Activity activity;
    WorkoutPlanAdapter adapter;
    ListView lstUsers;
    TextView tbEmpty;
    ArrayList<TrainingPlan> plans;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.activity=getActivity();
        adapter = new WorkoutPlanAdapter(this.activity,R.id.tbName,new ArrayList<TrainingPlan>());

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_people, container, false);
        lstUsers= (ListView) rootView.findViewById(R.id.lstUsers);
        lstUsers.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),false, false));
        tbEmpty= (TextView) rootView.findViewById(R.id.tbEmpty);
        title = getArguments().getString(TITLE);
        String emptyMessage = getArguments().getString(EMPTY_MESSAGE);
        tbEmpty.setText(emptyMessage);

        lstUsers.setAdapter(adapter);
        lstUsers.setItemsCanFocus(true);
        lstUsers.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(getActivity(), WorkoutPlanInfoActivity.class);
                TrainingPlan item= (TrainingPlan) lstUsers.getAdapter().getItem(i);
                myIntent.putExtra("Item",item);
                startActivity(myIntent);
            }
        });
        fillPlans(plans);
        tbEmpty.setVisibility(adapter==null || adapter.getCount()==0?View.VISIBLE:View.GONE);
        if(ApplicationState.getCurrent().isOffline)
        {
            tbEmpty.setText(R.string.info_feature_offline_mode);
        }
        lstUsers.setVisibility(adapter!=null && adapter.getCount()>0?View.VISIBLE:View.GONE);
        return rootView;
    }


    @Override
    public String getTitle() {
        return title;
    }

    public void setItems(ArrayList<TrainingPlan> plans) {
        this.plans=plans;
        if(adapter!=null)
        {
            fillPlans(plans);

        }
    }

    private void fillPlans(Collection<TrainingPlan> plans) {
        adapter.clear();
        if(plans!=null)
        {
            for (TrainingPlan entry:plans)
            {
                adapter.add( entry);
            }
            adapter.notifyDataSetChanged();
            if(tbEmpty!=null)
            {
                tbEmpty.setVisibility(adapter==null || adapter.getCount()==0? View.VISIBLE:View.GONE);
                lstUsers.setVisibility(adapter!=null && adapter.getCount()>0?View.VISIBLE:View.GONE);
            }
        }
    }
}
