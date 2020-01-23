package com.quasardevelopment.bodyarchitect.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.FriendInvitationDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.reverseOrder;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 13.06.13
 * Time: 07:43
 * To change this template use File | Settings | File Templates.
 */
public class InvitationsAdapter extends ArrayAdapter<FriendInvitationDTO>
{
    public InvitationsAdapter(Context context, int textViewResourceId,List<FriendInvitationDTO> entries) {
        super(context, textViewResourceId,new ArrayList<FriendInvitationDTO>());
        ArrayList<FriendInvitationDTO> messages = new ArrayList<FriendInvitationDTO>(entries);
        Collections.sort(messages, reverseOrder(new Comparator<FriendInvitationDTO>() {
            @Override
            public int compare(FriendInvitationDTO sup1, FriendInvitationDTO sup2) {
                return sup1.createdDateTime.compareTo(sup2.createdDateTime);
            }
        }));
        for (FriendInvitationDTO entry:entries)
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
        chkCheck.setVisibility(LinearLayout.GONE);
        FriendInvitationDTO item=getItem(position);

        tbTopic.setText(getOperationMessage(item));
        tbUsername.setText(item.inviter!=null?item.inviter.userName:item.invited.userName);
        tbDateTime.setText(DateTimeHelper.toRelativeDate(DateTimeHelper.toLocal(item.createdDateTime)));
        imgPicture.fill(item.inviter!=null?item.inviter.picture:item.invited.picture);
        return row;
    }

    public static int getOperationMessage(FriendInvitationDTO invitation)
    {
        if (invitation.invited != null)
        {
            if (invitation.invitationType == WS_Enums.InvitationType.Invite)
            {
                return R.string.invitation_view_activity_your_invitation;
            }

        }
        else
        {
            if (invitation.invitationType == WS_Enums.InvitationType.Invite)
            {
                return R.string.invitation_view_activity_you_have_been_invited;
            }
        }
        return 0;
    }
}
