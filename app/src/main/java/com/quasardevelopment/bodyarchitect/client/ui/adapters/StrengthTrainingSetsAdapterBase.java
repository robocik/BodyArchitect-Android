package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mobeta.android.dslv.DragSortListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SerieDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.VectorSerieDTO;
import org.joda.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 17.05.13
 * Time: 16:33
 * To change this template use File | Settings | File Templates.
 */
public abstract class StrengthTrainingSetsAdapterBase extends ArrayAdapter<SerieDTO>
{
    VectorSerieDTO sets;
    int textViewResourceId;

    public StrengthTrainingSetsAdapterBase(Context context, int textViewResourceId,VectorSerieDTO sets) {
        super(context, textViewResourceId,sets);
        this.sets=sets;
        this.textViewResourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false);
        }

        SerieDTO item=sets.get(position);

        TextView tbRestTime = (TextView)row.findViewById(R.id.strength_training_set_resttime);
        ImageView imgRecord = (ImageView)row.findViewById(R.id.strength_training_set_item_record);
        ImageView imgDragDrop = (ImageView)row.findViewById(R.id.img_drag_handle);
        CheckedTextView chkCheck =(CheckedTextView)row.findViewById(R.id.list_item_checkbox);

        boolean isAdvancedEdit=((DragSortListView)parent).isDragEnabled();
        imgDragDrop.setVisibility(isAdvancedEdit? LinearLayout.VISIBLE:LinearLayout.GONE);
        chkCheck.setVisibility(isAdvancedEdit? LinearLayout.VISIBLE:LinearLayout.GONE);

        imgRecord.setVisibility(item.isRecord?LinearLayout.VISIBLE: LinearLayout.GONE);
        String resttime=getRestTime(item,position);
        if(resttime!=null)
        {
            tbRestTime.setText(resttime);
            tbRestTime.setVisibility(LinearLayout.VISIBLE);
        }
        else
        {
            tbRestTime.setVisibility(LinearLayout.GONE);
        }

        buildItemRow(item,row,position);
        return row;
    }

    protected abstract void buildItemRow(SerieDTO set,View row,int position );

    String getRestTime(SerieDTO set,int position)
    {
        if(position>0)
        {
            SerieDTO previousSet=set.strengthTrainingItem.series.get(position-1);
            if(previousSet.endTime!=null && set.startTime!=null)
            {
                Duration duration = new Duration(previousSet.endTime, set.startTime);
//                int seconds=set.startTime-previousSet.endTime;
                int seconds=(int)duration.getStandardSeconds();
                if(seconds>0)
                {
                    String restPause=set.isRestPause?"rest pause %s":"rest time %s";//todo:translate

                    return String.format(restPause, DateTimeHelper.fromSeconds(seconds));
                }
            }
            return set.isRestPause?"rest pause":null;
        }
        return null;
    }
}
