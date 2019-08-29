package com.autonavi.inter.impl;

import com.autonavi.inter.IServiceLoader;
import java.util.HashMap;
import java.util.Map;

public final class ServiceLoaderImpl implements IServiceLoader {
    private static final Map<Class, Class> sMap;

    static {
        HashMap hashMap = new HashMap();
        sMap = hashMap;
        hashMap.putAll(new AMAP_MODULE_OPERATION_ServiceImpl_DATA());
        sMap.putAll(new AMAP_MODULE_COMMON_ServiceImpl_DATA());
        sMap.putAll(new BEHAVIORTRACKER_ServiceImpl_DATA());
        sMap.putAll(new EYRIEADAPTER_ServiceImpl_DATA());
        sMap.putAll(new APPUPGRADE_ServiceImpl_DATA());
        sMap.putAll(new CLOUDSYNC_ServiceImpl_DATA());
        sMap.putAll(new COMMUTE_ServiceImpl_DATA());
        sMap.putAll(new FAVORITES_ServiceImpl_DATA());
        sMap.putAll(new FEATUREGUIDE_ServiceImpl_DATA());
        sMap.putAll(new FEEDBACK_ServiceImpl_DATA());
        sMap.putAll(new FOOTRESULT_ServiceImpl_DATA());
        sMap.putAll(new FREQUENTLOCATION_ServiceImpl_DATA());
        sMap.putAll(new NOTIFICATION_ServiceImpl_DATA());
        sMap.putAll(new OFFLINEMAP_API_ServiceImpl_DATA());
        sMap.putAll(new QRCODE_ServiceImpl_DATA());
        sMap.putAll(new RIDERESULT_ServiceImpl_DATA());
        sMap.putAll(new SHAREBIKE_ServiceImpl_DATA());
        sMap.putAll(new SPLASHSCREEN_ServiceImpl_DATA());
        sMap.putAll(new TRAFFICEVENT_ServiceImpl_DATA());
        sMap.putAll(new TRAFFICEVENT_API_ServiceImpl_DATA());
        sMap.putAll(new VUI_ServiceImpl_DATA());
        sMap.putAll(new WALLET_ServiceImpl_DATA());
        sMap.putAll(new WEBVIEW_ServiceImpl_DATA());
        sMap.putAll(new AMAPHOME_ServiceImpl_DATA());
        sMap.putAll(new CAROWNERSERVICE_ServiceImpl_DATA());
        sMap.putAll(new EVALUATE_ServiceImpl_DATA());
        sMap.putAll(new LIFE_ServiceImpl_DATA());
        sMap.putAll(new MAIN_ServiceImpl_DATA());
        sMap.putAll(new MAPHOME_ServiceImpl_DATA());
        sMap.putAll(new ROUTEPLAN_ServiceImpl_DATA());
        sMap.putAll(new SEARCHRESULT_ServiceImpl_DATA());
        sMap.putAll(new UITEMPLATE_ServiceImpl_DATA());
        sMap.putAll(new DRIVE_ServiceImpl_DATA());
        sMap.putAll(new PLANHOME_ServiceImpl_DATA());
        sMap.putAll(new TRIPGROUP_ServiceImpl_DATA());
        sMap.putAll(new SCHOOLBUS_ServiceImpl_DATA());
        sMap.putAll(new MAPBASE_ServiceImpl_DATA());
    }

    public final Class getService(Class cls) {
        return sMap.get(cls);
    }
}
