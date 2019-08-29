package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"getImmersiveStatusBarHeight"}, jsActions = {"com.autonavi.bundle.uitemplate.ajx.JsActionImmersive"}, module = "uitemplate")
@KeepName
public final class UITEMPLATE_JsAction_DATA extends HashMap<String, Class<?>> {
    public UITEMPLATE_JsAction_DATA() {
        put("getImmersiveStatusBarHeight", bdq.class);
    }
}
