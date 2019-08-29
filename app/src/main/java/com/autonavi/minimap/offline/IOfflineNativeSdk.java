package com.autonavi.minimap.offline;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.autonavi.minimap.offlinesdk.ICityDownloadObserver;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.IDataInitObserver;
import com.autonavi.minimap.offlinesdk.IDownloadManager;
import com.autonavi.minimap.offlinesdk.OfflineConfig;

public interface IOfflineNativeSdk {
    void bindObserverForAllCities(ICityDownloadObserver iCityDownloadObserver);

    void destroy();

    @Nullable
    ICityManager getCityManager();

    @Nullable
    IDownloadManager getDownloadManager();

    @Nullable
    OfflineConfig getOfflineConfig();

    @Nullable
    String getOfflineEngineVersion();

    void init();

    void init(@NonNull OfflineConfig offlineConfig, @NonNull IDataInitObserver iDataInitObserver);

    boolean isInit();

    void notifyConfigChanged(String str, String str2);

    void setAE8UpdateFlag(boolean z);

    void waitOfflineDataReady();
}
