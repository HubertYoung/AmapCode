package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.bundle.evaluate.api.IEvaluateOperationManager;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.bundle.evaluate.impl.EvaluateOperationManagerImpl"}, inters = {"com.autonavi.minimap.bundle.evaluate.api.IEvaluateOperationManager"}, module = "evaluate")
@KeepName
public final class EVALUATE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public EVALUATE_ServiceImpl_DATA() {
        put(IEvaluateOperationManager.class, cxb.class);
    }
}
