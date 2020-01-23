package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Country;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.UserSearchDTO;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 10.06.13
 * Time: 08:31
 * To change this template use File | Settings | File Templates.
 */
public class PeopleAdapter extends ArrayAdapter<UserSearchDTO>
{

    public PeopleAdapter(Context context, int textViewResourceId,Collection<UserSearchDTO> entries) {
        super(context, textViewResourceId,new ArrayList<UserSearchDTO>());
        ArrayList<UserSearchDTO> messages = new ArrayList<UserSearchDTO>(entries);
        for (UserSearchDTO entry:entries)
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
            row = inflater.inflate(R.layout.people_item, parent, false);
        }

        BAPicture imgPicture = (BAPicture)row.findViewById(R.id.imgUser);
        TextView tbUsername = (TextView)row.findViewById(R.id.tbUsername);
        TextView tbCountry = (TextView)row.findViewById(R.id.tbCountry);
        TextView tbStatus = (TextView)row.findViewById(R.id.tbStatus);
        TextView tbLastEntryDate = (TextView)row.findViewById(R.id.tbLastEntryDate);
        LinearLayout pnlLastEntry = (LinearLayout)row.findViewById(R.id.pnlLastEntry);

        UserSearchDTO item=getItem(position);
        if(item.statistics.status!=null && !TextUtils.isEmpty(item.statistics.status.status))
        {
            tbStatus.setText(String.format("„%s”",item.statistics.status.status));
            tbStatus.setVisibility(View.VISIBLE);
        }
        else
        {
            tbStatus.setVisibility(View.GONE);
        }

        if(item.haveAccess(item.privacy.calendarView))
        {
            pnlLastEntry.setVisibility(View.VISIBLE);
        }
        else
        {
            pnlLastEntry.setVisibility(View.GONE);
        }


        if(item.statistics.lastEntryDate!=null)
        {
            tbLastEntryDate.setText(DateTimeHelper.toRelativeDate(item.statistics.lastEntryDate));
        }
        else
        {
            tbLastEntryDate.setText("");
        }
        imgPicture.fill(item.picture);
        tbUsername.setText(item.userName);
        tbCountry.setText(Country.getCountry(item.countryId).EnglishName);

        return row;
    }
}
