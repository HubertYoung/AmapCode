package com.autonavi.minimap.offlinesdk;

import com.autonavi.jni.ae.bl.Parcel;
import com.autonavi.minimap.ackor.ackoroffline.IOfflineService;

public class OfflineSDK {
    private static OfflineSDK mInstance;
    private CityManager mCityManager;
    private DownloadManager mDownloadManager;
    private long mPtr;

    private native String nativeGetEngineVersion(long j);

    private native long nativeInit(Parcel parcel, IDataInitObserver iDataInitObserver, IOfflineService iOfflineService);

    private native void nativeNotifyConfigChanged(String str, String str2, long j);

    private native void nativeUninit(long j);

    public CityManager getCityManager() {
        return this.mCityManager;
    }

    public DownloadManager getDownloadManager() {
        return this.mDownloadManager;
    }

    public String getEngineVersion() {
        return nativeGetEngineVersion(this.mPtr);
    }

    public void notifyConfigChanged(String str, String str2) {
        nativeNotifyConfigChanged(str, str2, this.mPtr);
    }

    public void destroy() {
        nativeUninit(this.mPtr);
    }

    public void init(OfflineConfig offlineConfig, IDataInitObserver iDataInitObserver, IOfflineService iOfflineService) {
        Parcel parcel = new Parcel();
        offlineConfig.writeToParcel(parcel);
        this.mPtr = nativeInit(parcel, iDataInitObserver, iOfflineService);
        this.mCityManager = new CityManager(this.mPtr);
        this.mDownloadManager = new DownloadManager(this.mPtr);
    }
}
