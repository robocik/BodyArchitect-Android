package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.quasardevelopment.bodyarchitect.R;


public class TileButton extends LinearLayout
{
    ImageView tileButton;
    public TileButton(Context context)
    {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.tile_button, this);
//
        tileButton= (ImageView) findViewById(R.id.tileButton);
    }


    public void setImageResource(int tileImageRes) {
        tileButton.setImageResource(tileImageRes);
    }

    public void setIsEmpty(boolean isEmpty)
    {
        tileButton.setAlpha(isEmpty?100:255);
    }
}
