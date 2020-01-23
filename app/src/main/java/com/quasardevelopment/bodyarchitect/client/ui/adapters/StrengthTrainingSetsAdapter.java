package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.VectorSerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 04.05.13
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
public class StrengthTrainingSetsAdapter extends StrengthTrainingSetsAdapterBase
{

    public StrengthTrainingSetsAdapter(Context context, int textViewResourceId,VectorSerieDTO sets) {
        super(context, textViewResourceId,sets);
    }

    @Override
    protected void buildItemRow(SerieDTO item, View row,int position) {
        TextView tbSetTypeInfo = (TextView)row.findViewById(R.id.strength_training_set_item_type_info);
        TextView tbDropSetInfo = (TextView)row.findViewById(R.id.strength_training_set_item_dropset_info);
        TextView tbSuperSlowInfo = (TextView)row.findViewById(R.id.strength_training_set_item_superslow_info);
        TextView tbSetNumber = (TextView)row.findViewById(R.id.strength_training_set_position);
        TextView tbReps = (TextView)row.findViewById(R.id.strength_training_set_reps);
        TextView tbWeight = (TextView)row.findViewById(R.id.strength_training_set_weight);
        TextView tbWeightType = (TextView)row.findViewById(R.id.strength_training_set_weight_type);


        tbReps.setText(item.repetitionNumber!=null?String.format("%.0f",item.repetitionNumber):"");
        if(item.weight!=null)
        {
            tbWeight.setText(Helper.ToDisplayWeightText(item.weight));
            tbWeightType.setText(EnumLocalizer.WeightType());
            tbWeight.setVisibility(View.VISIBLE);
            tbWeightType.setVisibility(View.VISIBLE);
        }
        else
        {
            tbWeight.setVisibility(View.GONE);
            tbWeightType.setVisibility(View.GONE);
        }

        tbSetNumber.setText(String.format(getContext().getString(R.string.strength_training_item_activity_sets),position+1));


        String dropSetInfo=getDropSetInfo(item);
        if(dropSetInfo!=null)
        {
            tbDropSetInfo.setText(dropSetInfo);
            tbDropSetInfo.setVisibility(LinearLayout.VISIBLE);
        }
        else
        {
            tbDropSetInfo.setVisibility(LinearLayout.GONE);
        }

        if(item.isSuperSlow)
        {
            tbSuperSlowInfo.setText("SS");
            tbSuperSlowInfo.setVisibility(LinearLayout.VISIBLE);
        }
        else
        {
            tbSuperSlowInfo.setVisibility(LinearLayout.GONE);
        }

        String setTypeInfo=getSetTypeInfo(item);
        if(setTypeInfo!=null)
        {
            tbSetTypeInfo.setText(setTypeInfo.toLowerCase());
            tbSetTypeInfo.setVisibility(LinearLayout.VISIBLE);
        }
        else
        {
            tbSetTypeInfo.setVisibility(LinearLayout.GONE);
        }
    }

    String getSetTypeInfo(SerieDTO set)
    {
        if(set.setType!= WS_Enums.SetType.Normalna)
        {
            return EnumLocalizer.Translate(set.setType);
        }
        return null;
    }

    String getDropSetInfo(SerieDTO set)
    {
        if(set.dropSet!= WS_Enums.DropSetType.None)
        {
            return String.format(getContext().getString(R.string.strength_training_item_activity_dropset),set.dropSet.getCode());
        }
        return null;
    }


}
