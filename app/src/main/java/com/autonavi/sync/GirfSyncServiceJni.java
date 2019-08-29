package com.autonavi.sync;

public class GirfSyncServiceJni {
    private long mShadow;

    protected static native GirfSyncServiceJni createSyncInstance(GirfSyncJni girfSyncJni, ICallback iCallback);

    protected static native void destroySyncInstance(GirfSyncServiceJni girfSyncServiceJni);

    /* access modifiers changed from: protected */
    public native int addCar(String str, String str2, int i);

    /* access modifiers changed from: protected */
    public native int deleteCar(String str, String str2, int i, int i2);

    /* access modifiers changed from: protected */
    public native String getCar(String str);

    /* access modifiers changed from: protected */
    public native String getCarList(int i);

    /* access modifiers changed from: protected */
    public native String getFrequentAddress();

    /* access modifiers changed from: protected */
    public native String getOftenUsedCar(int i);

    /* access modifiers changed from: protected */
    public native int removeFrequentAddress();

    /* access modifiers changed from: protected */
    public native int setFrequentAddress(String str);

    /* access modifiers changed from: protected */
    public native int setOftenUsedCar(String str, String str2, int i);

    /* access modifiers changed from: protected */
    public native int updateCar(String str, String str2, String str3, String str4, int i);

    static {
        System.loadLibrary("sync_service_jni");
    }
}
