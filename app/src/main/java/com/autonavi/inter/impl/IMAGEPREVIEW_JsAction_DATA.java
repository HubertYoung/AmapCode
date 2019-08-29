package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"imagePreview"}, jsActions = {"com.autonavi.map.search.imagepreview.jsaction.ImagePreviewAction"}, module = "imagepreview")
@KeepName
public final class IMAGEPREVIEW_JsAction_DATA extends HashMap<String, Class<?>> {
    public IMAGEPREVIEW_JsAction_DATA() {
        put("imagePreview", bye.class);
    }
}
