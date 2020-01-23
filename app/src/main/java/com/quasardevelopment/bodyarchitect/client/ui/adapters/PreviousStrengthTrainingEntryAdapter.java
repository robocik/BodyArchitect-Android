package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ch.lambdaj.function.matcher.Predicate;
import com.mobeta.android.dslv.DragSortListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.util.SuperSetViewManager;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingItemDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.VectorStrengthTrainingItemDTO;

import java.util.Collection;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 22.05.13
 * Time: 09:02
 * To change this template use File | Settings | File Templates.
 */
public class PreviousStrengthTrainingEntryAdapter extends ArrayAdapter<StrengthTrainingItemDTO>
{
    VectorStrengthTrainingItemDTO entries;
    SuperSetViewManager superSetManager;
    public PreviousStrengthTrainingEntryAdapter(Context context, int textViewResourceId,VectorStrengthTrainingItemDTO entries) {
        super(context, textViewResourceId, R.id.list_item_checkbox,entries);
        this.entries=entries;
        superSetManager=new SuperSetViewManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.previous_strength_training_item_row, parent, false);
        }

        boolean isAdvancedEdit=((DragSortListView)parent).getChoiceMode()==AbsListView.CHOICE_MODE_MULTIPLE;

        ImageView imgRecord = (ImageView)row.findViewById(R.id.strength_training_exercise_item_record);
        TextView tbExerciseType = (TextView)row.findViewById(R.id.strength_training_exercise_item_type);
        TextView tbSets = (TextView)row.findViewById(R.id.strength_training_exercise_sets);
        TextView tbExerciseName = (TextView)row.findViewById(R.id.strength_training_exercise_name);
        TextView tbPosition = (TextView)row.findViewById(R.id.strength_training_exercise_position);

        CheckedTextView chkCheck =(CheckedTextView)row.findViewById(R.id.list_item_checkbox);
        chkCheck.setVisibility(isAdvancedEdit? LinearLayout.VISIBLE:LinearLayout.GONE);

        StrengthTrainingItemDTO item=entries.get(position);
        imgRecord.setVisibility(isRecord(item)? LinearLayout.VISIBLE:LinearLayout.GONE);
        tbExerciseType.setText(EnumLocalizer.Translate(item.exercise.exerciseType));
        tbExerciseName.setText(item.exercise.getDisplayText());
        tbPosition.setText(String.format("%d",position+1));
        if(TextUtils.isEmpty(item.superSetGroup))
        {
            tbPosition.setTextColor(getContext().getResources().getColor(R.color.subtle));
        }
        else
        {
            tbPosition.setTextColor(superSetManager.GetSuperSetColor(item.superSetGroup));
        }

        tbSets.setText(getSetsString(item.series));
        return row;
    }

    String getSetsString(Collection<SerieDTO> sets)
    {
        StringBuilder builder = new StringBuilder();
        for (SerieDTO set : sets)
        {
             builder.append(String.format("%s  ", Helper.GetDisplayText(set,false)));
        }
        return builder.toString().trim();
    }

    boolean isRecord(StrengthTrainingItemDTO item)
    {
        return filter(new Predicate<SerieDTO>() {
            public boolean apply(SerieDTO item) {
                return item.isRecord;
            }
        }, item.series).size()>0;
    }
}
