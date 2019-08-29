package com.autonavi.bundle.routecommon.entity;

import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Trip implements Serializable, Cloneable {
    public static final int STATION_LEFT_SHRESHOLD = 1;
    private static final long serialVersionUID = 5390170459345675997L;
    public int arrival;
    public int arrival_old;
    public int dis;
    public double lat;
    public String lindID;
    public String lindName;
    public double lon;
    public int speed;
    public int speed_avg;
    public boolean startSection;
    public String stationID;
    public String stationName;
    public int station_left;
    public ArrayList<GeoPoint> track;
    public int x;
    public int y;

    public static class a implements Comparator<Trip> {
        public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            return ((Trip) obj).dis - ((Trip) obj2).dis;
        }
    }

    public static class b implements Comparator<Trip> {
        public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            return ((Trip) obj).arrival - ((Trip) obj2).arrival;
        }
    }

    public boolean isArriving() {
        return this.arrival <= 60 || this.station_left <= 1;
    }

    public Trip clone() {
        try {
            return (Trip) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
