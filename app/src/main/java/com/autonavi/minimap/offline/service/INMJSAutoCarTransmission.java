package com.autonavi.minimap.offline.service;

import java.util.ArrayList;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface INMJSAutoCarTransmission {
    boolean autoConnectStatus();

    void bindDownloadApk(IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void deleteCities(ArrayList<String> arrayList, IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void getAutoCarState(IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void initApkInfo(IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void pauseDownloadApk(int i);

    void prepareSendApk(String str, IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void registerAutoLinkListener(IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void removeApk(int i);

    void requestCityListInfo(IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void resumeDownloadApk(int i);

    void screenKeepScreenLit(boolean z);

    void startDownloadApk(String str, String str2, String str3, String str4, String str5, long j, String str6);

    void startSendAllCities(String str, IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void startSendApk(String str, IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void stopDownloadApk(int i);

    void stopSendApk(IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void stopSendCities(String str, IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void stopSendCity(IAutoOfflineJsCallback iAutoOfflineJsCallback);

    void unRegisterAutoListener();

    void unbindDownloadApk();
}
