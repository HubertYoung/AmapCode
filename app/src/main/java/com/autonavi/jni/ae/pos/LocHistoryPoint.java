package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocHistoryPoint implements Serializable {
    public float course;
    public int flag;
    public LocMapPoint pos;
    public float speed;
    public long tickTime;

    public LocHistoryPoint(LocMapPoint locMapPoint, float f, float f2, long j, int i) {
        this.pos = locMapPoint;
        this.course = f;
        this.speed = f2;
        this.tickTime = j;
        this.flag = i;
    }
}
