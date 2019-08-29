package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocGPSHistoryInfo implements Serializable {
    public float accuracy;
    public float azi;
    public int checkStatus;
    public int formway;
    public LocMapPoint gpsPos;
    public boolean hasRoute;
    public boolean hasRouteMatch;
    public int linkType;
    public int locOnRouteState;
    public LocMapPoint matchPos;
    public int matchRouteState;
    public int roadClass;
    public float speed;
    public long timestamp;
    public long timestampSystem;

    public LocGPSHistoryInfo(long j, long j2, LocMapPoint locMapPoint, float f, float f2, float f3, int i, boolean z, boolean z2, int i2, LocMapPoint locMapPoint2, int i3, int i4, int i5) {
        this.timestamp = j;
        this.timestampSystem = j2;
        this.gpsPos = locMapPoint;
        this.speed = f;
        this.azi = f2;
        this.accuracy = f3;
        this.checkStatus = i;
        this.hasRoute = z;
        this.hasRouteMatch = z2;
        this.matchRouteState = i2;
        this.matchPos = locMapPoint2;
        this.formway = i3;
        this.linkType = i4;
        this.roadClass = i5;
    }
}
