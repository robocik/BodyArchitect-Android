package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.MyPlaceLightDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 24.05.13
 * Time: 08:19
 * To change this template use File | Settings | File Templates.
 */
public class MyPlacesAdapter  extends ArrayAdapter<MyPlaceLightDTO>
{
    public MyPlacesAdapter(Context context,Collection<MyPlaceLightDTO> entries) {
        super(context,R.layout.my_place_item, R.id.my_place_item_name,new ArrayList<MyPlaceLightDTO>(entries));
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.my_place_item, parent, false);
        }

        TextView tbName = (TextView)row.findViewById(R.id.my_place_item_name);
        LinearLayout pnlColor = (LinearLayout)row.findViewById(R.id.my_place_item_color_box);
        MyPlaceLightDTO item=getItem(position);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            tbName.setTextColor(getContext().getResources().getColor(R.color.alternate_fg));
        }

        tbName.setText(item.name);

        pnlColor.setBackgroundColor(Helper.toColor(item.color));
        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;


        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.my_place_item, parent, false);
        }

        TextView tbName = (TextView)row.findViewById(R.id.my_place_item_name);
        LinearLayout pnlColor = (LinearLayout)row.findViewById(R.id.my_place_item_color_box);
        MyPlaceLightDTO item=getItem(position);

        tbName.setText(item.name);

        pnlColor.setBackgroundColor(Helper.toColor(item.color));
        return row;
    }
}
