package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ch.lambdaj.function.matcher.Predicate;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.activities.ExerciseSelectorActivity;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.ExerciseDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 10.05.13
 * Time: 07:40
 * To change this template use File | Settings | File Templates.
 */
public class ExerciseSelectorExpandableListAdapter extends BaseExpandableListAdapter
{
    class ExerciseCategory
    {
        public String category;
        public ArrayList<ExerciseDTO> exercises = new ArrayList<ExerciseDTO>();
    }

    ArrayList<ExerciseCategory> exercises;
    private Context context;

    public ExerciseSelectorExpandableListAdapter(Context context, Collection<ExerciseDTO> items)
    {
        ArrayList<ExerciseDTO> exerciseList = new ArrayList<ExerciseDTO>(items);
        Collections.sort(exerciseList, new Comparator<ExerciseDTO>() {
            @Override
            public int compare(ExerciseDTO sup1, ExerciseDTO sup2) {
                if(Settings.getSortExercises()==0)
                {   //by name
                    return sup1.name.compareToIgnoreCase(sup2.name);
                }
                else
                {   //by shortcut
                    return sup1.shortcut.compareToIgnoreCase(sup2.shortcut);
                }

            }
        });

        exercises=new ArrayList<ExerciseCategory>();
        this.context=context;
        for (final ExerciseDTO ex : exerciseList)
        {

            ExerciseCategory category= Helper.SingleOrDefault(filter(new Predicate<ExerciseCategory>() {
                public boolean apply(ExerciseCategory item) {
                    return item.category.equalsIgnoreCase(EnumLocalizer.Translate(ex.exerciseType));
                }
            }, exercises));

            if(category==null)
            {
                category=new ExerciseCategory();
                category.category=EnumLocalizer.Translate(ex.exerciseType);
                exercises.add(category);
            }
            category.exercises.add(ex);
        }

        Collections.sort(exercises, new Comparator<ExerciseCategory>() {
            @Override
            public int compare(ExerciseCategory sup1, ExerciseCategory sup2) {
                return sup1.category.compareToIgnoreCase(sup2.category);
            }
        });
    }


    @Override
    public int getGroupCount() {
        return exercises.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return exercises.get(i).exercises.size();
    }

    @Override
    public Object getGroup(int i) {
        return exercises.get(i);
    }

    @Override
    public Object getChild(int i, int i2) {
        return exercises.get(i).exercises.get(i2);
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
        ExerciseCategory group = (ExerciseCategory) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.expandable_exercise_group_item, null);
        }
        TextView tv = (TextView) view.findViewById(R.id.elv_exercise_group_tb);
        tv.setText(group.category);
        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        final ExerciseDTO child = (ExerciseDTO) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.expandable_exercise_child_item, null);
        }
        ImageButton btnAddExercise = (ImageButton) view.findViewById(R.id.btnAddExercise);
        TextView tbExerciseName = (TextView) view.findViewById(R.id.elv_exercise_name_tb);
        TextView tbExerciseShortcut = (TextView) view.findViewById(R.id.elv_exercise_shortcut_tb);
        ImageView imgPublishStatus = (ImageView) view.findViewById(R.id.elv_exercise_publish_status_img);
        imgPublishStatus.setImageDrawable(getPublishStatus(child));
        tbExerciseName.setText(child.name);
        tbExerciseShortcut.setText(child.shortcut);

        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ExerciseSelectorActivity parent=(ExerciseSelectorActivity)context;
                parent.AddExercise(child);

            }
        });
        return view;

    }

    Drawable getPublishStatus(ExerciseDTO exercise)
    {
        if (exercise.profile == null)
        {
            return context.getResources().getDrawable(R.drawable.global);
        }
        if (!exercise.profile.isMe())
        {
            return context.getResources().getDrawable(R.drawable.favorites);
        }

        return context.getResources().getDrawable(R.drawable.private_status);
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
