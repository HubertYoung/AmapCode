package com.autonavi.jni.bedstone;

import com.autonavi.jni.bedstone.model.FrequentLocationDBInfo;

public class BaseMapFrequentLocationsJni {
    public static native void initDb(String str);

    public native int addData(long j, FrequentLocationDBInfo frequentLocationDBInfo);

    public native int clearAll(long j);

    public native int delItem(long j, String str);

    public native int delItems(long j, String[] strArr);

    public native FrequentLocationDBInfo[] getTopPlace(long j, String[] strArr);

    public native long initFrequentLocations(int i, int i2, int i3, int i4);

    public native int uninit(long j);
}
