package com.quasardevelopment.bodyarchitect.client.util;

import com.quasardevelopment.bodyarchitect.client.ui.MyApplication;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;

/**
 * Created with IntelliJ IDEA.
 * User: roman_000
 * Date: 24.04.13
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class EnumLocalizer
{
    public static <T> String Translate(T item)
    {
        String key=String.format("%s_%s",item.getClass().getSimpleName(),item.toString());
        int id=MyApplication.getAppContext().getResources().getIdentifier(key, "string",MyApplication.getAppContext().getPackageName());
        return MyApplication.getAppContext().getString(id);
    }

    public static String WeightType()
    {
        if(ApplicationState.getCurrent().getProfileInfo().settings.weightType== WS_Enums.WeightType.Kg)
        {
            return "kg";
        }
        else
        {
            return "pound";
        }
    }

    public static String LengthType()
    {
        if(ApplicationState.getCurrent().getProfileInfo().settings.lengthType== WS_Enums.LengthType.Cm)
        {
            return "cm";
        }
        else
        {
            return "ApplicationStrings";
        }
    }

    public  static String SpeedType()
    {
        if (ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Cm)
        {
            return "km/h";
        }
        else
        {
            return "mph";
        }
    }

    public  static String AltitudeType()
    {
        if (ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Cm)
        {
            return "m";
        }
        else
        {
            return "ft";
        }
    }

    public  static String TemperatureType()
    {
        if (ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Inchs)
        {
            return "°F";
        }
        return "°C";
    }


    public  static String PaceType()
    {
        if (ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Cm)
        {
            return "min/km";
        }
        else
        {
            return "min/mi";
        }
    }


    public static String DistanceType()
    {
        if (ApplicationState.getCurrent().getProfileInfo().settings.lengthType == WS_Enums.LengthType.Cm)
        {
            return "km";
        }
        else
        {
            return "mi";
        }
    }
}
