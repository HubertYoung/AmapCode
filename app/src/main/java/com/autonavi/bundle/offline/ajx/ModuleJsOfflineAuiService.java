package com.autonavi.bundle.offline.ajx;

import android.support.annotation.NonNull;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.offline.service.IJsOfflineAuiServiceProxy3;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@AjxModule("offlineResourceService")
@KeepPublicClassMembers
@KeepName
public class ModuleJsOfflineAuiService extends AbstractModule {
    private IJsOfflineAuiServiceProxy3 mAuiServiceProxy = ((IJsOfflineAuiServiceProxy3) ank.a(IJsOfflineAuiServiceProxy3.class));

    @AjxMethod("gotoFAQPage")
    @Deprecated
    public void gotoFAQPage() {
    }

    @AjxMethod("gotoFeedbackPage")
    @Deprecated
    public void gotoFeedbackPage() {
    }

    @AjxMethod("viewMap")
    @Deprecated
    public void viewMap(String str) {
    }

    public ModuleJsOfflineAuiService(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("getAllCityInfo")
    public void getAllCityInfo(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getAllCityInfo(jsFunctionCallback);
    }

    @AjxMethod("getHotCityInfo")
    public void getHotCityInfo(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getHotCityInfo(jsFunctionCallback);
    }

    @AjxMethod("getAllDownloadCityList")
    public void getAllDownloadCityList(String str, JsFunctionCallback jsFunctionCallback) {
        StringBuilder sb = new StringBuilder("getAllDownloadCityList---");
        sb.append(this.mAuiServiceProxy);
        AMapLog.d("AJX3-ENGINE", sb.toString());
        this.mAuiServiceProxy.getAllDownloadCityList(str, jsFunctionCallback);
    }

    @AjxMethod("performDownloadCmd")
    public void performDownloadCmd(int i, String str) {
        this.mAuiServiceProxy.performDownloadCmd(i, str);
    }

    @AjxMethod("getDeviceSpaceInfo")
    public void getDeviceSpaceInfo(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getDeviceSpaceInfo(jsFunctionCallback);
    }

    @AjxMethod("getFreeDeviceSpace")
    public void getFreeDeviceSpace(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getFreeDeviceSpace(jsFunctionCallback);
    }

    @AjxMethod("getAlongWayCityInfo")
    public void getAlongWayCityInfo(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getAllAlongWayCity(jsFunctionCallback);
    }

    @AjxMethod("getCitiesInProvinces")
    public void getCitiesInProvinces(String str, JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getCitiesInProvinces(str, jsFunctionCallback);
    }

    @AjxMethod("requestCarRouteAlongCities")
    public void requestAlongWayCities(int i, int i2, JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.requestAlongWayCities(i, i2, jsFunctionCallback);
    }

    @AjxMethod("getSdCardList")
    public void getSdCardList(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getSdCardList(jsFunctionCallback);
    }

    @AjxMethod("bindObserverForAllCities")
    public void bindObserverForAllCities(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.bindObserverForAllCities(jsFunctionCallback);
    }

    @AjxMethod("switchOfflineData")
    public void switchOfflineData(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.switchOfflineData(str, str2, jsFunctionCallback);
    }

    @AjxMethod("switchOfflineDataCheck")
    public void switchOfflineDataCheck(String str, JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.switchOfflineDataCheck(str, jsFunctionCallback);
    }

    @AjxMethod("isDownloadingOfflineData")
    public void isDownloadingOfflineData(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.isDownloadingOfflineData(jsFunctionCallback);
    }

    @AjxMethod("getOfflineMapSwitchState")
    public void getOfflineMapSwitchState(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getOfflineMapSwitchState(jsFunctionCallback);
    }

    @AjxMethod("setOfflineMapSwitchState")
    public void setOfflineMapSwitchState(String str) {
        this.mAuiServiceProxy.setOfflineMapSwitchState(str);
    }

    @AjxMethod("getOfflineNaviSwitchState")
    public void getOfflineNaviSwitchState(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getOfflineNaviSwitchState(jsFunctionCallback);
    }

    @AjxMethod("setOfflineNaviSwitchState")
    public void setOfflineNaviSwitchState(String str) {
        this.mAuiServiceProxy.setOfflineNaviSwitchState(str);
    }

    @AjxMethod("getAutoDownloadInWifiSwitchState")
    public void getAutoDownloadInWifiSwitchState(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getAutoDownloadInWifiSwitchState(jsFunctionCallback);
    }

    @AjxMethod("setAutoDownloadInWifiSwitchState")
    public void setAutoDownloadInWifiSwitchState(String str) {
        this.mAuiServiceProxy.setAutoDownloadInWifiSwitchState(str);
    }

    @AjxMethod("getSavedTraffic")
    public void getSavedTraffic(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getSavedTraffic(jsFunctionCallback);
    }

    @AjxMethod("getCurrentCityDownloadInfo")
    public void getCurrentCityDownloadInfo(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getCurrentCityDownloadInfo(jsFunctionCallback);
    }

    @AjxMethod("autoCarHasConnection")
    public boolean autoCarHasConnection() {
        return this.mAuiServiceProxy.autoCarHasConnection();
    }

    @AjxMethod("readNewFeaturesWithPageID")
    public void readNewFeaturesWithPageID(String str) {
        this.mAuiServiceProxy.readNewFeaturesWithPageID(str);
    }

    @AjxMethod("hasNewFeaturesWithPageID")
    public void hasNewFeaturesWithPageID(String str, JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.hasNewFeaturesWithPageID(str, jsFunctionCallback);
    }

    @AjxMethod("isDownloaded")
    public void isDownloaded(String str, JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.isDownloaded(str, jsFunctionCallback);
    }

    @AjxMethod("backupConfig")
    public void backupConfig() {
        this.mAuiServiceProxy.backupConfig();
    }

    @AjxMethod("checkInit")
    public void checkInit(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.checkInit(jsFunctionCallback);
    }

    @AjxMethod("waitInit")
    public void waitInit(JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.waitInit(jsFunctionCallback);
    }

    @AjxMethod("getCityDownloadStatusWithAdcode")
    public void getCityDownloadStatusWithAdcode(String str, JsFunctionCallback jsFunctionCallback) {
        this.mAuiServiceProxy.getCityDownloadStatusWithAdcode(str, jsFunctionCallback);
    }

    @AjxMethod("value2json")
    @Deprecated
    public String value2json(long j) {
        return this.mAuiServiceProxy.value2json(j);
    }

    @AjxMethod("setResult")
    @Deprecated
    public void setResult() {
        if (this.mAuiServiceProxy != null) {
            this.mAuiServiceProxy.setResult();
        }
    }

    @AjxMethod(invokeMode = "sync", value = "isOfflineDataUpdate")
    public boolean isOfflineDataUpdate() {
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager != null) {
            return iOfflineManager.isOfflineDataUpdate();
        }
        return false;
    }
}
