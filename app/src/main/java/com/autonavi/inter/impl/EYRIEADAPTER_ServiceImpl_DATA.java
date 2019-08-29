package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.amap.bundle.eyrieadapter.EyrieABTest"}, inters = {"com.amap.bundle.eyrieadapter.IEyrieABTest"}, module = "eyrieadapter")
@KeepName
public final class EYRIEADAPTER_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public EYRIEADAPTER_ServiceImpl_DATA() {
        put(vg.class, vb.class);
    }
}
