package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.SimpleFloatViewManager;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 28.05.13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class BAListView  extends DragSortListView
{

    public BAListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SimpleFloatViewManager simpleFloatViewManager = new SimpleFloatViewManager(this);
        simpleFloatViewManager.setBackgroundColor(Color.TRANSPARENT);
        setFloatViewManager(simpleFloatViewManager);
    }
}
