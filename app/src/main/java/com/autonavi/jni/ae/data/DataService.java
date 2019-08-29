package com.autonavi.jni.ae.data;

import com.autonavi.jni.ae.bl.Parcel;

public final class DataService {
    private static volatile DataService mInstance;
    private DataPathManager mDataPathManager;
    private long mShadow;

    private static native void nativeDestroy();

    private native int nativeGetAdminCode(long j, int i, int i2);

    private static native Parcel[] nativeGetAllCities();

    private native Parcel nativeGetAreaExtraInfo(long j, int i);

    private native String nativeGetDataVersion(long j, int i);

    private static native String nativeGetEngineVersion();

    private native int nativeGetGlobalDBDataVersion(long j);

    private native int nativeGetGlobalDBFormatVersion(long j);

    private native long nativeInit();

    private static native void nativeInit(String str, String str2, String str3, String str4, String str5);

    private DataService() {
        this.mShadow = 0;
        this.mShadow = nativeInit();
        if (this.mShadow != 0) {
            this.mDataPathManager = new DataPathManager(this.mShadow);
        }
    }

    private boolean isValid() {
        return this.mShadow != 0;
    }

    public static DataService getInstance() {
        if (mInstance == null) {
            synchronized (DataService.class) {
                try {
                    if (mInstance == null) {
                        DataService dataService = new DataService();
                        mInstance = dataService;
                        if (!dataService.isValid()) {
                            mInstance = null;
                        }
                    }
                }
            }
        }
        return mInstance;
    }

    public final DataPathManager getDataPathManager() {
        return this.mDataPathManager;
    }

    public static void destroy() {
        if (mInstance != null) {
            nativeDestroy();
            mInstance = null;
        }
    }

    public final int getAdminCode(int i, int i2) {
        return nativeGetAdminCode(this.mShadow, i, i2);
    }

    public final String getDataVersion(int i) {
        return nativeGetDataVersion(this.mShadow, i);
    }

    public final int getGlobalDBFormatVersion() {
        return nativeGetGlobalDBFormatVersion(this.mShadow);
    }

    public final int getGlobalDBDataVersion() {
        return nativeGetGlobalDBDataVersion(this.mShadow);
    }

    public static String getEngineVersion() {
        return nativeGetEngineVersion();
    }

    public final ADCityInfo[] getAllCities() {
        Parcel[] nativeGetAllCities = nativeGetAllCities();
        if (nativeGetAllCities == null) {
            return null;
        }
        ADCityInfo[] aDCityInfoArr = new ADCityInfo[nativeGetAllCities.length];
        for (int i = 0; i < aDCityInfoArr.length; i++) {
            ADCityInfo aDCityInfo = new ADCityInfo();
            aDCityInfo.readFromParcel(nativeGetAllCities[i]);
            aDCityInfoArr[i] = aDCityInfo;
        }
        return aDCityInfoArr;
    }

    public final AreaExtraInfo getAreaExtraInfo(int i) {
        Parcel nativeGetAreaExtraInfo = nativeGetAreaExtraInfo(this.mShadow, i);
        if (nativeGetAreaExtraInfo == null) {
            return null;
        }
        AreaExtraInfo areaExtraInfo = new AreaExtraInfo();
        areaExtraInfo.readFromParcel(nativeGetAreaExtraInfo);
        return areaExtraInfo;
    }
}
