package com.quasardevelopment.bodyarchitect.client.model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.util.Constants;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 15.04.13
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class Settings
{

    public static boolean getRunUnderLockScreen() {
        return true;
    }

    public static boolean isScreenOrientationLock() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getBoolean("ScreenOrientationLock",false);
    }

    public static void setScreenOrientationLock(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("ScreenOrientationLock", value);
        editor.commit();
    }

    public static boolean isAutoPause() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getBoolean("AutoPause",true);
    }

    public static void setAutoPause(boolean autoPause) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("AutoPause", autoPause);
        editor.commit();
    }

    public static int getSortExercises() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getInt("SortExercises", 0);
    }

    public static void setSortExercises(int position) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("SortExercises", position);
        editor.commit();
    }

    public enum CopyStrengthTrainingMode{
        Full(0),
        WithoutSetsData(1),
        OnlyExercises (2);

        private int code;

        CopyStrengthTrainingMode(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }

        public static CopyStrengthTrainingMode fromString(String str)
        {
            if (str.equals("Full"))
                return Full;
            if (str.equals("WithoutSetsData"))
                return WithoutSetsData;
            if (str.equals("OnlyExercises"))
                return OnlyExercises;

            return null;
        }
    }


    public static String getEndPointUrl(String endpointName)
    {
        if(endpointName.equals("Localhost"))
        {
             return "http://192.168.0.9/BodyArchitectWebSite/V2/BodyArchitect.svc/WP7";
        }
        else if(endpointName.equals("Test"))
        {
            return "http://test.bodyarchitectonline.com/V2/BodyArchitect.svc/WP7";
        }
        else if(endpointName.equals("Test2"))
        {
            return "http://test2.bodyarchitectonline.com/V2/BodyArchitect.svc/WP7";
        }
        return  "http://service.bodyarchitectonline.com/V2/BodyArchitect.svc/WP7";
    }

    public static  String getServerUrl()
    {
        if(Constants.IsDebugMode )
        {
            return "http://test.bodyarchitectonline.com/";
        }
        else
        {
            return "http://service.bodyarchitectonline.com/";
        }
    }

    public static boolean getInfoAboutOfflineMode()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getBoolean("InfoAboutOffline",false);
    }
    public static void setInfoAboutOfflineMode(boolean value)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("InfoAboutOffline", value);
        editor.commit();
    }

    public static String getEndPointUrl()
    {
        String endpointName=getEndPoint();
        String url= getEndPointUrl(endpointName);
        return url;
    }
    public static void setEndPoint(String endPoint)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("EndPoint", endPoint);
        editor.commit();
    }

    public static String getEndPoint()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getString("EndPoint","Production");
    }

    public static int getNumberOfMonthToRetrieve()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getInt("NumberOfMonthToRetrieve",3);
    }

    public static void setNumberOfMonthToRetrieve(int value)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("NumberOfMonthToRetrieve", value);
        editor.commit();
    }

    public static String getClientInstanceId()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        String clientId= preferences.getString("ClientInstanceId",null);
        if(clientId==null)
        {
            clientId= UUID.randomUUID().toString();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("ClientInstanceId", clientId);
            editor.commit();
        }
        return clientId;
    }

    public static void setUserName(String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserName", value);
        editor.commit();
    }

    public static String getUserName()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getString("UserName",null);
    }

    public static void setPassword(String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Password", value);
        editor.commit();
    }

    public static String getPassword()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getString("Password",null);
    }

    public static void setCopyValuesFromNewSet(boolean copy) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("CopyValuesFromNewSet", copy);
        editor.commit();
    }

    public static boolean getCopyValuesFromNewSet()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getBoolean("CopyValuesFromNewSet",true);
    }

    public static void setTreatSuperSetsAsOne(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("TreatSuperSetsAsOne", value);
        editor.commit();
    }

    public static boolean getTreatSuperSetsAsOne()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getBoolean("TreatSuperSetsAsOne",false);
    }

    public static void setStartTimer(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("StartTimer", value);
        editor.commit();
    }

    public static boolean getStartTimer()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getBoolean("StartTimer",true);
    }

    public static void setCopyStrengthTrainingMode(CopyStrengthTrainingMode value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("CopyStrengthTrainingMode", value.toString());
        editor.commit();
    }

    public static CopyStrengthTrainingMode getCopyStrengthTrainingMode()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return CopyStrengthTrainingMode.valueOf(preferences.getString("CopyStrengthTrainingMode",CopyStrengthTrainingMode.WithoutSetsData.toString()));
    }

    public static void setLanguage(String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Language", value.toString());
        editor.commit();
    }

    public static String getLanguage()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
        return preferences.getString("Language","");
    }

}
