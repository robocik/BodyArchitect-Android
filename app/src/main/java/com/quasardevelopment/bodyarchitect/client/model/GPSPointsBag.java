package com.quasardevelopment.bodyarchitect.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class GPSPointsBag    implements Serializable
{
    ArrayList<GPSPoint> points;

    public GPSPointsBag()
    {
        this.points = new ArrayList<GPSPoint>();
    }

    public GPSPointsBag(Collection<GPSPoint> points)
    {
        this.points=new ArrayList<GPSPoint>(points);
        IsSaved = true;
    }

    public GPSPointsBag(Collection<GPSPoint> points,boolean isSaved)
    {
        this.points=new ArrayList<GPSPoint>(points);
        IsSaved = isSaved;
    }

    public ArrayList<GPSPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<GPSPoint> points) {
        this.points = points;
    }

    public boolean IsSaved;

}
