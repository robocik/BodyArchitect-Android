package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.MessageCache;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.DateTimeHelper;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.MessageDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 09.06.13
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class MessageViewActivity extends BANormalActivityBase
{
    MessageDTO message;
    TextView tbUsername;
    TextView tbDateTime;
    TextView tbContent;
    TextView tbTopic;
    ImageView imgPriority;
    BAPicture imgUser;
    private android.view.MenuItem mnuDelete;
    private android.view.MenuItem mnuReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_message_view, null);
        setMainContent(inflate);

        Intent intent =getIntent();
        message= (MessageDTO) intent.getSerializableExtra("Message");

        tbUsername= (TextView) findViewById(R.id.tbUsername);
        tbDateTime= (TextView) findViewById(R.id.tbDateTime);
        tbContent= (TextView) findViewById(R.id.tbContent);
        tbTopic= (TextView) findViewById(R.id.tbTopic);
        imgPriority= (ImageView) findViewById(R.id.imgPriority);
        imgUser= (BAPicture) findViewById(R.id.imgUser);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tbUsername.setText(message.sender.userName);
        tbDateTime.setText(DateTimeHelper.toLocal(message.createdDate).toString(DateTimeFormat.mediumDateTime()));
        tbContent.setText(message.content);
        tbTopic.setText(message.topic);
        imgUser.fill(message.sender.picture);
        imgPriority.setImageDrawable(getResources().getDrawable(getPriorityImage(message.priority)));
    }

    int getPriorityImage(WS_Enums.MessagePriority priority)
    {
        if(priority.equals(WS_Enums.MessagePriority.High))
        {
            return R.drawable.priority_high;
        }
        else  if(priority.equals(WS_Enums.MessagePriority.Low))
        {
            return R.drawable.priority_low;
        }
        else  if(priority.equals(WS_Enums.MessagePriority.System))
        {
            return R.drawable.priority_system;
        }
        return  R.drawable.priority_normal;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if(!ApplicationState.getCurrent().isOffline)
        {
            mnuDelete=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_delete);
            mnuDelete.setIcon(getResources().getDrawable(R.drawable.content_discard)) ;
//            mnuDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuDelete,MenuItem.SHOW_AS_ACTION_ALWAYS);

            mnuReply=menu.add(Menu.NONE,2,Menu.NONE,R.string.button_reply);
            mnuReply.setIcon(getResources().getDrawable(R.drawable.social_reply)) ;
//            mnuReply.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuReply,MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuDelete)
        {
            removeMessage();
        }
        if(item==mnuReply)
        {
            Intent intent = new Intent(this,SendMessageActivity.class);
            intent.putExtra("Receiver",message.sender);
            intent.putExtra("Topic",String.format(getString(R.string.message_view_re_subject),message.topic));
            startActivity(intent);
        }
        return true;
    }

    private void removeMessage() {
        MessageCache cache= ObjectsReposidory.getCache().getMessages();
        cache.Remove(message.globalId);
        ArrayList<MessageDTO> messagesToRemove = new ArrayList<MessageDTO>();
        messagesToRemove.add(message);
        MessagesActivity.RemoveMessagesFromServer(messagesToRemove);
        finish();
    }
}
