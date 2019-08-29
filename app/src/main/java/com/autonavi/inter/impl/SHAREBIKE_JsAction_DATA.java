package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"getInfoForShareBike"}, jsActions = {"com.autonavi.common.js.action.GetInfoForShareBike"}, module = "sharebike")
@KeepName
public final class SHAREBIKE_JsAction_DATA extends HashMap<String, Class<?>> {
    public SHAREBIKE_JsAction_DATA() {
        put("getInfoForShareBike", bky.class);
    }
}
