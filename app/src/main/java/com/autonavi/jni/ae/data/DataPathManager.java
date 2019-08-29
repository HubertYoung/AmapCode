package com.autonavi.jni.ae.data;

import android.text.TextUtils;

public final class DataPathManager {
    private long mShadow = 0;

    private native String nativeGet3DDataPath(long j);

    private native String nativeGetDataPath(long j);

    private native String nativeGetDiffPath(long j);

    private native String nativeGetLogPath(long j);

    private native String nativeGetOfflinePath(long j);

    private native String nativeGetResPath(long j);

    private native String nativeGetRootPath(long j);

    private native String nativeGetSubPath(long j, String str);

    private native long nativeInit(long j);

    private native void nativeSet3DDataPath(long j, String str);

    private native boolean nativeSetConfigData(long j, String str, String str2);

    private native boolean nativeSetConfigFile(long j, String str, String str2);

    private native void nativeSetOfflinePath(long j, String str);

    DataPathManager(long j) {
        this.mShadow = nativeInit(j);
    }

    public final boolean setConfigFile(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return nativeSetConfigFile(this.mShadow, str, str2);
    }

    public final boolean setConfigData(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return nativeSetConfigData(this.mShadow, str, str2);
    }

    public final String getRootPath() {
        return nativeGetRootPath(this.mShadow);
    }

    public final String getDataPath() {
        return nativeGetDataPath(this.mShadow);
    }

    public final String getResPath() {
        return nativeGetResPath(this.mShadow);
    }

    public final String getLogPath() {
        return nativeGetLogPath(this.mShadow);
    }

    public final String getDiffPath() {
        return nativeGetDiffPath(this.mShadow);
    }

    public final String getSubPath(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return nativeGetSubPath(this.mShadow, str);
    }

    public final void setOfflinePath(String str) {
        if (!TextUtils.isEmpty(str)) {
            nativeSetOfflinePath(this.mShadow, str);
        }
    }

    public final String getOfflinePath() {
        return nativeGetOfflinePath(this.mShadow);
    }

    public final void set3DDataPath(String str) {
        if (!TextUtils.isEmpty(str)) {
            nativeSet3DDataPath(this.mShadow, str);
        }
    }

    public final String get3DDataPath() {
        return nativeGet3DDataPath(this.mShadow);
    }
}
