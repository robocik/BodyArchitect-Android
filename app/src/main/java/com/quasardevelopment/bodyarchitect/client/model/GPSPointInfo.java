package com.quasardevelopment.bodyarchitect.client.model;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 16.07.13
 * Time: 09:17
 * To change this template use File | Settings | File Templates.
 */
public class GPSPointInfo
{
    public GPSPoint point;
    public double distance;
    public boolean isVirtual;

    public GPSPointInfo(GPSPoint point)
    {
        this.point=point;
    }

}
