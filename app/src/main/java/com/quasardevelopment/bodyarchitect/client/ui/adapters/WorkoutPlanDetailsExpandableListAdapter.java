package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.activities.WorkoutPlanInfoActivity;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlanDay;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlanEntry;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.TrainingPlanSerie;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 04.07.13
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class WorkoutPlanDetailsExpandableListAdapter extends BaseExpandableListAdapter
{
    List<TrainingPlanDay> days;
    private Context context;

    public WorkoutPlanDetailsExpandableListAdapter(Context context, List<TrainingPlanDay> items)
    {
        this.days=items;
        this.context=context;
    }


    @Override
    public int getGroupCount() {
        return days.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return days.get(i).entries.size();
    }

    @Override
    public Object getGroup(int i) {
        return days.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return days.get(i).entries.get(i2);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        final  TrainingPlanDay group = (TrainingPlanDay) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.expandable_workout_plan_group_item, null);
        }
        TextView tbName = (TextView) view.findViewById(R.id.tbName);
        ImageButton btnAdd = (ImageButton) view.findViewById(R.id.btnAdd);
        tbName.setText(group.name);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutPlanInfoActivity parent=(WorkoutPlanInfoActivity)context;
                parent.UseTrainingPlanDay(group);

            }
        });
        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        final TrainingPlanEntry child = (TrainingPlanEntry) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.expandable_workout_plan_child_item, null);
        }
        TextView tbExerciseName = (TextView) view.findViewById(R.id.tbName);
        TextView tbExerciseType = (TextView) view.findViewById(R.id.tbExerciseType);
        TextView tbExerciseNumber = (TextView) view.findViewById(R.id.tbExerciseNumber);
        TextView tbExerciseSets = (TextView) view.findViewById(R.id.tbExerciseSets);

        tbExerciseNumber.setText(Integer.toString(childPosition+1));
        tbExerciseName.setText(child.exercise.getDisplayText());
        tbExerciseType.setText(EnumLocalizer.Translate(child.exercise.exerciseType));
        tbExerciseSets.setText(getSetsInfo(child));
        return view;

    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    String getSetsInfo(TrainingPlanEntry entry)
    {
        StringBuilder builder = new StringBuilder();
        for (TrainingPlanSerie set : entry.sets)
        {
            builder.append(String.format("%s, ",Helper.GetDisplayText(set)));
//            builder.AppendFormat("{0}, ", set.GetDisplayText());
        }
        //first remove two last characters (, and space)
        return builder.length()>=2? builder.delete(builder.length()-2,builder.length()-1).toString():"";
    }
}
