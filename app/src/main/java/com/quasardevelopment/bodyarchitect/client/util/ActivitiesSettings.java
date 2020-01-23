package com.quasardevelopment.bodyarchitect.client.util;

import android.content.SharedPreferences;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 23.05.13
 * Time: 20:15
 * To change this template use File | Settings | File Templates.
 */
public class ActivitiesSettings
{
    public static boolean isTimerEnabled()
    {
        SharedPreferences mPrefs = MyApplication.getAppContext().getSharedPreferences(Constants.ActivityPreferences, 0);
        return mPrefs.getBoolean("TimerEnabled",false);
    }

    public static void setTimerEnabled(boolean enabled)
    {
        SharedPreferences mPrefs = MyApplication.getAppContext().getSharedPreferences(Constants.ActivityPreferences, 0);
        SharedPreferences.Editor prefs=mPrefs.edit();
        prefs.putBoolean("TimerEnabled",enabled);
        prefs.commit();
    }

    public static void setTimerExpanded(boolean value) {
        SharedPreferences preferences = MyApplication.getAppContext().getSharedPreferences(Constants.ActivityPreferences, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("TimerExpanded", value);
        editor.commit();
    }

    public static boolean getTimerExpanded()
    {
        SharedPreferences preferences = MyApplication.getAppContext().getSharedPreferences(Constants.ActivityPreferences, 0);
        return preferences.getBoolean("TimerExpanded",true);
    }

    public static boolean isGlobalTimerEnabled()
    {
        SharedPreferences mPrefs = MyApplication.getAppContext().getSharedPreferences(Constants.ActivityPreferences, 0);
        return mPrefs.getBoolean("GlobalTimerEnabled",false);
    }

    public static void setGlobalTimerEnabled(boolean enabled)
    {
        SharedPreferences mPrefs = MyApplication.getAppContext().getSharedPreferences(Constants.ActivityPreferences, 0);
        SharedPreferences.Editor prefs=mPrefs.edit();
        prefs.putBoolean("GlobalTimerEnabled",enabled);
        prefs.commit();
    }

    public static void clearStrengthTrainingPrefs() {
        SharedPreferences prefs = MyApplication.getAppContext().getSharedPreferences(Constants.ActivityPreferences, 0);
        SharedPreferences.Editor edit=prefs.edit();
        edit.putBoolean("TimerEnabled",false);
        edit.putBoolean("GlobalTimerEnabled",false);
        edit.commit();
    }
}
