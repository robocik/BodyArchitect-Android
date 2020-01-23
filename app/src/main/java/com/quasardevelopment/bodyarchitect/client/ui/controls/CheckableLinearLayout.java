package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;
import com.quasardevelopment.bodyarchitect.R;

public class CheckableLinearLayout extends LinearLayout implements Checkable {

    private static final int CHECKABLE_CHILD_INDEX = 1;
    private Checkable child;

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child = (Checkable)findViewById(R.id.list_item_checkbox);
    }

    @Override
    public boolean isChecked() {
        return child.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {
        child.setChecked(checked);
    }

    @Override
    public void toggle() {
        child.toggle();
    }

}

