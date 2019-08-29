package com.autonavi.jni.ae.dice;

import java.io.Serializable;

public class LocMountAngle implements Serializable {
    public int hasMountAngle;
    public double pitchMountAngle;
    public double rollMountAngle;
    public double yawMountAngle;

    public LocMountAngle(int i, double d, double d2, double d3) {
        this.hasMountAngle = i;
        this.yawMountAngle = d;
        this.pitchMountAngle = d2;
        this.rollMountAngle = d3;
    }
}
