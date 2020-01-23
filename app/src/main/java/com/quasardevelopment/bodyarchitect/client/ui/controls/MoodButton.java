package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.util.EnumLocalizer;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 08.05.13
 * Time: 09:07
 * To change this template use File | Settings | File Templates.
 */


public class MoodButton extends Button
{
    List<MoodChangedListener> listeners = new ArrayList<MoodChangedListener>();

    WS_Enums.Mood mood= WS_Enums.Mood.Normal;

    public MoodButton(Context context)
    {
        this(context, null);
    }

    void updateButton()
    {
        setText(EnumLocalizer.Translate(mood));

        if(mood== WS_Enums.Mood.Normal)
        {
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.mood_normal , 0, 0, 0)  ;
        }
        else if(mood== WS_Enums.Mood.Good)
        {
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.mood_good , 0, 0, 0)  ;
        }
        else
        {
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.mood_bad , 0, 0, 0)  ;
        }
    }
    public MoodButton(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        setBackgroundColor(Color.TRANSPARENT);
        setTextColor(getResources().getColor(R.color.main_fg));
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.mood_normal , 0, 0, 0)  ;
        updateButton();
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                 if(mood== WS_Enums.Mood.Normal)
                 {
                     setMood(WS_Enums.Mood.Good);
                 }
                else if(mood== WS_Enums.Mood.Good )
                 {
                     setMood(WS_Enums.Mood.Bad);
                 }
                 else
                 {
                     setMood(WS_Enums.Mood.Normal);
                 }
            }
        });
    }

    public void setMood(WS_Enums.Mood mood)
    {
        this.mood=mood;
        updateButton();
        for (MoodChangedListener hl : listeners)
            hl.moodChanged(mood);
    }

    public WS_Enums.Mood getMood()
    {
        return mood;
    }




    public void addListener(MoodChangedListener toAdd) {
        listeners.add(toAdd);
    }

}
