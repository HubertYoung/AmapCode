package com.autonavi.minimap.offline.service;

import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IJsOfflineAuiServiceProxy {
    boolean autoCarHasConnection();

    void backupConfig();

    boolean checkInit();

    void doStorageChange(String str);

    String getAutoDownloadInWifiSwitchState();

    String getCityDownloadStatusWithAdcode(String str);

    String getCurrentCityDownloadInfo();

    String getCurrentOfflineStoragePath();

    String getFreeDeviceSpace();

    String getOfflineMapSwitchState();

    String getOfflineNaviSwitchState();

    long getSavedTraffic();

    void gotoFAQPage();

    void gotoFeedbackPage();

    boolean hasNewFeaturesWithPageID(String str);

    boolean isDownloaded(String str);

    boolean isDownloadingOfflineData();

    void performDownloadCmd(int i, String str);

    void readNewFeaturesWithPageID(String str);

    void setAutoDownloadInWifiSwitchState(String str);

    void setOfflineMapSwitchState(String str);

    void setOfflineNaviSwitchState(String str);

    void setResult();

    int switchOfflineDataCheck(String str);

    void updateCityData(int[] iArr, int[] iArr2);

    String value2json(long j);

    void viewMap(String str);
}
