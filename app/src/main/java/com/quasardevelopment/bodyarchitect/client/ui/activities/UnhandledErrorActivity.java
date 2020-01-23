package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.quasardevelopment.bodyarchitect.R;
import com.splunk.mint.Mint;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 05.08.13
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */
public class UnhandledErrorActivity extends BANormalActivityBase
{
    LinearLayout pnlMain;
    String errorInfo;
    Button btnSendError;
    private android.view.MenuItem mnuSendEmail;
    EditText txtErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Mint.setMintCallback(null);

        View inflate = getLayoutInflater().inflate(R.layout.activity_unhandled_error, null);
        setMainContent(inflate);
        getSupportActionBar().setSubtitle(R.string.unhandled_error_activity_title);
        //pnlMain= (LinearLayout) findViewById(R.id.pnlMain);

        //txtErrorMessage= (EditText) findViewById(R.id.txtErrorMessage);
//        btnSendError= (Button) findViewById(R.id.btnSendError);
//        btnSendError.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendErrorEmail();
//            }
//        });
//        Intent intent=getIntent();
//        errorInfo=intent.getStringExtra("Error");
//        txtErrorMessage.setText(errorInfo);
//        txtErrorMessage.setVisibility(Constants.IsDebugMode?View.VISIBLE:View.GONE);
    }

//    @Override
//    public boolean onCreateOptionsMenu(android.view.Menu menu) {
//        mnuSendEmail=menu.add(Menu.NONE,1, Menu.NONE,R.string.unhandled_error_activity_send_error);
//        mnuSendEmail.setIcon(getResources().getDrawable(R.drawable.content_email)) ;
//        mnuSendEmail.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(android.view.MenuItem item) {
//        if(item==mnuSendEmail)
//        {
//            sendErrorEmail();
//        }
//        return true;
//    }
//
//    private void sendErrorEmail() {
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"admin@bodyarchitectonline.com"});
//        i.putExtra(Intent.EXTRA_SUBJECT, "Error: "+ UUID.randomUUID().toString());
//        i.putExtra(Intent.EXTRA_TEXT   ,errorInfo);
//        try {
//            startActivity(Intent.createChooser(i,getString(R.string.unhandled_error_activity_send_email)));
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(UnhandledErrorActivity.this,R.string.unhandled_error_activity_err_no_email_clients, Toast.LENGTH_SHORT).show();
//        }
//    }
}
