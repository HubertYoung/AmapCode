package com.autonavi.indoor.pdr;

import com.autonavi.indoor.util.L;

public class JNIWrapper {
    static double mAngleNoMag;
    static double mAngleYaw;
    static double mDeltaX;
    static double mDeltaY;
    static double mDeltaZ;
    static double mDx;
    static double mDy;
    static double mDz;
    static double mFrequency;
    static double mMagCaliResultX;
    static double mMagCaliResultY;
    static double mMagCaliResultZ;
    static int mMoveDirection;
    static double mMoveStateScore;
    static double mQ1;
    static double mQ2;
    static double mQ3;
    static double mQ4;
    static double mStepLength;
    static int mStepNum;
    static double mV;
    static double mX;
    static double mY;
    static double mZ;

    public static native boolean jniChangeCoordinate2Ground(float f, float f2, float f3, float f4, float f5, float f6, float f7);

    public static native int jniDestroy();

    public static native String jniEncodeURL(String str);

    public static native boolean jniGetMag8Param10Cali(float f, float f2, float f3);

    public static native boolean jniGetStepData();

    public static native String jniLocGetDebugString();

    public static native boolean jniLocGetDirectionState();

    public static native float jniLocGetFilterSquareAngle();

    public static native int jniPDRStart(String str);

    public static native int jniPDRStop();

    public static native boolean jniSetDebug(boolean z);

    public static native int jniUpdateAcceleration(long j, float f, float f2, float f3, float f4, float f5, float f6);

    public static native int jniUpdateGravity(long j, float f, float f2, float f3);

    public static native int jniUpdateGyro(long j, float f, float f2, float f3);

    public static native int jniUpdateMagnetic(long j, float f, float f2, float f3);

    static {
        try {
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("System.loadLibrary:");
                sb.append("indoorpdr6.9");
                sb.append("  isSilent=");
                sb.append(L.isSilent());
                L.d(sb.toString());
            }
            System.loadLibrary("indoorpdr6.9");
            jniSetDebug(false);
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
    }

    public static void jniStepDataCallback(int i, double d, double d2, double d3, int i2, double d4, double d5, double d6) {
        mStepNum = i;
        mFrequency = d;
        mV = d2;
        mStepLength = d3;
        mMoveDirection = i2;
        mAngleYaw = d4;
        mAngleNoMag = d5;
        mMoveStateScore = d6;
    }

    public static void jniDirectionStateCallback(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10) {
        mQ1 = d;
        mQ2 = d2;
        mQ3 = d3;
        mQ4 = d4;
        mDx = d5;
        mDy = d6;
        mDz = d7;
        mDeltaX = d8;
        mDeltaY = d9;
        mDeltaZ = d10;
    }

    public static void jniMagCaliResultCallback(double d, double d2, double d3) {
        mMagCaliResultX = d;
        mMagCaliResultY = d2;
        mMagCaliResultZ = d3;
    }

    public static void jniChangeCoordinate2GroundCallback(double d, double d2, double d3) {
        mX = d;
        mY = d2;
        mZ = d3;
    }
}
