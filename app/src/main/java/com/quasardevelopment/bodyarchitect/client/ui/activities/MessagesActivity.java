package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.MessageCache;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsCacheBase;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.InvitationsAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.MessagesAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAListView;
import com.quasardevelopment.bodyarchitect.client.util.AndroidHelper;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 07.06.13
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
public class MessagesActivity extends BANormalActivityBase implements IWsdl2CodeEvents {
    TabHost tabHost;
    ListView lstMessages;
    ListView lstInvitations;
    private MessagesAdapter adapter;
    private TextView tbEmptyMessages;
    private android.view.MenuItem mnuSelectMode;
    private ActionMode currentActionMode;
    private InvitationsAdapter invitationsAdapter;
    TextView tbEmptyInvitations;
    private android.view.MenuItem mnuRefresh;
    private ProgressDialog progressDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_messages, null);
        setMainContent(inflate);

        tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();


        TabHost.TabSpec spec1=tabHost.newTabSpec(getString(R.string.messages_activity_tab_messages));
        spec1.setContent(R.id.messages_tab_messages);
        spec1.setIndicator(getString(R.string.messages_activity_tab_messages));


        TabHost.TabSpec spec2=tabHost.newTabSpec(getString(R.string.messages_activity_tab_invitations));
        spec2.setIndicator(getString(R.string.messages_activity_tab_invitations));
        spec2.setContent(R.id.messages_tab_invitations);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);

        lstMessages= (BAListView) findViewById(R.id.lstMessages);
        tbEmptyMessages =(TextView) findViewById(R.id.tbEmptyMessages);
        tbEmptyInvitations  =(TextView) findViewById(R.id.tbEmptyInvitations);
        lstInvitations= (ListView) findViewById(R.id.lstInvitations);
        lstMessages.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isAdvancedEditMode())
                {
                    currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),AndroidHelper.GetCheckedItemsCount(lstMessages)));
                    return;
                }
                MessageDTO item= (MessageDTO) lstMessages.getAdapter().getItem(i);
                Intent myIntent =new Intent(MessagesActivity.this, MessageViewActivity.class);
                myIntent.putExtra("Message",item);
                startActivity(myIntent);
            }
        });

        getSupportActionBar().setSubtitle(R.string.messages_activity_title);

        lstMessages.setLongClickable(true);
        lstMessages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentActionMode==null)
                {
                    ((ListView)adapterView).clearChoices();
                    advancedEdit();
                }
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        lstInvitations.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FriendInvitationDTO item= (FriendInvitationDTO) lstInvitations.getAdapter().getItem(i);
                Intent myIntent =new Intent(MessagesActivity.this, InvitationViewActivity.class);
                myIntent.putExtra("Invitation",item);
                startActivity(myIntent);
            }
        });

        Intent intent=getIntent();
        tabHost.setCurrentTab(intent.getIntExtra("SelectedTab",0));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                finishCurrentMode(true);
                supportInvalidateOptionsMenu();
            }
        });
    }


    protected boolean finishCurrentMode(boolean invokeFinish)
    {
        if(currentActionMode!=null)
        {
            if(invokeFinish)
            {
                currentActionMode.finish();
            }
            currentActionMode=null;
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillMessages();
        fillInvitations();
    }

    void fillInvitations()
    {
        Parcelable state = lstInvitations.onSaveInstanceState();
        invitationsAdapter = new InvitationsAdapter(this,R.layout.message_row, ApplicationState.getCurrent().getProfileInfo().invitations);
        lstInvitations.setAdapter(invitationsAdapter);
        invitationsAdapter.notifyDataSetChanged();

        lstInvitations.onRestoreInstanceState(state);
        tbEmptyInvitations.setVisibility(ApplicationState.getCurrent().getProfileInfo().invitations.size() == 0 ? View.VISIBLE : View.GONE);
        lstInvitations.setVisibility(ApplicationState.getCurrent().getProfileInfo().invitations.size() > 0 ? View.VISIBLE : View.GONE);
    }

    void refreshMessages()
    {
        ObjectsCacheBase cache= ObjectsReposidory.getCache().getMessages();


        if(cache.isLoaded() || ApplicationState.getCurrent().isOffline)
        {
            fillMessages();
        }
        else
        {

            progressDlg= BAMessageBox.ShowProgressDlg(this, R.string.progress_retrieving_items);
            cache.LoadAsync(this);
        }
    }

    void fillMessages()
    {

        Parcelable state = lstMessages.onSaveInstanceState();
        HashMap<UUID, MessageDTO> messages = ObjectsReposidory.getCache().getMessages().getItems();
        adapter = new MessagesAdapter(this,R.layout.message_row, new ArrayList<MessageDTO>(messages.values()));
        lstMessages.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lstMessages.onRestoreInstanceState(state);
        tbEmptyMessages.setVisibility(messages.size() == 0 ? View.VISIBLE : View.GONE);
        lstMessages.setVisibility(messages.size() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if(!ApplicationState.getCurrent().isOffline && isMessagesTabSelected())
        {
            mnuSelectMode=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_select);
            mnuSelectMode.setIcon(getResources().getDrawable(R.drawable.appbar_select)) ;
//            mnuSelectMode.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuSelectMode,MenuItem.SHOW_AS_ACTION_ALWAYS);

            mnuRefresh=menu.add(Menu.NONE,1,Menu.NONE,R.string.button_refresh);
            mnuRefresh.setIcon(getResources().getDrawable(R.drawable.navigation_refresh)) ;
//            mnuRefresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            MenuItemCompat.setShowAsAction(mnuRefresh,MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    private boolean isMessagesTabSelected()
    {
        return tabHost.getCurrentTab()==0;
    }
    boolean isAdvancedEditMode()
    {
        return lstMessages.getChoiceMode()== AbsListView.CHOICE_MODE_MULTIPLE;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if(item==mnuSelectMode)
        {
              advancedEdit();
        }
        if(item==mnuRefresh)
        {
            ObjectsCacheBase cache= ObjectsReposidory.getCache().getMessages();
            cache.Clear();
            refreshMessages();
        }
        return true;
    }

    void advancedEdit()
    {
        if(!isAdvancedEditMode())
        {
            lstMessages.clearChoices();
            lstMessages.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            currentActionMode=startSupportActionMode(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
                    android.view.MenuItem mnuDelete = menu.add(Menu.NONE, 1, Menu.NONE, R.string.button_delete);
                    mnuDelete.setIcon(R.drawable.content_discard);
//                    mnuDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    MenuItemCompat.setShowAsAction(mnuDelete,MenuItem.SHOW_AS_ACTION_ALWAYS);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
                    mode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),0));
                    return false;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, android.view.MenuItem item) {
                    if(item.getItemId()==1)
                    {
                        removeSelectedMessages(mode);
                        return true;
                    }
                    return false;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                    advancedEdit();//leave advanced edit mode
                    finishCurrentMode(false);
                }
            }) ;
            currentActionMode.setTitle(String.format(getString(R.string.advanced_edit_selected_items),AndroidHelper.GetCheckedItemsCount(lstMessages)));
        }
        else
        {
            lstMessages.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }



        adapter.notifyDataSetChanged();
    }

    private void removeSelectedMessages(ActionMode mode) {
        MessageCache cache=ObjectsReposidory.getCache().getMessages();
        SparseBooleanArray checked =lstMessages.getCheckedItemPositions();
        final ArrayList<MessageDTO> messagesToRemove = new ArrayList<MessageDTO>();
        for (int i = lstMessages.getCount()-1; i >=0; i--)
            if (checked.get(i)) {
                MessageDTO itemDto = adapter.getItem(i);
                messagesToRemove.add(itemDto);
                cache.Remove(itemDto.globalId);
                adapter.remove(itemDto);
            }

        RemoveMessagesFromServer(messagesToRemove);
        mode.finish();
        adapter.notifyDataSetChanged();
    }

    public static void RemoveMessagesFromServer(final Collection<MessageDTO> messagesToRemove)
    {
        //now run delete messages in separate thread (in background)
        new Thread(new Runnable() {
            @Override
            public void run() {

                BasicHttpBinding_IBodyArchitectAccessService service =new BasicHttpBinding_IBodyArchitectAccessService(null);
                for(MessageDTO msg:messagesToRemove)
                {
                    try {
                        MessageOperationParam param = new MessageOperationParam();
                        param.operation= WS_Enums.MessageOperationType.Delete;
                        param.messageId=msg.globalId;

                        service.MessageOperation(param);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                }
            }
        }).start();
    }

    protected void closeProgress()
    {
        if(progressDlg!=null)
        {
            progressDlg.dismiss();
            progressDlg=null;
        }
    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        fillMessages();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        closeProgress();
    }
}
