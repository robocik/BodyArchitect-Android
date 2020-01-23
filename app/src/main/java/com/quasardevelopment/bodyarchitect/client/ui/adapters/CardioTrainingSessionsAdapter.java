package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.VectorSerieDTO;
import org.joda.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 17.05.13
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
public class CardioTrainingSessionsAdapter extends StrengthTrainingSetsAdapterBase
{
    VectorSerieDTO sets;

    public CardioTrainingSessionsAdapter(Context context, int textViewResourceId,VectorSerieDTO sets) {
        super(context, textViewResourceId,sets);
        this.sets=sets;
    }



    @Override
    protected void buildItemRow(SerieDTO item, View row, int position) {
        TextView tbPosition = (TextView)row.findViewById(R.id.cardio_training_session_item_position);
        TextView tbSessionTime = (TextView)row.findViewById(R.id.cardio_training_session_time);
        TextView tbDistance = (TextView)row.findViewById(R.id.cardio_training_session_item_distance);
        TextView tbDistanceType = (TextView)row.findViewById(R.id.cardio_training_session_item_distance_type);
        TextView tbCalories = (TextView)row.findViewById(R.id.cardio_training_session_item_calories);
        TextView tbCaloriesType = (TextView)row.findViewById(R.id.cardio_training_session_item_calories_type);

        tbPosition.setText(String.format(getContext().getString(R.string.strength_training_cardio_item_activity_sets),position+1));
        if(item.repetitionNumber!=null)
        {
            tbDistance.setText(Helper.ToDisplayText(item.repetitionNumber));
            tbDistanceType.setText(EnumLocalizer.DistanceType());
            tbDistance.setVisibility(LinearLayout.VISIBLE);
            tbDistanceType.setVisibility(LinearLayout.VISIBLE);
        }
        else
        {
            tbDistance.setVisibility(LinearLayout.GONE);
            tbDistanceType.setVisibility(LinearLayout.GONE);
        }

        if(item.calculatedValue!=null)
        {
            tbCalories.setText(String.format("%.0f",item.calculatedValue));
            tbCalories.setVisibility(LinearLayout.VISIBLE);
            tbCaloriesType.setVisibility(LinearLayout.VISIBLE);
        }
        else
        {
            tbCalories.setVisibility(LinearLayout.GONE);
            tbCaloriesType.setVisibility(LinearLayout.GONE);
        }
        tbSessionTime.setText(getCardioSessionTime(item));
    }

    public static String getCardioSessionTime(SerieDTO set)
    {
        if (set.startTime!=null && set.endTime!=null)
        {
            Duration duration = new Duration(set.startTime, set.endTime);
            int seconds=(int)duration.getStandardSeconds();
            return DateTimeHelper.fromSeconds(seconds);
        }
        return DateTimeHelper.fromSeconds(0);
    }
}
