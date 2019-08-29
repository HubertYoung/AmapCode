package com.alipay.streammedia.cvengine.slam;

public class ORBResult {
    public static final int ARMODE_FUSE = 2;
    public static final int ARMODE_IMU = 1;
    public static final int ARMODE_INVALID = 0;
    public float[] ORBData;
    public int[] ORBDrawerData;
    public long fastDectedPoints;
    public long fastTrackPoints;
    public int retCode;
    public int retMode;
    public double timeStamp;
    public long totalKeyFrames;
    public long totalMapPoints;
    public long visionInitCostTime;
    public boolean visionInitSuccess;
}
