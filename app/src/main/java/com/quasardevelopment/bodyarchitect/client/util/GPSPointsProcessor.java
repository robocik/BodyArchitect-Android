package com.quasardevelopment.bodyarchitect.client.util;

import android.location.Location;
import com.quasardevelopment.bodyarchitect.client.model.GPSPoint;
import com.quasardevelopment.bodyarchitect.client.model.GPSPointInfo;
import com.quasardevelopment.bodyarchitect.client.model.LapInfo;
import org.joda.time.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 16.07.13
 * Time: 09:14
 * To change this template use File | Settings | File Templates.
 */
public class GPSPointsProcessor
{
    public final List<GPSPointInfo> pointViewModels;
    public final List<LapInfo> laps;

    public GPSPointsProcessor(List<GPSPoint> points,double lapLength)
     {
         pointViewModels = processGPSPoints(points);
         laps = fillLapsInfo(pointViewModels, lapLength);
     }

    private List<LapInfo> fillLapsInfo(List<GPSPointInfo> points, double lapLength)
    {
        ArrayList<LapInfo> laps = new ArrayList<LapInfo>();

        GPSPointInfo veryFirst=Helper.FirstOrDefault(points);

        GPSPointInfo start = veryFirst;
        LapInfo bestLap = null;
        double lapDistance = 0;
        for (int i = 1; i < points.size(); i++)
        {
            lapDistance = points.get(i).distance - start.distance;

            boolean isLastPoint = i == points.size() - 1;
            if (lapDistance >= lapLength || isLastPoint)
            {
                GPSPointInfo endLapPoint = points.get(i);
                //if distance is higher than exact lap length then create virtual gps point and calculate speed and duration
                if (lapDistance > lapLength)
                {
                    double above = lapDistance - lapLength;
                    double twoLastPointTime = (double)(points.get(i).point.Duration - points.get(i-1).point.Duration);
                    double twoLastPointsDistance = points.get(i).distance - points.get(i-1).distance;
                    double midDuration = twoLastPointTime - ((twoLastPointTime * above) / twoLastPointsDistance) + (double)points.get(i - 1).point.Duration;

                    double bearing = GPSHelper.CalculateInitialBearing(points.get(i - 1).point, points.get(i).point);

                    GPSPoint midPoint = GPSHelper.CalculateDestination(points.get(i - 1).point, bearing, (double)((laps.size() * lapLength + (lapDistance - above)) - points.get(i - 1).distance));



                    midPoint.Duration = (float)midDuration;
                    midPoint.Speed = points.get(i).point.Speed;
                    GPSPointInfo midPointViewModel = new GPSPointInfo(midPoint);
                    midPointViewModel.distance = (laps.size() + 1) * lapLength;
                    midPointViewModel.isVirtual = true;
                    endLapPoint = midPointViewModel;//inject new virtual point as a end lap point
                    //now we should insert mid point between two points
                    points.add(i, midPointViewModel);

                }


                LapInfo lapViewModel = new LapInfo(start, endLapPoint);

                lapViewModel.LapTime = Duration.millis((long)((lapViewModel.endPoint.point.Duration - lapViewModel.startPoint.point.Duration)*1000));
                lapViewModel.TotalTime = Duration.millis((long)((lapViewModel.endPoint.point.Duration - veryFirst.point.Duration)*1000));
                lapViewModel.Speed = lapViewModel.LapTime.getStandardSeconds() > 0 ? (lapDistance / lapViewModel.LapTime.getStandardSeconds()) : 0;
                laps.add(lapViewModel);
                lapViewModel.Nr = laps.size();
                lapViewModel.Distance = lapViewModel.endPoint.distance;
                start = endLapPoint;

                if (isLastPoint && lapDistance < lapLength)
                {//if last lap is smaller than lap length
                    lapViewModel.FullLap = false;
                }

                if (bestLap == null || (bestLap.LapTime.isLongerThan(lapViewModel.LapTime) && lapViewModel.FullLap))
                {
                    bestLap = lapViewModel;
                }
            }
        }

        if (bestLap != null)
        {
            bestLap.BestLap = true;
        }
        return laps;
    }

    private List<GPSPointInfo> processGPSPoints(List<GPSPoint> points)
    {
        ArrayList<GPSPointInfo> viewModels = new ArrayList<GPSPointInfo>();
        double distance = 0;

        GPSPoint previousPoint = null;
        for (int index = 0; index < points.size(); index++)
        {
            GPSPoint gpsPoint = points.get(index);
            if (gpsPoint.IsPause())
            {
                previousPoint = null;
            }
            if (gpsPoint.IsPoint() && previousPoint != null)
            {
                float [] dist = new float[1];
                Location.distanceBetween(previousPoint.Latitude,previousPoint.Longitude,gpsPoint.Latitude,gpsPoint.Longitude,dist);
                distance += dist[0];
            }
            GPSPointInfo pointViewModel = new GPSPointInfo(gpsPoint);
            pointViewModel.distance = distance;
            viewModels.add(pointViewModel);
            if (gpsPoint.IsPoint())
            {
                previousPoint = gpsPoint;
            }
        }



        return viewModels;
    }
}
