package com.autonavi.indoor.pdr;

public class JniMagCaliResult {
    public long mTime = System.currentTimeMillis();
    public double mX;
    public double mY;
    public double mZ;

    public JniMagCaliResult(double d, double d2, double d3) {
        this.mX = d;
        this.mY = d2;
        this.mZ = d3;
    }

    public String toString() {
        return String.format("%d (%f, %f, %f)", new Object[]{Long.valueOf(this.mTime), Double.valueOf(this.mX), Double.valueOf(this.mY), Double.valueOf(this.mZ)});
    }
}
