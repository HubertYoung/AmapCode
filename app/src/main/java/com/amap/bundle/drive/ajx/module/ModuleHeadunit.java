package com.amap.bundle.drive.ajx.module;

import com.amap.bundle.drive.ajx.inter.HeadunitBtnEventCallback;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@AjxModule("headunit")
@Keep
@KeepClassMembers
public class ModuleHeadunit extends AbstractModule {
    public static final String MODULE_NAME = "headunit";
    private static final String TAG = "ModuleHeadunit";
    private ModuleHeadunitImpl mModuleHeadunitImpl = new ModuleHeadunitImpl();

    public ModuleHeadunit(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("routeResultInfo")
    public void routeResultInfo(String str) {
        AMapLog.i(TAG, "routeResultInfo     ".concat(String.valueOf(str)));
        this.mModuleHeadunitImpl.routeResultInfo(str);
    }

    @AjxMethod("sendNaviRouteInfo")
    public void sendNaviRouteInfo(String str) {
        AMapLog.i(TAG, "sendNaviRouteInfo     ".concat(String.valueOf(str)));
        this.mModuleHeadunitImpl.sendNaviRouteInfo(str);
    }

    @AjxMethod("sendPoiToHeadunit")
    public void sendPoiToHeadunit(String str, JsFunctionCallback jsFunctionCallback) {
        this.mModuleHeadunitImpl.sendPoiToHeadunit(str, jsFunctionCallback);
    }

    @AjxMethod("headunitBtnEvent")
    public void headunitBtnEvent(String str, JsFunctionCallback jsFunctionCallback) {
        this.mModuleHeadunitImpl.headunitBtnEvent(str, jsFunctionCallback);
    }

    public String getRouteInfo() {
        return this.mModuleHeadunitImpl.getRouteInfo();
    }

    public void setHeadunitBtnEventCallback(HeadunitBtnEventCallback headunitBtnEventCallback) {
        this.mModuleHeadunitImpl.setHeadunitBtnEventCallback(headunitBtnEventCallback);
    }

    @AjxMethod(invokeMode = "sync", value = "canShowHeadunit")
    public boolean canShowHeadunit() {
        vp vpVar = (vp) a.a.a(vp.class);
        return vpVar != null && vpVar.b();
    }

    @AjxMethod(invokeMode = "sync", value = "isWifiConnected")
    public boolean isWifiConnected() {
        vp vpVar = (vp) a.a.a(vp.class);
        return vpVar != null && vpVar.c();
    }
}
