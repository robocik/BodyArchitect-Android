package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;

import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Constants;
import com.splunk.mint.DataSaverResponse;
import com.splunk.mint.Mint;
import com.splunk.mint.MintCallback;
import com.splunk.mint.NetSenderResponse;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 19.04.13
 * Time: 07:49
 * To change this template use File | Settings | File Templates.
 */
public abstract class BAActivityBase extends AppCompatActivity implements MintCallback
{
    public void netSenderResponse(NetSenderResponse var1){

    }

    public void dataSaverResponse(DataSaverResponse var1){}

    @Override
    public void lastBreath(Exception ex) {

        Intent crashedIntent = new Intent(BAActivityBase.this, UnhandledErrorActivity.class);
        crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(crashedIntent);

        finish();
    }

    protected void EnsureLogged()
    {
        if(ApplicationState.getCurrent()==null)
        {
            ApplicationState.setCurrent(ApplicationState.loadState());
            if(ApplicationState.getCurrent()==null)
            {
                Intent loginIntent = new Intent(this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
                return;
            }
            ObjectsReposidory.loadState();

        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(null);
        actionBar.setIcon(R.drawable.ba_action_bar);
        actionBar.setDisplayShowHomeEnabled(true);
//        Drawable bgColor=new ColorDrawable(Color.TRANSPARENT);
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000ff")));
//        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000ff")));
        EnsureLogged();
        //if(!Constants.IsDebugMode)
        {   //todo:in debug mode I disabled sending error info
            Mint.initAndStartSession(this, Constants.IsDebugMode? "debug key":"release key");
        }
        //BugSenseHandler.setExceptionCallback(this);
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable e) {
//                StackTraceElement[] arr = e.getStackTrace();
//                final StringBuffer report = new StringBuffer(e.toString());
//                final String lineSeperator = "-------------------------------\n\n";
//                report.append("\r\n\n");
//                report.append("--------- Stack trace ---------\n\n");
//                for (int i = 0; i < arr.length; i++) {
//                    report.append( "    ");
//                    report.append(arr[i].toString());
//                    report.append("\r\n");
//                }
//                report.append(lineSeperator);
//                // If the exception was thrown in a background thread inside
//                // AsyncTask, then the actual exception can be found with getCause
//                report.append("--------- Cause ---------\n\n");
//                Throwable cause = e.getCause();
//                if (cause != null) {
//                    report.append(cause.toString());
//                    report.append("\r\n\n");
//                    arr = cause.getStackTrace();
//                    for (int i = 0; i < arr.length; i++) {
//                        report.append("    ");
//                        report.append(arr[i].toString());
//                        report.append("\r\n");
//                    }
//                }
//                // Getting the Device brand,model and sdk verion details.
//                report.append(lineSeperator);
//                report.append("--------- Device ---------\n\n");
//                report.append("Brand: ");
//                report.append(Build.BRAND);
//                report.append("\r\n");
//                report.append("Device: ");
//                report.append(Build.DEVICE);
//                report.append("\r\n");
//                report.append("Model: ");
//                report.append(Build.MODEL);
//                report.append("\r\n");
//                report.append("Id: ");
//                report.append(Build.ID);
//                report.append("\r\n");
//                report.append("Product: ");
//                report.append(Build.PRODUCT);
//                report.append("\r\n");
//                report.append(lineSeperator);
//                report.append("--------- Firmware ---------\n\n");
//                report.append("SDK: ");
//                report.append(Build.VERSION.SDK);
//                report.append("\r\n");
//                report.append("Release: ");
//                report.append(Build.VERSION.RELEASE);
//                report.append("\r\n");
//                report.append("Incremental: ");
//                report.append(Build.VERSION.INCREMENTAL);
//                report.append("\r\n");
//                report.append(lineSeperator);
//
//                Intent crashedIntent = new Intent(BAActivityBase.this, UnhandledErrorActivity.class);
//                crashedIntent.putExtra("Error",  report.toString());
//                crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                crashedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                startActivity(crashedIntent);
//
//                finish();
//
//                System.exit(0);
//            }
//        });
        MyApplication.updateLanguage(this);

        if(Settings.isScreenOrientationLock())
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onStop() {

        ApplicationState state=ApplicationState.getCurrent();
        if(state!=null)
        {
            state.SaveStateAsync();
        }
        super.onStop();
    }

    protected void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
