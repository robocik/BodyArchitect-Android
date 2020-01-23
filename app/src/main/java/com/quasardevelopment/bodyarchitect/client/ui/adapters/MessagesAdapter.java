package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mobeta.android.dslv.DragSortListView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.MessageDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.reverseOrder;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 08.06.13
 * Time: 21:16
 * To change this template use File | Settings | File Templates.
 */
public class MessagesAdapter extends ArrayAdapter<MessageDTO>
{
    public MessagesAdapter(Context context, int textViewResourceId,List<MessageDTO> entries) {
        super(context, textViewResourceId,new ArrayList<MessageDTO>());
        ArrayList<MessageDTO> messages = new ArrayList<MessageDTO>(entries);
        Collections.sort(messages, reverseOrder(new Comparator<MessageDTO>() {
            @Override
            public int compare(MessageDTO sup1, MessageDTO sup2) {
                return sup1.createdDate.compareTo(sup2.createdDate);
            }
        }));
        for (MessageDTO entry:messages)
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
            row = inflater.inflate(R.layout.message_row, parent, false);
        }

        BAPicture imgPicture = (BAPicture)row.findViewById(R.id.imgUser);
        TextView tbTopic = (TextView)row.findViewById(R.id.tbTopic);
        TextView tbUsername = (TextView)row.findViewById(R.id.tbUsername);
        TextView tbDateTime = (TextView)row.findViewById(R.id.tbDateTime);
        CheckedTextView chkCheck =(CheckedTextView)row.findViewById(R.id.list_item_checkbox);

        boolean isAdvancedEdit=((DragSortListView)parent).getChoiceMode()== AbsListView.CHOICE_MODE_MULTIPLE;
        chkCheck.setVisibility(isAdvancedEdit? LinearLayout.VISIBLE:LinearLayout.GONE);
        MessageDTO item=getItem(position);
        tbTopic.setText(item.topic);
        tbUsername.setText(item.sender!=null?item.sender.userName:item.receiver.userName);
        tbDateTime.setText(DateTimeHelper.toRelativeDate(DateTimeHelper.toLocal(item.createdDate)));
        imgPicture.fill(item.sender!=null?item.sender.picture:item.receiver.picture);
        return row;
    }
}
