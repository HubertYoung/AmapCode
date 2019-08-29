package com.autonavi.inter.impl;

import com.autonavi.inter.IJsActionLoader;
import java.util.HashMap;
import java.util.Map;

public final class JsActionLoaderImpl implements IJsActionLoader {
    private static final Map<String, Class> sMap;

    static {
        HashMap hashMap = new HashMap();
        sMap = hashMap;
        hashMap.putAll(new AMAP_MODULE_OPERATION_JsAction_DATA());
        sMap.putAll(new JSADAPTER_JsAction_DATA());
        sMap.putAll(new ACCOUNT_JsAction_DATA());
        sMap.putAll(new ACTIVITIES_JsAction_DATA());
        sMap.putAll(new BUSLINE_JsAction_DATA());
        sMap.putAll(new FAVORITES_JsAction_DATA());
        sMap.putAll(new FEEDBACK_JsAction_DATA());
        sMap.putAll(new PHOTOSELECT_JsAction_DATA());
        sMap.putAll(new ROUTECOMMUTE_JsAction_DATA());
        sMap.putAll(new SEARCHAROUND_JsAction_DATA());
        sMap.putAll(new SHAREBIKE_JsAction_DATA());
        sMap.putAll(new TRAIN_JsAction_DATA());
        sMap.putAll(new WEBVIEW_JsAction_DATA());
        sMap.putAll(new IMAGEPREVIEW_JsAction_DATA());
        sMap.putAll(new LIFE_JsAction_DATA());
        sMap.putAll(new PHOTOUPLOAD_JsAction_DATA());
        sMap.putAll(new SEARCHRESULT_JsAction_DATA());
        sMap.putAll(new SHARE_JsAction_DATA());
        sMap.putAll(new SUBWAY_JsAction_DATA());
        sMap.putAll(new UITEMPLATE_JsAction_DATA());
    }

    public final Class getJsAction(String str) {
        return sMap.get(str);
    }
}
