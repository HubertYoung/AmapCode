package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.bundle.featureguide.util.GuideSplashManager"}, inters = {"com.autonavi.minimap.bundle.featureguide.api.IGuideSplashManager"}, module = "featureguide")
@KeepName
public final class FEATUREGUIDE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public FEATUREGUIDE_ServiceImpl_DATA() {
        put(cxs.class, cxv.class);
    }
}
