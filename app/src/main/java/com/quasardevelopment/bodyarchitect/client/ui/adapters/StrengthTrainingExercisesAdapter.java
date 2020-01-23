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
import com.quasardevelopment.bodyarchitect.client.util.SuperSetViewManager;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.StrengthTrainingItemDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.VectorStrengthTrainingItemDTO;

import static ch.lambdaj.Lambda.filter;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 24.04.13
 * Time: 20:28
 * To change this template use File | Settings | File Templates.
 */
public class StrengthTrainingExercisesAdapter extends ArrayAdapter<StrengthTrainingItemDTO>
{
    VectorStrengthTrainingItemDTO entries;
    SuperSetViewManager superSetManager;
    public StrengthTrainingExercisesAdapter(Context context, int textViewResourceId,VectorStrengthTrainingItemDTO entries) {
        super(context, textViewResourceId,R.id.list_item_checkbox,entries);
        this.entries=entries;
        superSetManager=new SuperSetViewManager();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.strength_training_exercise_row, parent, false);
        }

        boolean isAdvancedEdit=((DragSortListView)parent).isDragEnabled();

        ImageView imgRecord = (ImageView)row.findViewById(R.id.strength_training_exercise_item_record);
        ImageView imgDragDrop = (ImageView)row.findViewById(R.id.img_drag_handle);
        TextView tbExerciseType = (TextView)row.findViewById(R.id.strength_training_exercise_item_type);
        TextView tbSetsCount = (TextView)row.findViewById(R.id.strength_training_exercise_item_sets_count);
        TextView tbExerciseName = (TextView)row.findViewById(R.id.strength_training_exercise_name);
        TextView tbPosition = (TextView)row.findViewById(R.id.strength_training_exercise_position);

        CheckedTextView chkCheck =(CheckedTextView)row.findViewById(R.id.list_item_checkbox);
        imgDragDrop.setVisibility(isAdvancedEdit? LinearLayout.VISIBLE:LinearLayout.GONE);
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

        tbSetsCount.setText(String.format(getContext().getString(R.string.strength_training_exercises_adapter_lbl_sets_count),item.series.size()));
        return row;
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
