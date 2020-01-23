package com.quasardevelopment.bodyarchitect.client.util;

import org.joda.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 20.05.13
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
public class WilksFormula
{
    public static double CalculateCaloriesUsingMET(boolean male,double met,Duration time,double age,double weight,Double height)
    {
        double bmr = 0;
        if (male)
        {
            if(height==null)
            {
                height = 177.4;
            }
            bmr = (13.75*weight) + (5*height) - (6.76*age) + 66;
        }
        else
        {//female
            if (height == null)
            {
                height = 163d;
            }
            bmr = (9.56*weight) + (1.85*height) - (4.68*age) + 655;
        }

        double hours=time.getStandardSeconds() /3600.0;
        double calories = bmr/24*met*hours;
        return Math.round(calories);
    }

    /// <summary>
    /// pace=Time (sec)/distance (km)
    /// </summary>
    /// <param name="pace"></param>
    /// <returns></returns>
    public static String PaceToString(double pace,boolean showWithoutHours)
    {
        StringBuilder builder = new StringBuilder();
        double s = Math.floor(pace); 	  // Whole seconds
        double m = Math.floor(s / 60);     // Whole minutes
        double h = Math.floor(m / 60);     // Whole hours

        if (h > 0)
        {
            if (h < 10)
                builder.append("0");
            builder.append(String.format("%d:",(int)h));
        }
        else if (!showWithoutHours)
        {
            builder.append("00:");
        }

        m = m % 60;

        if (m < 10)
            builder.append("0");
        builder.append( (int)m);

        s = s % 60;

        builder.append( ":");

        if (s < 10)
            builder.append( "0");
        builder.append( (int)s);

        return builder.toString();
    }

    public static String ToPaceString(float distanceInKm, float timeInSec)
    {
        return PaceToString(timeInSec/distanceInKm,false);
    }
    //min/km
    public static double SpeedToPace(double speedMetersPerSecond,boolean toMiles)
    {
        double speedKmPerH = speedMetersPerSecond*3.6;//to km/h
        if (speedKmPerH == 0f)
        {
            return 0;
        }
        if (toMiles)
        {
            speedKmPerH = speedKmPerH/1.621371f;//to miles
        }
        return (60 / speedKmPerH) *60;
    }
}
