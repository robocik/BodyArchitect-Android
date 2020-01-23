package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.controls.BAPicture;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;
import com.splunk.mint.Mint;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 09.06.13
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class SendMessageActivity extends BANormalActivityBase implements IWsdl2CodeEvents {
    TextView tbUsername;
    EditText txtContent;
    EditText txtTopic;
    Spinner cmbPriority;
    BAPicture imgUser;
    UserDTO receiver;
    LinearLayout pnlProgress;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_send_message, null);
        setMainContent(inflate);



        tbUsername= (TextView) findViewById(R.id.tbUsername);
        txtContent= (EditText) findViewById(R.id.txtContent);
        txtTopic= (EditText) findViewById(R.id.txtTopic);
        btnSend= (Button) findViewById(R.id.btnSend);
        imgUser= (BAPicture) findViewById(R.id.imgUser);
        pnlProgress= (LinearLayout) findViewById(R.id.progressPane);
        Intent intent =getIntent();
        txtTopic.setText(intent.getStringExtra("Topic"));
        receiver= (UserDTO) intent.getSerializableExtra("Receiver");

        imgUser.fill(receiver.picture);
        tbUsername.setText(receiver.userName);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        createSetType();

        getSupportActionBar().setSubtitle(R.string.send_message_activity_title);
    }

    private void sendMessage() {
        if(TextUtils.isEmpty(txtTopic.getText()))
        {
            BAMessageBox.ShowToast(R.string.send_message_activity_err_empty_topic);
            return;
        }
        MessageDTO msg = new MessageDTO();
        msg.createdDate= DateTime.now(DateTimeZone.UTC);
        msg.sender= ApplicationState.getCurrent().getSessionData().profile;
        msg.receiver=receiver;
        msg.topic=txtTopic.getText().toString();
        msg.content=txtContent.getText().toString();
        msg.priority=WS_Enums.MessagePriority.values()[cmbPriority.getSelectedItemPosition()];
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(this);
        try {
            service.SendMessageAsync(msg);
        } catch (Exception e) {
            e.printStackTrace();
            showProgress(false);
            BAMessageBox.ShowToast(R.string.send_message_activity_err_cannot_send);
        }

    }

    void showProgress(boolean show)
    {
        pnlProgress.setVisibility(show?View.VISIBLE:View.GONE);
        btnSend.setVisibility(show?View.GONE:View.VISIBLE);
    }

    private void createSetType() {
        cmbPriority= (Spinner) findViewById(R.id.cmbPriority);
        final String array_spinner[]=new String[3];
        array_spinner[0]=getString(R.string.MessagePriority_Normal);
        array_spinner[1]= getString(R.string.MessagePriority_Low);
        array_spinner[2]= getString(R.string.MessagePriority_High);
        ArrayAdapter<String> adapter =   new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,array_spinner );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbPriority.setAdapter(adapter);
        cmbPriority.setSelection(0);
    }

    @Override
    public void Wsdl2CodeStartedRequest() {
        showProgress(true);
    }

    @Override
    public void Wsdl2CodeFinished(String methodName, Object Data) {
        BAMessageBox.ShowToast(R.string.send_message_activity_message_sent);
        finish();
    }

    @Override
    public void Wsdl2CodeFinishedWithException(Exception ex) {
        Mint.logException(ex);
        BAMessageBox.ShowToast(R.string.send_message_activity_err_cannot_send);
    }

    @Override
    public void Wsdl2CodeEndedRequest() {
        showProgress(false);
    }
}
