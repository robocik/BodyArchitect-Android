package com.quasardevelopment.bodyarchitect.client.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.activities.CalendarView;
import com.quasardevelopment.bodyarchitect.client.ui.controls.TrainingDaySelectorControl;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.Functions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 20.04.13
 * Time: 11:31
 * To change this template use File | Settings | File Templates.
 */
public class TodayFragment extends Fragment implements Functions.IAction {

    TextView btnCalendar;
    ImageButton btnRefresh;
    TrainingDaySelectorControl ctrlDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_today, container, false);
        ctrlDay= (TrainingDaySelectorControl) rootView.findViewById(R.id.dayCtrl);
        ctrlDay.setOnTrainingDayRetrieved(this);

        btnCalendar = (TextView)rootView.findViewById(R.id.btnCalendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent myIntent = new Intent(TodayFragment.this.getActivity(), CalendarView.class);
                TodayFragment.this.startActivity(myIntent);
            }
        });
        btnRefresh = (ImageButton)rootView.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ApplicationState.getCurrent().clearTrainingDays();
                ctrlDay.Fill(DateTimeHelper.toDate(DateTime.now(DateTimeZone.UTC)));
                Action();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        ctrlDay.Fill(DateTimeHelper.toDate(DateTime.now(DateTimeZone.UTC)));
        Action();
    }

    @Override
    public void Action() {
        btnRefresh.setVisibility(ApplicationState.getCurrent().isOffline || ApplicationState.getCurrent().isTrainingDaysLoading()?View.GONE:View.VISIBLE);
        btnCalendar.setVisibility(ApplicationState.getCurrent().isTrainingDaysLoading()?View.GONE:View.VISIBLE);
    }
}
