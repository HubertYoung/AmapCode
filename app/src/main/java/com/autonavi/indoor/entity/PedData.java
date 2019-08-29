package com.autonavi.indoor.entity;

public class PedData {
    public double mAngle;
    public double mAngleNoMag;
    public double mAngleYaw;
    public double mFrequency;
    public double mMoveDirection;
    public double mMoveStateScore;
    public int mStep;
    public double mStepLen;
    public long mTime;
    public double mV;

    public PedData(long j, int i, double d) {
        this.mStep = i;
        this.mAngle = d;
        this.mTime = j;
        this.mFrequency = 0.0d;
        this.mV = 0.0d;
        this.mStepLen = 0.5d;
        this.mMoveDirection = -10000.0d;
        this.mAngleNoMag = 0.0d;
        this.mMoveStateScore = 0.0d;
    }

    public PedData(long j, int i, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        this.mStep = i;
        this.mAngle = d;
        this.mTime = j;
        this.mFrequency = d3;
        this.mV = d4;
        this.mStepLen = d2;
        this.mMoveDirection = d5;
        this.mAngleYaw = d6;
        this.mAngleNoMag = d7;
        this.mMoveStateScore = d8;
    }

    public String toString() {
        return String.format("Time=%d, Step=%d, Angle=%f, StepLen=%f, mAngleYaw=%f, mAngleNoMag=%f", new Object[]{Long.valueOf(this.mTime), Integer.valueOf(this.mStep), Double.valueOf(this.mAngle), Double.valueOf(this.mStepLen), Double.valueOf(this.mAngleYaw), Double.valueOf(this.mAngleNoMag)});
    }
}
