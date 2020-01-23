package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.quasardevelopment.bodyarchitect.R;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 25.07.13
 * Time: 06:51
 * To change this template use File | Settings | File Templates.
 */
public class CompanyLogoControl extends LinearLayout {
    public CompanyLogoControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.company_logo_control, this);
    }
}
