package com.autonavi.jni.eyrie.amap.tracker;

public class TrackInfo {
    private long objPtr;

    private static native long beginTrackInfo(int i);

    private static native void nativeSet(long j, int i, int i2);

    private static native void nativeSet(long j, int i, String str);

    private static native void nativeSet(long j, String str, int i);

    private static native void nativeSet(long j, String str, String str2);

    private static native void nativeSetYaw(long j, double d, double d2, int i, int i2);

    public static TrackInfo createTrackInfo(TrackType trackType) {
        long beginTrackInfo = beginTrackInfo(trackType.ordinal());
        if (beginTrackInfo == 0) {
            return null;
        }
        return new TrackInfo(beginTrackInfo);
    }

    public void set(TrackInfoKeyType trackInfoKeyType, String str) {
        nativeSet(this.objPtr, trackInfoKeyType.ordinal(), str);
    }

    public long getPtr() {
        return this.objPtr;
    }

    public void set(TrackInfoKeyType trackInfoKeyType, int i) {
        nativeSet(this.objPtr, trackInfoKeyType.ordinal(), i);
    }

    public void set(String str, String str2) {
        nativeSet(this.objPtr, str, str2);
    }

    public void set(String str, int i) {
        nativeSet(this.objPtr, str, i);
    }

    public void setYaw(double d, double d2, int i, int i2) {
        nativeSetYaw(this.objPtr, d, d2, i, i2);
    }

    private TrackInfo(long j) {
        this.objPtr = j;
    }
}
