package com.autonavi.inter.impl;

import com.autonavi.inter.IBundleInterfaceLoader;
import java.util.HashMap;
import java.util.Map;

public final class BundleInterfaceLoaderImpl implements IBundleInterfaceLoader {
    private static final Map<Class, Class> sInterfaceMap;

    static {
        HashMap hashMap = new HashMap();
        sInterfaceMap = hashMap;
        hashMap.putAll(new QRSCAN_BundleInterface_DATA());
        sInterfaceMap.putAll(new MINIAPP_BundleInterface_DATA());
        sInterfaceMap.putAll(new EYRIEADAPTER_BundleInterface_DATA());
        sInterfaceMap.putAll(new ACCOUNT_BundleInterface_DATA());
        sInterfaceMap.putAll(new ACTIVITIES_BundleInterface_DATA());
        sInterfaceMap.putAll(new BUSCARD_BundleInterface_DATA());
        sInterfaceMap.putAll(new BUSLINE_BundleInterface_DATA());
        sInterfaceMap.putAll(new CARLOGO_BundleInterface_DATA());
        sInterfaceMap.putAll(new CLOUDRES_BundleInterface_DATA());
        sInterfaceMap.putAll(new CLOUDSYNC_BundleInterface_DATA());
        sInterfaceMap.putAll(new COACH_BundleInterface_DATA());
        sInterfaceMap.putAll(new FAVORITES_BundleInterface_DATA());
        sInterfaceMap.putAll(new FEATUREGUIDE_BundleInterface_DATA());
        sInterfaceMap.putAll(new FEED_BundleInterface_DATA());
        sInterfaceMap.putAll(new FOOTNAVI_BundleInterface_DATA());
        sInterfaceMap.putAll(new FOOTRESULT_BundleInterface_DATA());
        sInterfaceMap.putAll(new FREQUENTLOCATION_BundleInterface_DATA());
        sInterfaceMap.putAll(new HEADUNIT_BundleInterface_DATA());
        sInterfaceMap.putAll(new HEALTHYRIDE_BundleInterface_DATA());
        sInterfaceMap.putAll(new HEALTHYRUN_BundleInterface_DATA());
        sInterfaceMap.putAll(new IMPRESSIONREPORTER_BundleInterface_DATA());
        sInterfaceMap.putAll(new MSGBOX_BundleInterface_DATA());
        sInterfaceMap.putAll(new NOTIFICATION_BundleInterface_DATA());
        sInterfaceMap.putAll(new PHOTOSELECT_BundleInterface_DATA());
        sInterfaceMap.putAll(new QRCODE_BundleInterface_DATA());
        sInterfaceMap.putAll(new REALTIMEBUS_BundleInterface_DATA());
        sInterfaceMap.putAll(new RIDENAVI_BundleInterface_DATA());
        sInterfaceMap.putAll(new RIDERESULT_BundleInterface_DATA());
        sInterfaceMap.putAll(new ROUTECOMMUTE_BundleInterface_DATA());
        sInterfaceMap.putAll(new RUNRECOMMEND_BundleInterface_DATA());
        sInterfaceMap.putAll(new SEARCHAROUND_BundleInterface_DATA());
        sInterfaceMap.putAll(new SEARCHHOME_BundleInterface_DATA());
        sInterfaceMap.putAll(new SHAREBIKE_BundleInterface_DATA());
        sInterfaceMap.putAll(new TRAFFICEVENT_BundleInterface_DATA());
        sInterfaceMap.putAll(new TRAIN_BundleInterface_DATA());
        sInterfaceMap.putAll(new VUI_BundleInterface_DATA());
        sInterfaceMap.putAll(new WEBVIEW_BundleInterface_DATA());
        sInterfaceMap.putAll(new AGROUP_BundleInterface_DATA());
        sInterfaceMap.putAll(new AIRTICKET_BundleInterface_DATA());
        sInterfaceMap.putAll(new AMAPHOME_BundleInterface_DATA());
        sInterfaceMap.putAll(new BUSNAVI_BundleInterface_DATA());
        sInterfaceMap.putAll(new CAROWNERSERVICE_BundleInterface_DATA());
        sInterfaceMap.putAll(new ENVIRONMENTMAP_BundleInterface_DATA());
        sInterfaceMap.putAll(new IMAGEPREVIEW_BundleInterface_DATA());
        sInterfaceMap.putAll(new LIFE_BundleInterface_DATA());
        sInterfaceMap.putAll(new LOCATIONSELECT_BundleInterface_DATA());
        sInterfaceMap.putAll(new PHOTOUPLOAD_BundleInterface_DATA());
        sInterfaceMap.putAll(new ROUTEPLAN_BundleInterface_DATA());
        sInterfaceMap.putAll(new SEARCHRESULT_BundleInterface_DATA());
        sInterfaceMap.putAll(new SETTING_BundleInterface_DATA());
        sInterfaceMap.putAll(new SHARE_BundleInterface_DATA());
        sInterfaceMap.putAll(new SUBWAY_BundleInterface_DATA());
        sInterfaceMap.putAll(new TOOLBOX_BundleInterface_DATA());
        sInterfaceMap.putAll(new UGC_BundleInterface_DATA());
        sInterfaceMap.putAll(new DRIVE_BundleInterface_DATA());
        sInterfaceMap.putAll(new SEARCHSERVICE_BundleInterface_DATA());
        sInterfaceMap.putAll(new PLANHOME_BundleInterface_DATA());
        sInterfaceMap.putAll(new TRIPGROUP_BundleInterface_DATA());
        sInterfaceMap.putAll(new VOICESERVICE_BundleInterface_DATA());
        sInterfaceMap.putAll(new MAPBASE_BundleInterface_DATA());
    }

    public final Class getBundle(Class cls) {
        return sInterfaceMap.get(cls);
    }
}
