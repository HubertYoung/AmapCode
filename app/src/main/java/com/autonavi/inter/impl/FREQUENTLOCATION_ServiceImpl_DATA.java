package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.bundle.frequentlocation.FrequentLocationController", "com.autonavi.minimap.bundle.frequentlocation.service.FrequentLocationService"}, inters = {"com.autonavi.com.autonavi.minimap.bundle.frequentlocation.api.IFrequentLocationService.IFrequentLoactionControoler", "com.autonavi.com.autonavi.minimap.bundle.frequentlocation.api.IFrequentLocationService"}, module = "frequentlocation")
@KeepName
public final class FREQUENTLOCATION_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public FREQUENTLOCATION_ServiceImpl_DATA() {
        put(a.class, cya.class);
        put(bhz.class, cyh.class);
    }
}
