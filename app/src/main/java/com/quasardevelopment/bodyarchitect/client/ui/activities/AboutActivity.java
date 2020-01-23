package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.util.Constants;
import com.quasardevelopment.bodyarchitect.client.util.Helper;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 29.08.13
 * Time: 08:47
 * To change this template use File | Settings | File Templates.
 */
public class AboutActivity extends BAActivityBase
{

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);



        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.about_title, null);

        ActionBar.LayoutParams param = new ActionBar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200);
        actionBar.setCustomView(v,param);
        Drawable bgColor=new ColorDrawable(getResources().getColor(R.color.main_bg));
        actionBar.setBackgroundDrawable(bgColor);
        actionBar.setBackgroundDrawable(bgColor);
        actionBar.setStackedBackgroundDrawable(bgColor);

        Button btnWebSite = (Button) findViewById(R.id.btnWebSite);
        btnWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.OpenUrl("http://bodyarchitectonline.com",AboutActivity.this);

            }
        });
        Button btnRateReview = (Button) findViewById(R.id.btnRateReview);
        btnRateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            }
        });
        Button btnEmail = (Button) findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"admin@bodyarchitectonline.com"});

                try {
                    startActivity(Intent.createChooser(intent,  getString(R.string.about_send_email_chooser)));
                } catch (android.content.ActivityNotFoundException ex) {
                    BAMessageBox.ShowToast(R.string.err_unhandled);
                }
            }
        });
        Button btnFacebook = (Button) findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Helper.OpenUrl("https://www.facebook.com/pages/BodyArchitect/192639960758583",AboutActivity.this);
            }
        });

        TextView tbVersion= (TextView) v.findViewById(R.id.tbVersion);
        tbVersion.setText(String.format(getString(R.string.about_version,Constants.Version)));
    }

}
