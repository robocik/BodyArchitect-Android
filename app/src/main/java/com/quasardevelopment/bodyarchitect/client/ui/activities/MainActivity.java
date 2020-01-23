package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.Cache.ObjectsReposidory;
import com.quasardevelopment.bodyarchitect.client.model.LocalObjectKey;
import com.quasardevelopment.bodyarchitect.client.model.Settings;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.MainPagerAdapter;
import com.quasardevelopment.bodyarchitect.client.ui.controls.TrainingDaySelectorControl;
import com.quasardevelopment.bodyarchitect.client.ui.controls.ViewPagerParallax;
import com.quasardevelopment.bodyarchitect.client.util.AppRater;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;
import com.quasardevelopment.bodyarchitect.client.util.Constants;
import com.quasardevelopment.bodyarchitect.client.util.Helper;
import com.quasardevelopment.bodyarchitect.client.wcf.EmptyWsdlEvents;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.BasicHttpBinding_IBodyArchitectAccessService;
import com.splunk.mint.Mint;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

public class MainActivity extends BAActivityBase {

    ViewPagerParallax pager;

    @Override
    protected void onDestroy() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
        unbindDrawables(findViewById(R.id.pnlMain));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            Mint.setUserIdentifier(ApplicationState.getCurrent().getSessionData().profile.userName);
        }
        catch (Exception ex)
        {
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.mytitle, null);

        ActionBar.LayoutParams param = new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(v,param);
        actionBar.setBackgroundDrawable(null);
        buildUI();


        if(ApplicationState.getCurrent().isOffline && !Settings.getInfoAboutOfflineMode())
        {
            Settings.setInfoAboutOfflineMode(true);
            BAMessageBox.ShowInfo(R.string.offline_mode_description, this);
        }
        else
        {
            AppRater.app_launched(this);
        }

        boolean crash = ApplicationState.getCurrent().Crash;
        if (ApplicationState.getCurrent().getTrainingDay() != null && crash)
        {
            ApplicationState.getCurrent().Crash=false;
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setTitle(R.string.html_app_name);
            dlgAlert.setMessage(R.string.restore_after_crash_question);
            dlgAlert.setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    openEntry(true);
                }
            });
            dlgAlert.setNegativeButton(android.R.string.cancel,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    ApplicationState.getCurrent().ResetCurrents();
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
        }
        else
        {
            ApplicationState.getCurrent().ResetCurrents();
        }
    }

    private void openEntry(boolean checkRuntimePermissions) {
        LocalObjectKey id= ApplicationState.getCurrent().CurrentEntryId;
        Object currentEntry=ApplicationState.getCurrent().getTrainingDay().getTrainingDay().getTypedEntry(id);
        //if user select to restore crashed entry then we basically do nothing more. This entry is already in a good place
        TrainingDaySelectorControl.GoToPage(currentEntry, MainActivity.this,checkRuntimePermissions);
    }


    private void buildUI() {
        setContentView(R.layout.activity_main);
        pager = (ViewPagerParallax) findViewById(R.id.pager);
        pager.set_max_pages(3);
        pager.setBackgroundAsset(R.raw.main_background);
        pager.setPageMargin(Helper.toDp(-40));
        pager.setHorizontalFadingEdgeEnabled(true);
        //pager.setFadingEdgeLength(30);

        MainPagerAdapter mPagerAdapter = new MainPagerAdapter(this,getSupportFragmentManager());
        pager.setAdapter(mPagerAdapter);
        TitlePageIndicator mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
        Helper.prepare(mIndicator);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_main_settings:
                Intent myIntent = new Intent(this, SettingsActivity.class);
                this.startActivity(myIntent);
                return true;
            case R.id.menu_main_logout:
                logoutMe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void logoutMe() {
        BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new EmptyWsdlEvents());
        service.LogoutAsync();

        Settings.setUserName(null);
        Settings.setPassword (null);
        //todo:remove reminders
        ApplicationState.getCurrent().setSessionData(null);
        ApplicationState.getCurrent().setTempUserName(null);
        ApplicationState.getCurrent().setTempPassword(null);
        ApplicationState.setCurrent(null);
        ImageLoader.getInstance().clearDiscCache();
        ImageLoader.getInstance().clearMemoryCache();
        if(!Constants.IsDebugMode)
        {
            ApplicationState.ClearOffline();
        }
        ObjectsReposidory.clear();
        Intent myIntent = new Intent(this, LoginActivity.class);
        this.startActivity(myIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

/*this method is a second part of opening GPS Entry (which was started in TrainingDaySelectorControl.GoToPage). If user don't have permissions to GPS then we neeed to ask about them.
* And here we back from the permission dialog*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case TrainingDaySelectorControl.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    openEntry(false);

                } else {

                    //Toast.makeText(this,"Needs permissions", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
