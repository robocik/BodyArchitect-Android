package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.fragments.UpgradeAccountFragment;
import com.quasardevelopment.bodyarchitect.client.util.ApplicationState;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 15.08.13
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
public class LabelAccountTypeAwarness extends LinearLayout {
    ImageButton btnHelp;
    TextView txtLabel;

    public LabelAccountTypeAwarness(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a=getContext().obtainStyledAttributes(
                attrs, R.styleable.LabelAccountTypeAwarness);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.label_account_type_awarness, this);

        txtLabel= (TextView) findViewById(R.id.txtLabel);
        btnHelp= (ImageButton) findViewById(R.id.button_help);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpgradeAccountFragment.EnsureAccountType(getContext());
            }
        });
        btnHelp.setVisibility(ApplicationState.getCurrent().isPremium()?View.GONE:View.VISIBLE);
        txtLabel.setText(a.getString(R.styleable.LabelAccountTypeAwarness_android_text));

        //Don't forget this
        a.recycle();
    }


}
