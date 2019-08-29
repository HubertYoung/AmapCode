package com.autonavi.jni.route.health;

import com.autonavi.jni.eyrie.amap.tracker.TrackInfo;

public class IHealth {
    protected long mPtr;

    public static native String GetBuildDate();

    public static native String GetVersion();

    private native void NativeSetTrackInfo(long j);

    public static native void Release(IHealth iHealth);

    public native TraceStatistics GetTraceStatistics();

    public native boolean IsTraceTooShort();

    public native void PauseTrace();

    public native void PlayGPSWeakVoice();

    public native void ResumeTrace();

    public native void SetGPSArray(HealthPoint[] healthPointArr);

    public native void SetGPSInfo(double d, double d2, double d3, double d4, int i, long j);

    public native boolean SetParam(HealthParamKey healthParamKey, String str);

    public native boolean StartTrace();

    public native void StopTrace();

    public void SetTrackInfo(TrackInfo trackInfo) {
        NativeSetTrackInfo(trackInfo.getPtr());
    }
}
