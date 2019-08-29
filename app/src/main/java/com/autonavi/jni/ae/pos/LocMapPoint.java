package com.autonavi.jni.ae.pos;

import java.io.Serializable;

public class LocMapPoint implements Serializable {
    public int lat;
    public int lon;
    public int zLevel;

    public LocMapPoint(int i, int i2, int i3) {
        this.lon = i;
        this.lat = i2;
        this.zLevel = i3;
    }
}
