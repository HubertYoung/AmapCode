package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"getCloudConfig", "registerCallback"}, jsActions = {"com.amap.bundle.jsadapter.action.GetCloudConfigAction", "com.amap.bundle.jsadapter.action.RegisterCallbackAction"}, module = "jsadapter")
@KeepName
public final class JSADAPTER_JsAction_DATA extends HashMap<String, Class<?>> {
    public JSADAPTER_JsAction_DATA() {
        put("getCloudConfig", wd.class);
        put("registerCallback", we.class);
    }
}
