package com.autonavi.indoor.pdr;

public class JniMag8Result {
    public double mCoverage;
    public double mR;
    public long mTime = System.currentTimeMillis();
    public double mX;
    public double mY;
    public double mZ;

    public JniMag8Result(double d, double d2, double d3, double d4, double d5) {
        this.mX = d;
        this.mY = d2;
        this.mZ = d3;
        this.mR = d4;
        this.mCoverage = d5;
    }

    public String toString() {
        return String.format("%d (%f, %f, %f, %f, %f)", new Object[]{Long.valueOf(this.mTime), Double.valueOf(this.mX), Double.valueOf(this.mY), Double.valueOf(this.mZ), Double.valueOf(this.mR), Double.valueOf(this.mCoverage)});
    }
}
