package com.autonavi.minimap.offline.service;

import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IJsOfflineAuiServiceProxy3 {
    boolean autoCarHasConnection();

    void backupConfig();

    void bindObserverForAllCities(JsFunctionCallback jsFunctionCallback);

    void checkInit(JsFunctionCallback jsFunctionCallback);

    void doStorageChange(String str);

    void getAllAlongWayCity(JsFunctionCallback jsFunctionCallback);

    void getAllCityInfo(JsFunctionCallback jsFunctionCallback);

    void getAllDownloadCityList(String str, JsFunctionCallback jsFunctionCallback);

    void getAutoDownloadInWifiSwitchState(JsFunctionCallback jsFunctionCallback);

    void getCitiesInProvinces(String str, JsFunctionCallback jsFunctionCallback);

    void getCityDownloadStatusWithAdcode(String str, JsFunctionCallback jsFunctionCallback);

    void getCurrentCityDownloadInfo(JsFunctionCallback jsFunctionCallback);

    String getCurrentOfflineStoragePath();

    void getDeviceSpaceInfo(JsFunctionCallback jsFunctionCallback);

    void getFreeDeviceSpace(JsFunctionCallback jsFunctionCallback);

    void getHotCityInfo(JsFunctionCallback jsFunctionCallback);

    void getOfflineMapSwitchState(JsFunctionCallback jsFunctionCallback);

    void getOfflineNaviSwitchState(JsFunctionCallback jsFunctionCallback);

    void getSavedTraffic(JsFunctionCallback jsFunctionCallback);

    void getSdCardList(JsFunctionCallback jsFunctionCallback);

    void gotoFAQPage();

    void gotoFeedbackPage();

    void hasNewFeaturesWithPageID(String str, JsFunctionCallback jsFunctionCallback);

    void isDownloaded(String str, JsFunctionCallback jsFunctionCallback);

    void isDownloadingOfflineData(JsFunctionCallback jsFunctionCallback);

    void performDownloadCmd(int i, String str);

    void readNewFeaturesWithPageID(String str);

    void requestAlongWayCities(int i, int i2, JsFunctionCallback jsFunctionCallback);

    void setAutoDownloadInWifiSwitchState(String str);

    void setOfflineMapSwitchState(String str);

    void setOfflineNaviSwitchState(String str);

    void setResult();

    void switchOfflineData(String str, String str2, JsFunctionCallback jsFunctionCallback);

    void switchOfflineDataCheck(String str, JsFunctionCallback jsFunctionCallback);

    String value2json(long j);

    void viewMap(String str);

    void waitInit(JsFunctionCallback jsFunctionCallback);
}
