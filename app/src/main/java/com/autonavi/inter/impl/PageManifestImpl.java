package com.autonavi.inter.impl;

import com.autonavi.inter.IPageManifest;
import java.util.HashMap;
import java.util.Map;

public final class PageManifestImpl implements IPageManifest {
    private static final Map<String, Class> sMap;

    static {
        HashMap hashMap = new HashMap();
        sMap = hashMap;
        hashMap.putAll(new AMAP_MODULE_OPERATION_PageAction_DATA());
        sMap.putAll(new AMAP_MODULE_COMMON_PageAction_DATA());
        sMap.putAll(new QRSCAN_PageScheme_DATA());
        sMap.putAll(new ACTIVITIES_PageAction_DATA());
        sMap.putAll(new BUSCARD_PageAction_DATA());
        sMap.putAll(new BUSLINE_PageAction_DATA());
        sMap.putAll(new CITYSELECT_PageAction_DATA());
        sMap.putAll(new COACH_PageAction_DATA());
        sMap.putAll(new FAVORITES_PageAction_DATA());
        sMap.putAll(new FEEDBACK_PageAction_DATA());
        sMap.putAll(new PHOTOSELECT_PageAction_DATA());
        sMap.putAll(new QRCODE_PageAction_DATA());
        sMap.putAll(new ROUTECOMMUTE_PageAction_DATA());
        sMap.putAll(new SEARCHAROUND_PageAction_DATA());
        sMap.putAll(new SEARCHHOME_PageAction_DATA());
        sMap.putAll(new SHAREBIKE_PageScheme_DATA());
        sMap.putAll(new TRAFFICEVENT_PageAction_DATA());
        sMap.putAll(new TRAIN_PageAction_DATA());
        sMap.putAll(new WEBVIEW_PageAction_DATA());
        sMap.putAll(new AMAPHOME_PageAction_DATA());
        sMap.putAll(new BUSNAVI_PageScheme_DATA());
        sMap.putAll(new CAROWNERSERVICE_PageAction_DATA());
        sMap.putAll(new CAROWNERSERVICE_PageScheme_DATA());
        sMap.putAll(new COMMENTEDIT_PageAction_DATA());
        sMap.putAll(new ENVIRONMENTMAP_PageAction_DATA());
        sMap.putAll(new LOCATIONSELECT_PageAction_DATA());
        sMap.putAll(new PHOTOUPLOAD_PageAction_DATA());
        sMap.putAll(new SCENICAREA_PageAction_DATA());
        sMap.putAll(new SEARCHRESULT_PageAction_DATA());
        sMap.putAll(new SEARCHRESULT_PageScheme_DATA());
        sMap.putAll(new SETTING_PageAction_DATA());
        sMap.putAll(new TOOLBOX_PageAction_DATA());
        sMap.putAll(new DRIVE_PageAction_DATA());
        sMap.putAll(new PLANHOME_PageAction_DATA());
        sMap.putAll(new TRIPGROUP_PageAction_DATA());
        sMap.putAll(new PAY_PageAction_DATA());
    }

    public final Class getPage(String str) {
        return sMap.get(str);
    }
}
