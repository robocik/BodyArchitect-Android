package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlanSerie;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 05.07.13
 * Time: 07:45
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlanSetsAdapter extends ArrayAdapter<TrainingPlanSerie>
{
    public WorkoutPlanSetsAdapter(Context context, int textViewResourceId,List<TrainingPlanSerie> entries) {
        super(context, textViewResourceId, R.id.tbReps,entries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.workout_plan_set_row, parent, false);
        }

        TrainingPlanSerie item=getItem(position);

        TextView tbReps = (TextView)row.findViewById(R.id.tbReps);
        TextView tbRepsType = (TextView)row.findViewById(R.id.tbRepsType);
        TextView tbDropSet = (TextView)row.findViewById(R.id.tbDropSet);
        TextView tbSuperSlow = (TextView)row.findViewById(R.id.tbSuperSlow);
        TextView tbRestPause = (TextView)row.findViewById(R.id.tbRestPause);
        TextView tbSetNumber = (TextView)row.findViewById(R.id.tbSetNumber);
        TextView tbDescription = (TextView)row.findViewById(R.id.tbDescription);

        tbSetNumber.setText(Integer.toString(position+1));
        if(item.repetitionNumberMax!=null || item.repetitionNumberMin!=null)
        {
            tbReps.setText(Helper.ToStringRepetitionsRange(item));
        }
        else
        {
            tbReps.setText("");
        }

        if(!item.dropSet.equals(WS_Enums.DropSetType.None))
        {
            tbDropSet.setText(EnumLocalizer.Translate(item.dropSet));
        }
        else
        {
            tbDropSet.setText("");
        }
        if(!item.repetitionsType.equals(WS_Enums.SetType.Normalna))
        {
            tbRepsType.setText(EnumLocalizer.Translate(item.repetitionsType));
        }
        else
        {
            tbRepsType.setText("");
        }
        if(!TextUtils.isEmpty(item.comment))
        {
            tbDescription.setText(item.comment);
        }
        else
        {
            tbDescription.setText("");
        }
        tbRestPause.setVisibility(item.isRestPause?View.VISIBLE:View.GONE);
        tbSuperSlow.setVisibility(item.isSuperSlow?View.VISIBLE:View.GONE);
        return row;
    }
}
