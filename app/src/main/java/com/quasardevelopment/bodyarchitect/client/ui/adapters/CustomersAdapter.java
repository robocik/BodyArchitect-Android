package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.CustomerDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 06.07.13
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class CustomersAdapter extends ArrayAdapter<CustomerDTO>
{

    public CustomersAdapter(Context context, int textViewResourceId,Collection<CustomerDTO> entries) {
        super(context, textViewResourceId,new ArrayList<CustomerDTO>());
        for (CustomerDTO entry:entries)
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
            row = inflater.inflate(R.layout.customer_item, parent, false);
        }

        BAPicture imgPicture = (BAPicture)row.findViewById(R.id.imgUser);
        ImageView imgVirtualCustomer = (ImageView)row.findViewById(R.id.imgVirtualCustomer);
        TextView tbUsername = (TextView)row.findViewById(R.id.tbUsername);

        CustomerDTO item=getItem(position);
        tbUsername.setText(item.getFullName());
        imgVirtualCustomer.setVisibility(item.isVirtual?View.VISIBLE:View.GONE);
        imgPicture.fill(item.picture);
        return row;
    }
}
