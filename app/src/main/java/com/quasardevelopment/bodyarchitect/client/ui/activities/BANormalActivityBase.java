package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.util.Helper;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 23.04.13
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
public class BANormalActivityBase extends BAActivityBase
{
    private LinearLayout mainPane;
    LinearLayout blackPane;

    protected void onCreate(Bundle savedInstanceState) {



        //requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);


        blackPane = new LinearLayout(this);
        MyApplication app=(MyApplication)getApplication();
        mainPane = new LinearLayout(this);
        mainPane.setId(R.id.pnlMain);
        if(app.normalBgImg!=null)
        {
            blackPane.setBackgroundColor(getResources().getColor(R.color.main_bg));
            Drawable img=app.normalBgImg;
            mainPane.setBackgroundDrawable(img);

        }
        else
        {
            blackPane.setBackgroundColor(getResources().getColor(R.color.main_low_bg));
        }

//        if(app.normalBgImg==null)
//        {
//            mainPane.setBackgroundDrawable(loadBitmap());
//        }
        //getSupportActionBar().setTitle(R.string.html_app_name);
        mainPane.setPadding(0, Helper.toDp(70),0,0);
        //mainPane.setBackground(img);
            /*int sdk = android.os.Build.VERSION.SDK_INT;
if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
    setBackgroundDrawable();
} else {
    setBackground();
}*/


        blackPane.addView(mainPane, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(blackPane);
    }



    protected void onDestroy() {
        super.onDestroy();
        unbindDrawables(blackPane);
        System.gc();
    }

    protected void setMainContent(View view)
    {
        mainPane.addView(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
    }
}
