package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"pictureUpload"}, jsActions = {"com.autonavi.bundle.photoUpload.jsaction.PictureUploadAction"}, module = "photoupload")
@KeepName
public final class PHOTOUPLOAD_JsAction_DATA extends HashMap<String, Class<?>> {
    public PHOTOUPLOAD_JsAction_DATA() {
        put("pictureUpload", aws.class);
    }
}
