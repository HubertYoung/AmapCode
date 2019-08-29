package com.autonavi.indoor.entity;

public class PressData {
    public double mPress;
    public long mTime;

    public PressData(long j, double d) {
        this.mTime = j;
        this.mPress = d;
    }

    public String toString() {
        return String.format("Time=%d, Press=%f", new Object[]{Long.valueOf(this.mTime), Double.valueOf(this.mPress)});
    }
}
