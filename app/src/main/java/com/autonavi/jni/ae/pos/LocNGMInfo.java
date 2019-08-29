package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocNGMInfo implements Serializable {
    public double dx;
    public double dy;
    public int flag;
    public long gpsTickTime;
    public double heading;
    public int isRerouting;

    public LocNGMInfo(long j, double d, int i, double d2, double d3, int i2) {
        this.gpsTickTime = j;
        this.heading = d;
        this.isRerouting = i;
        this.dx = d2;
        this.dy = d3;
        this.flag = i2;
    }

    public LocNGMInfo() {
    }
}
