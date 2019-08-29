package com.autonavi.jni.ae.dice;

import java.io.Serializable;

public class LocSensorOption implements Serializable {
    public int accFreq;
    public int gpsFreq;
    public int gyroFreq;
    public int hasAcc;
    public int hasGyro;
    public int hasMag;
    public int hasPressure;
    public int hasTemp;
    public int hasW4m;
    public int pulseFreq;
    public int w4mFreq;

    public LocSensorOption(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        this.hasAcc = i;
        this.hasGyro = i2;
        this.hasTemp = i3;
        this.hasPressure = i4;
        this.hasMag = i5;
        this.hasW4m = i6;
        this.pulseFreq = i7;
        this.gyroFreq = i8;
        this.gpsFreq = i9;
        this.accFreq = i10;
        this.w4mFreq = i11;
    }
}
