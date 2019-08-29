package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"addPhoto"}, jsActions = {"com.autonavi.minimap.photograph.jsaction.AddPhotoAction"}, module = "photoselect")
@KeepName
public final class PHOTOSELECT_JsAction_DATA extends HashMap<String, Class<?>> {
    public PHOTOSELECT_JsAction_DATA() {
        put("addPhoto", dtl.class);
    }
}
