package com.autonavi.inter.impl;

import com.autonavi.minimap.bundle.evaluate.callback.EvaluateLifecycleCallbacks;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class EVALUATE_VirtualApp_DATA extends ArrayList<Class<?>> {
    public EVALUATE_VirtualApp_DATA() {
        add(EvaluateLifecycleCallbacks.class);
    }
}
