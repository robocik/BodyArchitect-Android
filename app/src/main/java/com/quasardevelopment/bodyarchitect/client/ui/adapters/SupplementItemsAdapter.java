package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mobeta.android.dslv.DragSortListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.SuplementItemDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.VectorSuplementItemDTO;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 26.05.13
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public class SupplementItemsAdapter extends ArrayAdapter<SuplementItemDTO>
{
    VectorSuplementItemDTO entries;
    public SupplementItemsAdapter(Context context, int textViewResourceId,VectorSuplementItemDTO entries) {
        super(context, textViewResourceId, R.id.list_item_checkbox,entries);
        this.entries=entries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.supplement_item_row, parent, false);
        }

        SuplementItemDTO item=entries.get(position);
        boolean isAdvancedEdit=((DragSortListView)parent).getChoiceMode()==AbsListView.CHOICE_MODE_MULTIPLE;

        TextView tbSupplementName = (TextView)row.findViewById(R.id.supplement_name);
        TextView tbSupplementDosage = (TextView)row.findViewById(R.id.supplement_dosage);
        TextView tbSupplementDosageType = (TextView)row.findViewById(R.id.supplement_dosage_type);
        TextView tbTime = (TextView)row.findViewById(R.id.supplement_time);
        TextView tbTimeType = (TextView)row.findViewById(R.id.supplement_time_type);

        CheckedTextView chkCheck =(CheckedTextView)row.findViewById(R.id.list_item_checkbox);
        chkCheck.setVisibility(isAdvancedEdit? LinearLayout.VISIBLE:LinearLayout.GONE);

        tbSupplementName.setText(getSupplementName(item));
        DecimalFormat df = new DecimalFormat("#.##");
        tbSupplementDosage.setText(df.format(item.dosage));
        tbSupplementDosageType.setText(EnumLocalizer.Translate(item.dosageType));

        DateTimeFormatter shortTimeFormatter=DateTimeFormat.shortTime();
        tbTime.setText(item.time.dateTime.toString(shortTimeFormatter));
        tbTimeType.setText(EnumLocalizer.Translate(item.time.timeType));
        return row;
    }

    String getSupplementName(SuplementItemDTO item)
    {
        String name = "";

        if (item != null && item.suplement != null)
        {
            name= item.suplement.name;
        }
        if(item != null && !TextUtils.isEmpty(item.name))
        {
            name += ": " + item.name;
        }
        return name;
    }
}
