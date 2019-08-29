package com.autonavi.minimap.offline.service.impl;

import android.os.Handler;
import android.os.Looper;
import com.autonavi.minimap.offline.auto.protocol.OfflineAutoManager;
import com.autonavi.minimap.offline.service.IAutoOfflineJsCallback3;
import com.autonavi.minimap.offline.service.INMJSAutoCarTransmission3;
import java.util.ArrayList;

public class NMJSAutoCarTransmission3Impl implements INMJSAutoCarTransmission3 {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public OfflineAutoManager mOfflineAutoManager = new OfflineAutoManager();

    public void requestCityListInfo(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.requestCityListInfo(iAutoOfflineJsCallback3);
    }

    public void deleteCities(ArrayList<String> arrayList, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.deleteCities(arrayList, iAutoOfflineJsCallback3);
    }

    public void getAutoCarState(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.getAutoCarState(iAutoOfflineJsCallback3);
    }

    public void startSendAllCities(String str, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.startSendAllCities(str, iAutoOfflineJsCallback3);
    }

    public void stopSendCity(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.stopSendCity(iAutoOfflineJsCallback3);
    }

    public void startSendApk(String str, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.startSendApk(str, iAutoOfflineJsCallback3);
    }

    public void stopSendApk() {
        this.mOfflineAutoManager.stopSendApk();
    }

    public void initApkInfo(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.initApkInfo(iAutoOfflineJsCallback3);
    }

    public void prepareSendApk(String str, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.prepareSendApk(str, iAutoOfflineJsCallback3);
    }

    public void bindDownloadApk(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.bindDownloadApk(iAutoOfflineJsCallback3);
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

    public void registerAutoLinkListener(IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.registerAutoLinkListener(iAutoOfflineJsCallback3);
    }

    public void unRegisterAutoListener() {
        this.mOfflineAutoManager.unRegisterAutoListener();
    }

    public boolean autoConnectStatus() {
        return this.mOfflineAutoManager.autoConnectStatus();
    }

    public void stopSendCities(String str, IAutoOfflineJsCallback3 iAutoOfflineJsCallback3) {
        this.mOfflineAutoManager.stopSendCities(str, iAutoOfflineJsCallback3);
    }

    public void screenKeepScreenLit(final boolean z) {
        this.mHandler.post(new Runnable() {
            public final void run() {
                NMJSAutoCarTransmission3Impl.this.mOfflineAutoManager.screenKeepScreenLit(z);
            }
        });
    }
}
