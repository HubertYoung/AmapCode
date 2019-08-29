package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"addNewPoint", "errorReport"}, jsActions = {"com.autonavi.minimap.basemap.jsaction.AddNewPointAction", "com.autonavi.minimap.basemap.jsaction.ErrorReportAction"}, module = "feedback")
@KeepName
public final class FEEDBACK_JsAction_DATA extends HashMap<String, Class<?>> {
    public FEEDBACK_JsAction_DATA() {
        put("addNewPoint", cpw.class);
        put("errorReport", cpy.class);
    }
}
