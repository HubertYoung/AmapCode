package com.autonavi.minimap.route.net.module;

import android.graphics.Point;
import com.alibaba.fastjson.annotation.JSONField;
import com.autonavi.bundle.routecommon.entity.Trip;
import com.autonavi.common.model.GeoPoint;
import java.util.ArrayList;

public class RTBusTrip {
    private int arrival;
    private int dis;
    private ArrayList<GeoPoint> mTrackList;
    private int speed;
    private int speed_avg;
    private int station_left;
    private double x;
    private double y;

    public Trip convertToTrip() {
        Trip trip = new Trip();
        trip.arrival = this.arrival;
        trip.speed = this.speed_avg;
        trip.station_left = this.station_left;
        trip.speed_avg = this.speed;
        trip.dis = this.dis;
        Double valueOf = Double.valueOf(this.x);
        Double valueOf2 = Double.valueOf(this.y);
        Point a = cfg.a(valueOf2.doubleValue(), valueOf.doubleValue());
        trip.x = a.x;
        trip.y = a.y;
        trip.lon = valueOf.doubleValue();
        trip.lat = valueOf2.doubleValue();
        trip.track = this.mTrackList;
        return trip;
    }

    public int getArrival() {
        return this.arrival;
    }

    public void setArrival(int i) {
        this.arrival = i;
    }

    public int getSpeed_avg() {
        return this.speed_avg;
    }

    public void setSpeed_avg(int i) {
        this.speed_avg = i;
    }

    public int getStation_left() {
        return this.station_left;
    }

    public void setStation_left(int i) {
        this.station_left = i;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double d) {
        this.x = d;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double d) {
        this.y = d;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int i) {
        this.speed = i;
    }

    public int getDis() {
        return this.dis;
    }

    public void setDis(int i) {
        this.dis = i;
    }

    @JSONField(name = "track")
    public void setTrack(RTBusTrack rTBusTrack) {
        String[] strArr;
        String[] strArr2 = null;
        if (rTBusTrack != null) {
            this.mTrackList = new ArrayList<>();
            String xs = rTBusTrack.getXs();
            String ys = rTBusTrack.getYs();
            if (xs == null) {
                strArr = null;
            } else {
                strArr = xs.split(",");
            }
            if (ys != null) {
                strArr2 = ys.split(",");
            }
            if (!(strArr == null || strArr2 == null || strArr.length != strArr2.length)) {
                for (int i = 0; i < strArr.length; i++) {
                    this.mTrackList.add(new GeoPoint(Double.parseDouble(strArr[i]), Double.parseDouble(strArr2[i])));
                }
            }
            return;
        }
        this.mTrackList = null;
    }
}
