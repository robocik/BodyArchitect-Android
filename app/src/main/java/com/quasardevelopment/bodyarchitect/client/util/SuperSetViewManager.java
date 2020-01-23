package com.quasardevelopment.bodyarchitect.client.util;

import android.graphics.Color;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 21.05.13
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public class SuperSetViewManager
{
    HashMap<String, Integer> usedSuperSetColors = new HashMap<String, Integer>();
    ArrayList<Integer> superSetColors = new ArrayList<Integer>() ;

    public SuperSetViewManager()
    {
        superSetColors.add(Color.argb(255,255,165,0))   ;//orange
        superSetColors.add(Color.GREEN)   ;//green
        superSetColors.add(Color.BLUE)   ;//blue
        superSetColors.add(Color.RED)   ;//red
        superSetColors.add(Color.argb(255,165,42,42))   ;//brown
        superSetColors.add(Color.argb(255,255,0,255))   ;//magenta
    }

    public Integer GetSuperSetColor(String superSetGroup)
    {
        if (usedSuperSetColors.containsKey(superSetGroup))
        {
            return usedSuperSetColors.get(superSetGroup);
        }
        //we need to create a new color
        for (Integer color : superSetColors)
        {
            if (!usedSuperSetColors.containsValue(color))
            {
                usedSuperSetColors.put(superSetGroup, color);
                return color;
            }
        }
        return MyApplication.getAppContext().getResources().getColor(R.color.subtle);
    }
}
