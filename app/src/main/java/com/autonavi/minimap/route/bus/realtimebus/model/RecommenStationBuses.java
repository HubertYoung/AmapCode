package com.autonavi.minimap.route.bus.realtimebus.model;

import android.text.TextUtils;
import java.io.Serializable;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RecommenStationBuses implements Serializable {
    private String arrival;
    private String dis;
    private String speed;
    private String speed_avg;
    private String station_left;
    private String x;
    private String y;

    public String getArrival() {
        return this.arrival == null ? "" : this.arrival;
    }

    public int getArrivalInt() {
        if (TextUtils.isEmpty(this.arrival)) {
            return 0;
        }
        return Integer.parseInt(this.arrival, 10);
    }

    public void setArrival(String str) {
        this.arrival = str;
    }

    public String getSpeed_avg() {
        return this.speed_avg;
    }

    public void setSpeed_avg(String str) {
        this.speed_avg = str;
    }

    public String getStation_left() {
        return this.station_left;
    }

    public void setStation_left(String str) {
        this.station_left = str;
    }

    public String getX() {
        return this.x;
    }

    public void setX(String str) {
        this.x = str;
    }

    public String getY() {
        return this.y;
    }

    public void setY(String str) {
        this.y = str;
    }

    public String getDis() {
        return this.dis;
    }

    public void setDis(String str) {
        this.dis = str;
    }

    public String getSpeed() {
        return this.speed;
    }

    public void setSpeed(String str) {
        this.speed = str;
    }
}
