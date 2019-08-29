package com.autonavi.minimap.offline.service.impl;

import com.autonavi.minimap.offline.auto.protocol.OfflineAutoManager;
import com.autonavi.minimap.offline.service.IAutoOfflineJsCallback;
import com.autonavi.minimap.offline.service.INMJSAutoCarTransmission;
import java.util.ArrayList;

public class NMJSAutoCarTransmissionImpl implements INMJSAutoCarTransmission {
    private OfflineAutoManager mOfflineAutoManager = new OfflineAutoManager();

    public void requestCityListInfo(IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.requestCityListInfo(iAutoOfflineJsCallback);
    }

    public void deleteCities(ArrayList<String> arrayList, IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.deleteCities(arrayList, iAutoOfflineJsCallback);
    }

    public void getAutoCarState(IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.getAutoCarState(iAutoOfflineJsCallback);
    }

    public void startSendAllCities(String str, IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.startSendAllCities(str, iAutoOfflineJsCallback);
    }

    public void stopSendCity(IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.stopSendCity(iAutoOfflineJsCallback);
    }

    public void startSendApk(String str, IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.startSendApk(str, iAutoOfflineJsCallback);
    }

    public void stopSendApk(IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.stopSendApk();
    }

    public void initApkInfo(IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.initApkInfo(iAutoOfflineJsCallback);
    }

    public void prepareSendApk(String str, IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.prepareSendApk(str, iAutoOfflineJsCallback);
    }

    public void bindDownloadApk(IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.bindDownloadApk(iAutoOfflineJsCallback);
    }

    public void unbindDownloadApk() {
        this.mOfflineAutoManager.unbindDownloadApk();
    }

    public void startDownloadApk(String str, String str2, String str3, String str4, String str5, long j, String str6) {
        this.mOfflineAutoManager.startDownloadApk(str, str2, str3, str4, str5, j, str6);
    }

    public void pauseDownloadApk(int i) {
        this.mOfflineAutoManager.pauseDownloadApk(i);
    }

    public void resumeDownloadApk(int i) {
        this.mOfflineAutoManager.resumeDownloadApk(i);
    }

    public void stopDownloadApk(int i) {
        this.mOfflineAutoManager.stopDownloadApk(i);
    }

    public void removeApk(int i) {
        this.mOfflineAutoManager.removeApk(i);
    }

    public void registerAutoLinkListener(IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.registerAutoLinkListener(iAutoOfflineJsCallback);
    }

    public void unRegisterAutoListener() {
        this.mOfflineAutoManager.unRegisterAutoListener();
    }

    public boolean autoConnectStatus() {
        return this.mOfflineAutoManager.autoConnectStatus();
    }

    public void stopSendCities(String str, IAutoOfflineJsCallback iAutoOfflineJsCallback) {
        this.mOfflineAutoManager.stopSendCities(str, iAutoOfflineJsCallback);
    }

    public void screenKeepScreenLit(boolean z) {
        this.mOfflineAutoManager.screenKeepScreenLit(z);
    }
}
