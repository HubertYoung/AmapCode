package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"searchAround"}, jsActions = {"com.autonavi.bundle.searcharound.jsaction.SearchAroundAction"}, module = "searcharound")
@KeepName
public final class SEARCHAROUND_JsAction_DATA extends HashMap<String, Class<?>> {
    public SEARCHAROUND_JsAction_DATA() {
        put("searchAround", bbl.class);
    }
}
