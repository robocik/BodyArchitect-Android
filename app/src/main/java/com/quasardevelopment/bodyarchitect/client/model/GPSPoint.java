package com.quasardevelopment.bodyarchitect.client.model;


import java.io.Serializable;

public class GPSPoint   implements Serializable
{
    public GPSPoint()
    {
    }

    public GPSPoint(float latitude, float longitude, float altitude, float speed, float duration)
    {
        Speed = speed;
        Latitude = latitude;
        Longitude = longitude;
        Altitude = altitude;
        Duration = duration;
    }


    public static GPSPoint CreatePause(float duration)
    {
        return new GPSPoint(0, Float.NaN, Float.NaN, 0, duration);
    }
    public static GPSPoint CreateNotAvailable(float duration)
    {
        return new GPSPoint(Float.NaN, 0, Float.NaN, 0, duration);
    }

    public boolean IsPause()
    {
        return Float.isNaN(Longitude);
    }

    public boolean IsNotAvailable()
    {
        return Float.isNaN(Latitude );
    }

    public boolean IsPoint()
    {
        return !IsPause() && !IsNotAvailable();
    }

    public float Speed;

    public float Latitude;

    public float Longitude;

    public float Altitude;

    public Short HearRate;

    public float Duration;
}
