package com.quasardevelopment.bodyarchitect.client.wcf;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.Locale;

public class WcfConstans
{
    public final static String Namespace="http://MYBASERVICE.TK/";
    public final static String ServiceNamespace="http://MYBASERVICE.TK/IBodyArchitectAccessService/";
    public final static String NIL_LABEL = "nil";
    public final static String CollectionNamespace="http://schemas.datacontract.org/2004/07/BodyArchitect.Service.V2.Model";
    public final static String TrainingPlansNamespace="http://schemas.datacontract.org/2004/07/BodyArchitect.Service.V2.Model.TrainingPlans";

    public static void AddStandardHeaders(SoapSerializationEnvelope env)
    {
        env.headerOut = new org.kxml2.kdom.Element[2];
        org.kxml2.kdom.Element h = new org.kxml2.kdom.Element().createElement("","APIKey");
        h.addChild(org.kxml2.kdom.Node.TEXT, "api key");

        org.kxml2.kdom.Element lang = new org.kxml2.kdom.Element().createElement("","Lang");
        lang.addChild(org.kxml2.kdom.Node.TEXT, Locale.getDefault().toString());

        env.headerOut[0]=h;
        env.headerOut[1]=lang;
    }

    public static boolean CheckConnection() {

        try {


            ConnectivityManager cm = (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public static String getCurrentServiceLanguage()
    {
        if("pl_pl".equalsIgnoreCase(Locale.getDefault().toString()))
        {
            return "pl";
        }
        return "en";
    }
}
