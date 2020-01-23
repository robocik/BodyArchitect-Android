package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlan;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 01.07.13
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlanAdapter  extends ArrayAdapter<TrainingPlan>
{

    public WorkoutPlanAdapter(Context context, int textViewResourceId,Collection<TrainingPlan> entries) {
        super(context, textViewResourceId,new ArrayList<TrainingPlan>());
        //ArrayList<TrainingPlan> messages = new ArrayList<TrainingPlan>(entries);
        for (TrainingPlan entry:entries)
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
            row = inflater.inflate(R.layout.workoutplan_row, parent, false);
        }

        ImageView imgStatus= (ImageView) row.findViewById(R.id.imgStatus);
        TextView tbName= (TextView) row.findViewById(R.id.tbName);
        TextView tbDifficult= (TextView) row.findViewById(R.id.tbDifficult);
        TextView tbPurpose= (TextView) row.findViewById(R.id.tbPurpose);
        TextView tbTrainingType= (TextView) row.findViewById(R.id.tbTrainingType);
        TrainingPlan item=getItem(position);

        Drawable statusImg=getContext().getResources().getDrawable(item.status.equals(WS_Enums.PublishStatus.Published)?R.drawable.public_status:R.drawable.private_status);
        imgStatus.setImageDrawable(statusImg);
        tbName.setText(item.name);
        tbDifficult.setText(EnumLocalizer.Translate(item.difficult));
        tbPurpose.setText(EnumLocalizer.Translate(item.purpose));
        tbTrainingType.setText(EnumLocalizer.Translate(item.trainingType));
        return row;
    }
}
