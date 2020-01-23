package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.LapInfo;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 16.07.13
 * Time: 08:50
 * To change this template use File | Settings | File Templates.
 */
public class LapsAdapter extends ArrayAdapter<LapInfo>
{
    public LapsAdapter(Context context, int textViewResourceId,List<LapInfo> entries) {
        super(context, textViewResourceId, entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;



        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.lap_row, parent, false);
        }

        LapInfo lap=getItem(position);

        ImageView imgRecord = (ImageView)row.findViewById(R.id.imgRecord);
        TextView tbLapTime = (TextView)row.findViewById(R.id.tbLapTime);
        TextView tbTotalTime = (TextView)row.findViewById(R.id.tbTotalTime);
        TextView tbLapNumber = (TextView)row.findViewById(R.id.tbLapNumber);
        TextView tbDistanceType = (TextView)row.findViewById(R.id.tbDistanceType);



        tbLapNumber.setText(Integer.toString(lap.Nr));
        tbLapTime.setText(DateTimeHelper.fromSeconds(lap.LapTime.getStandardSeconds()));
        tbTotalTime.setText(DateTimeHelper.fromSeconds(lap.TotalTime.getStandardSeconds()));
        imgRecord.setVisibility(lap.BestLap?View.VISIBLE:View.GONE);
        tbDistanceType.setText(EnumLocalizer.DistanceType());
        return row;
    }
}
