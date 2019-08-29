package com.amap.bundle.cloudconfig.appinit.request;

import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.mapstorage.MapSharePreference;
import de.greenrobot.event.EventBus;
import org.json.JSONObject;

public class AppInitCallback extends BaseAppInitAndSwitchCallback {
    public static final String SP_KEY_endTime = "endTime";
    public static final String SP_KEY_startTime = "startTime";
    public static final String SP_NAME_AmapCloudControlAgooXML = "AmapCloudControlAgooXML";

    public void callback(String str) {
        LogFormat("AppInitCallback callback: %s", str);
        if (lt.a().a(str, false)) {
            handleResponser();
        }
    }

    public void error(Throwable th, boolean z) {
        LogFormat("AppInitCallback error", th, new Object[0]);
    }

    private void handleResponser() {
        LogFormat("AppInitCallback. handleResponser start.", new Object[0]);
        a();
        handlePushServiceRuntime();
        handleRealtimeBus();
        handleTaxiService();
        handleShareBicycleSwitch();
        LogFormat("AppInitCallback. handleResponser end.", new Object[0]);
    }

    private void handlePushServiceRuntime() {
        MapSharePreference mapSharePreference = new MapSharePreference((String) SP_NAME_AmapCloudControlAgooXML);
        mapSharePreference.putLongValue("startTime", lt.a().c.h);
        mapSharePreference.putLongValue(SP_KEY_endTime, lt.a().c.i);
    }

    private void handleRealtimeBus() {
        JSONObject jSONObject = lt.a().c.j;
        if (jSONObject != null) {
            FunctionSupportConfiger.getInst().decode(jSONObject, FunctionSupportConfiger.ROUTE_BUS_TAG);
        }
    }

    private void handleTaxiService() {
        JSONObject jSONObject = lt.a().c.m;
        if (jSONObject != null) {
            FunctionSupportConfiger.getInst().decode(jSONObject, FunctionSupportConfiger.TAXI_TAG);
        }
    }

    private void handleShareBicycleSwitch() {
        EventBus.getDefault().post(lt.a().c.w);
    }
}
