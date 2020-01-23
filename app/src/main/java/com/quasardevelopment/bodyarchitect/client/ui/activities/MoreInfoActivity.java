package com.quasardevelopment.bodyarchitect.client.ui.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.Helper;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 18.08.13
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public class MoreInfoActivity extends BANormalActivityBase
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View inflate = getLayoutInflater().inflate(R.layout.activity_more_info, null);
        setMainContent(inflate);

        TextView btnTutorial= (TextView) findViewById(R.id.btnTutorial);
        btnTutorial.setPaintFlags(btnTutorial.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper.OpenUrl("https://www.youtube.com/embed/Frm1eoQUH5Q?feature=player_detailpage",MoreInfoActivity.this);
            }
        });
        getSupportActionBar().setSubtitle(R.string.more_feature_title);
    }
}
