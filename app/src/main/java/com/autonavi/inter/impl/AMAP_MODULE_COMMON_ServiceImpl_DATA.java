package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.abtest.src.AbtestImpl", "com.amap.common.impl.PageBackImpl", "com.autonavi.map.SuspendUtils"}, inters = {"com.autonavi.abtest.infer.IABTest", "com.amap.common.inter.IPageBack", "com.autonavi.map.suspend.inter.ISuspendUtils"}, module = "amap_module_common")
@KeepName
public final class AMAP_MODULE_COMMON_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public AMAP_MODULE_COMMON_ServiceImpl_DATA() {
        put(akn.class, ako.class);
        put(ajw.class, ajt.class);
        put(cct.class, brb.class);
    }
}
