package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"getDownloadFromUrlStatus", "showVoiceGuide", "downloadFromUrl", "orderFeature", "freshRoomData", "removeCacheItem", "openNewWebView", "openResourceByPath", "lifeServiceCallBack", "openNearbyCinema", "openGroupBuyMoreSecKill", "triggerFeature", "tuanGou", "openWebView"}, jsActions = {"com.autonavi.minimap.life.common.js.jsaction.GetDownloadStatusAction", "com.autonavi.minimap.life.common.js.jsaction.ShowVoiceGuideAction", "com.autonavi.minimap.life.common.js.jsaction.DownloadUrlAction", "com.autonavi.minimap.life.common.js.jsaction.OrderFeatureAction", "com.autonavi.minimap.life.common.js.jsaction.FreshRoomDataAction", "com.autonavi.minimap.life.common.js.jsaction.RemoveCacheItemAction", "com.autonavi.minimap.life.common.js.jsaction.OpenNewWebViewAction", "com.autonavi.minimap.life.common.js.jsaction.OpenResourceByPathAction", "com.autonavi.minimap.life.common.js.jsaction.LifeServiceCallBackAction", "com.autonavi.minimap.life.common.js.jsaction.OpenNearbyCinemaAction", "com.autonavi.minimap.life.common.js.jsaction.OpenGroupBuyMoreSecKillAction", "com.autonavi.minimap.life.common.js.jsaction.TriggerFeatureAction", "com.autonavi.minimap.life.common.js.jsaction.TuanGouAction", "com.autonavi.minimap.life.common.js.jsaction.OpenWebViewAction"}, module = "life")
@KeepName
public final class LIFE_JsAction_DATA extends HashMap<String, Class<?>> {
    public LIFE_JsAction_DATA() {
        put("getDownloadFromUrlStatus", dnr.class);
        put("showVoiceGuide", doa.class);
        put("downloadFromUrl", dnp.class);
        put("orderFeature", dny.class);
        put("freshRoomData", dnq.class);
        put("removeCacheItem", dnz.class);
        put("openNewWebView", dnv.class);
        put("openResourceByPath", dnw.class);
        put("lifeServiceCallBack", dns.class);
        put("openNearbyCinema", dnu.class);
        put("openGroupBuyMoreSecKill", dnt.class);
        put("triggerFeature", dob.class);
        put("tuanGou", doc.class);
        put("openWebView", dnx.class);
    }
}
