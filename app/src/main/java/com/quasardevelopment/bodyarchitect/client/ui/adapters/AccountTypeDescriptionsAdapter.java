package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 09.07.13
 * Time: 08:50
 * To change this template use File | Settings | File Templates.
 */
public class AccountTypeDescriptionsAdapter extends ArrayAdapter<AccountTypeDescriptionsAdapter.AccountTypeItem>
{
    public  static class AccountTypeItem
    {
        public int Title;

        public int Description;

        public AccountTypeItem(int title,int description)
        {
            Title=title;
            Description=description;
        }
    }

    public AccountTypeDescriptionsAdapter(Context context, int textViewResourceId,ArrayList<AccountTypeItem> entries) {
        super(context, textViewResourceId,entries);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;



        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.account_type_description_item, parent, false);
        }

        TextView tbName = (TextView)row.findViewById(R.id.tbName);
        TextView tbDescription = (TextView)row.findViewById(R.id.tbDescription);

        AccountTypeItem item=getItem(position);

        tbName.setText(item.Title);
        tbDescription.setText(item.Description);


        return row;
    }
}
