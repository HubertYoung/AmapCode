package com.autonavi.ae.bl.search;

import com.autonavi.jni.ae.bl.Parcel;

public class BLSearchEngine {
    protected long mShadow = nativeCreate();

    private native void nativeAbort(long j, long j2);

    private native long nativeCreate();

    private static native boolean nativeDbExists(int i);

    private static native Parcel nativeGetAreaExtraInfo(int i);

    private static native String nativeGetDataVersion(int i);

    private static native String nativeGetSdkVersion();

    private native boolean nativeSearch(long j, long j2);

    public static void setDeps() {
    }

    public boolean search(BLSearchRequest bLSearchRequest) {
        if (this.mShadow == 0) {
            return false;
        }
        if (bLSearchRequest != null) {
            return nativeSearch(this.mShadow, bLSearchRequest.shadow());
        }
        return true;
    }

    public void abort(BLSearchRequest bLSearchRequest) {
        if (!(this.mShadow == 0 || bLSearchRequest == null)) {
            nativeAbort(this.mShadow, bLSearchRequest.shadow());
        }
    }

    public void abortAll() {
        if (this.mShadow != 0) {
            nativeAbort(this.mShadow, 0);
        }
    }

    public void destroy() {
        this.mShadow = 0;
    }

    public static boolean dbExists(int i) {
        return nativeDbExists(i);
    }

    public static String getDataVersion(int i) {
        return nativeGetDataVersion(i);
    }

    public static String getSdkVersion() {
        return nativeGetSdkVersion();
    }

    public static Parcel getAreaExtraInfo(int i) {
        return nativeGetAreaExtraInfo(i);
    }
}
