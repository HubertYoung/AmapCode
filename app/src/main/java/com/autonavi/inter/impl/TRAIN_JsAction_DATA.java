package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"setSelectedDate"}, jsActions = {"com.autonavi.minimap.route.train.js.action.TrainSelectedDataAction"}, module = "train")
@KeepName
public final class TRAIN_JsAction_DATA extends HashMap<String, Class<?>> {
    public TRAIN_JsAction_DATA() {
        put("setSelectedDate", eis.class);
    }
}
