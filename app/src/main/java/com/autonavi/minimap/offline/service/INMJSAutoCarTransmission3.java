package com.autonavi.minimap.offline.service;

import java.util.ArrayList;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface INMJSAutoCarTransmission3 {
    boolean autoConnectStatus();

    void bindDownloadApk(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void deleteCities(ArrayList<String> arrayList, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void getAutoCarState(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void initApkInfo(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void pauseDownloadApk(int i);

    void prepareSendApk(String str, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void registerAutoLinkListener(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void removeApk(int i);

    void requestCityListInfo(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void resumeDownloadApk(int i);

    void screenKeepScreenLit(boolean z);

    void startDownloadApk(String str, String str2, String str3, String str4, String str5, long j, String str6);

    void startSendAllCities(String str, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void startSendApk(String str, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void stopDownloadApk(int i);

    void stopSendApk();

    void stopSendCities(String str, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void stopSendCity(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3);

    void unRegisterAutoListener();

    void unbindDownloadApk();
}
