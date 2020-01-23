package com.quasardevelopment.bodyarchitect.client.model;

import org.joda.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 16.07.13
 * Time: 09:14
 * To change this template use File | Settings | File Templates.
 */
public class LapInfo {
    public GPSPointInfo startPoint;
    public GPSPointInfo endPoint;
    public Duration LapTime;
    public Duration TotalTime;
    public double Speed;
    public int Nr;
    public double Distance;
    public boolean FullLap=true;
    public boolean BestLap;

    public LapInfo(GPSPointInfo start, GPSPointInfo endLapPoint) {
        startPoint=start;
        endPoint=endLapPoint;

    }
}
