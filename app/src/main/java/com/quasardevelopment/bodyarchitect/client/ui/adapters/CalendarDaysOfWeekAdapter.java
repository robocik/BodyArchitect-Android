package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;

import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 21.04.13
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class CalendarDaysOfWeekAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 7;  //from monday to sunday
    }

    @Override
    public Object getItem(int i) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView txt = new TextView(viewGroup.getContext());
        txt.setTextColor(viewGroup.getResources().getColor(R.color.main_fg));
        String dayName=getShortDayName(i);
        txt.setText(dayName);
        txt.setGravity(Gravity.CENTER);
        return txt;
    }

    public static String getShortDayName(int day) {
        Calendar c = Calendar.getInstance();
        int firstDay=Calendar.getInstance().getFirstDayOfWeek()-1;
        c.set(2011, 7, 1, 0, 0, 0);


        if(firstDay==0)
        {   //sunday
            c.add(Calendar.DAY_OF_MONTH, day-1);
        }
        else
        {     //monday
            c.add(Calendar.DAY_OF_MONTH, day);
        }
        return String.format("%ta", c);
    }
}
