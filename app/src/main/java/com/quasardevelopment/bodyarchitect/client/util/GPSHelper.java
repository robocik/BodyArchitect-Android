package com.quasardevelopment.bodyarchitect.client.util;

import com.quasardevelopment.bodyarchitect.client.model.GPSPoint;
import com.quasardevelopment.bodyarchitect.client.model.IPerson;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.GPSTrackerEntryDTO;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.WS_Enums;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 20.05.13
 * Time: 09:28
 * To change this template use File | Settings | File Templates.
 */
public class GPSHelper
{
    public static Double CalculateCalories(double met, Double duration, IPerson person)
{
    if (duration == null)
    {//we don't have time of exercising then we cannot calculate calories
        return null;
    }
    DateTime birthday = null;
    WS_Enums.Gender gender = WS_Enums.Gender.Male;
    double weight = 0;
    double height = 0;

    if (person.getBirthday() != null)
    {
        birthday = person.getBirthday();
    }
    gender = person.getGender();
    if (person.getWymiary() != null)
    {
        weight = person.getWymiary().weight;
        height = person.getWymiary().height;
    }
    if (gender.equals(WS_Enums.Gender.NotSet))
    {
        gender = WS_Enums.Gender.Male;
    }


    if (weight == 0)
    {//set default weight if not defined
        if (gender .equals(WS_Enums.Gender.Female))
        {
            weight = 70;
        }
        else
        {
            weight = 100;
        }
    }
    if (gender.equals(WS_Enums.Gender.Male))
    {
        if (height == 0)
        {
            height = 177.4;
        }
    }
    else
    {//female
        if (height == 0)
        {
            height = 163;
        }
    }
    if (birthday == null)
    {//if birthday is not set then assume 30 years old
        birthday=DateTime.now().minusYears(30).minusDays(1);
    }

    int age =DateTimeHelper.GetAge(birthday);

    return WilksFormula.CalculateCaloriesUsingMET(gender == WS_Enums.Gender.Male, met, Duration.standardSeconds(duration.longValue()), age, weight, height);
}

    private static double degreeToRadian(double angle)
    {
        return Math.PI * angle / 180.0;
    }

    private static double radianToDegree(double angle)
    {
        return angle * (180.0 / Math.PI);
    }

    public void CalculateCaloriesBurned(GPSTrackerEntryDTO gpsEntry,double duration, IPerson person)
    {
        gpsEntry.calories = CalculateCalories(gpsEntry.exercise.met, duration, person);
    }

    public static double CalculateInitialBearing(GPSPoint posA, GPSPoint posB)
    {
        double latA = degreeToRadian(posA.Latitude);
        double latB = degreeToRadian(posB.Latitude);
        double dLon = degreeToRadian(posB.Longitude - posA.Longitude);
        double y = Math.sin(dLon) * Math.cos(latB);
        double x = Math.cos(latA) * Math.sin(latB) -
                Math.sin(latA) * Math.cos(latB) * Math.cos(dLon);
        double brng = Math.atan2(y, x);
        return (radianToDegree(brng) + 360)%360;
    }

    public static GPSPoint CalculateDestination(GPSPoint start, double bearing, double distance)
    {
        distance = distance/1000;//to km
        double radBearing = degreeToRadian(bearing);

        double R = 6371;
        double radDistance = distance / R;
        double lat = degreeToRadian(start.Latitude);
        double lon=degreeToRadian(start.Longitude);

        double lat2 = Math.asin(Math.sin(lat) * Math.cos(radDistance) +
                Math.cos(lat) * Math.sin(radDistance) * Math.cos(radBearing));
        double lon2 = lon + Math.atan2(Math.sin(radBearing) * Math.sin(radDistance) * Math.cos(lat),
                Math.cos(radDistance) - Math.sin(lat) * Math.sin(lat2));
        lon2 = (lon2+3*Math.PI) % (2*Math.PI) - Math.PI;  // normalise to -180..+180º
        return new GPSPoint((float) radianToDegree(lat2), (float) radianToDegree(lon2), start.Altitude, 0, 0);

    }
}
