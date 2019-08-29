package com.autonavi.jni.eyrie.amap.tracker;

public class TrackPoster {
    public static native void destroy();

    private static native void endTrackInfo(int i);

    public static native void init(String str, String str2, String str3, String str4);

    private static native void nativeSet(int i, String str);

    public static native void uploadAll();

    public static void upload(TrackType trackType) {
        endTrackInfo(trackType.ordinal());
    }

    public static void set(TrackType trackType, String str) {
        nativeSet(trackType.ordinal(), str);
    }
}
