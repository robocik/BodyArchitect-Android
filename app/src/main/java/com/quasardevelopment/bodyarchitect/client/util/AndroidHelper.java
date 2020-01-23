package com.quasardevelopment.bodyarchitect.client.util;

import android.os.Build;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 25.07.13
 * Time: 19:21
 * To change this template use File | Settings | File Templates.
 */
public class AndroidHelper
{
    static public int GetCheckedItemsCount(ListView list)
    {
        if (Build.VERSION.SDK_INT >= 11)
            return list.getCheckedItemCount();
        else
        {
            int count = 0;
            for (int i = list.getCount() - 1; i >= 0; i--)
                if (list.isItemChecked(i)) count++;
            return count;
        }
    }
}
