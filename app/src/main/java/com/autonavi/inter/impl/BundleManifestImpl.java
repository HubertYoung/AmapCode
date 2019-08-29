package com.autonavi.inter.impl;

import com.autonavi.inter.IBundleManifest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class BundleManifestImpl implements IBundleManifest {
    private static final bqo comparator = new bqo();
    private static final List<Class> sBundleList = new ArrayList();

    static {
        sBundleList.addAll(new AMAP_MODULE_OPERATION_VirtualApp_DATA());
        sBundleList.addAll(new AMAP_MODULE_AJX_VirtualApp_DATA());
        sBundleList.addAll(new QRSCAN_VirtualApp_DATA());
        sBundleList.addAll(new MINIAPP_VirtualApp_DATA());
        sBundleList.addAll(new ACCOUNT_VirtualApp_DATA());
        sBundleList.addAll(new BUSCARD_VirtualApp_DATA());
        sBundleList.addAll(new CARLOGO_VirtualApp_DATA());
        sBundleList.addAll(new CLOUDRES_VirtualApp_DATA());
        sBundleList.addAll(new CLOUDSYNC_VirtualApp_DATA());
        sBundleList.addAll(new COACH_VirtualApp_DATA());
        sBundleList.addAll(new FAVORITES_VirtualApp_DATA());
        sBundleList.addAll(new FEED_VirtualApp_DATA());
        sBundleList.addAll(new FEEDBACK_VirtualApp_DATA());
        sBundleList.addAll(new FOOTRESULT_VirtualApp_DATA());
        sBundleList.addAll(new FREQUENTLOCATION_VirtualApp_DATA());
        sBundleList.addAll(new IMPRESSIONREPORTER_VirtualApp_DATA());
        sBundleList.addAll(new LOCATION_VirtualApp_DATA());
        sBundleList.addAll(new LOTUSPOOL_VirtualApp_DATA());
        sBundleList.addAll(new MSGBOX_VirtualApp_DATA());
        sBundleList.addAll(new NETWORK_VirtualApp_DATA());
        sBundleList.addAll(new OFFLINEMAP_API_VirtualApp_DATA());
        sBundleList.addAll(new ROUTECOMMUTE_VirtualApp_DATA());
        sBundleList.addAll(new SHAREBIKE_VirtualApp_DATA());
        sBundleList.addAll(new SPLASHSCREEN_VirtualApp_DATA());
        sBundleList.addAll(new STATISTICS_VirtualApp_DATA());
        sBundleList.addAll(new TRAFFICEVENT_VirtualApp_DATA());
        sBundleList.addAll(new VUI_VirtualApp_DATA());
        sBundleList.addAll(new WEBVIEW_VirtualApp_DATA());
        sBundleList.addAll(new WING_VirtualApp_DATA());
        sBundleList.addAll(new AGROUP_VirtualApp_DATA());
        sBundleList.addAll(new AIRTICKET_VirtualApp_DATA());
        sBundleList.addAll(new AMAPHOME_VirtualApp_DATA());
        sBundleList.addAll(new CAROWNERSERVICE_VirtualApp_DATA());
        sBundleList.addAll(new CLOUDCONFIG_VirtualApp_DATA());
        sBundleList.addAll(new EVALUATE_VirtualApp_DATA());
        sBundleList.addAll(new MAPHOME_VirtualApp_DATA());
        sBundleList.addAll(new PHOTOUPLOAD_VirtualApp_DATA());
        sBundleList.addAll(new ROUTEPLAN_VirtualApp_DATA());
        sBundleList.addAll(new SEARCHRESULT_VirtualApp_DATA());
        sBundleList.addAll(new SETTING_VirtualApp_DATA());
        sBundleList.addAll(new SHARE_VirtualApp_DATA());
        sBundleList.addAll(new TOOLBOX_VirtualApp_DATA());
        sBundleList.addAll(new UITEMPLATE_VirtualApp_DATA());
        sBundleList.addAll(new ONLINEMONITOR_VirtualApp_DATA());
        sBundleList.addAll(new DRIVE_VirtualApp_DATA());
        sBundleList.addAll(new PLANHOME_VirtualApp_DATA());
        sBundleList.addAll(new TRIPGROUP_VirtualApp_DATA());
        sBundleList.addAll(new VOICESERVICE_VirtualApp_DATA());
        sBundleList.addAll(new SCHOOLBUS_VirtualApp_DATA());
        sBundleList.addAll(new MAPBASE_VirtualApp_DATA());
        Collections.sort(sBundleList, comparator);
    }

    public final List<Class> loadAllBundle() {
        return sBundleList;
    }
}
