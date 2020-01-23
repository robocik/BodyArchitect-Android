package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.activities.StatisticsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 13.06.13
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsAdapter extends ArrayAdapter<StatisticsActivity.StatisticItem> {



    public StatisticsAdapter(Context context, int textViewResourceId,List<StatisticsActivity.StatisticItem> statistics) {
        super(context, textViewResourceId,new ArrayList<StatisticsActivity.StatisticItem>());

        ArrayList<StatisticsActivity.StatisticItem> messages = new ArrayList<StatisticsActivity.StatisticItem>(statistics);
        Collections.sort(messages, new Comparator<StatisticsActivity.StatisticItem>() {
            @Override
            public int compare(StatisticsActivity.StatisticItem sup1, StatisticsActivity.StatisticItem sup2) {
                return sup1.name.compareTo(sup2.name);
            }
        });
        for (StatisticsActivity.StatisticItem entry:messages)
        {
            add(entry);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;



        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.statistic_row, parent, false);
        }

        ImageView imgRank= (ImageView) row.findViewById(R.id.imgRank);
        TextView tbName= (TextView) row.findViewById(R.id.tbName);
        TextView tbItems= (TextView) row.findViewById(R.id.tbItems);
        TextView tbDescription= (TextView) row.findViewById(R.id.tbDescription);
        StatisticsActivity.StatisticItem item = getItem(position);
        imgRank.setImageDrawable(getContext().getResources().getDrawable(item.statusIcon));
        tbName.setText(item.name);
        tbDescription.setText(item.statusDescription);
        tbItems.setText(item.value);
        return row;
    }
}
