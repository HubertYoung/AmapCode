package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.route.sharebike.view.ShareBikeBlueBarPolicyImpl"}, inters = {"com.autonavi.bundle.routecommon.inter.IShareBikeBlueBarPolicy"}, module = "sharebike")
@KeepName
public final class SHAREBIKE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public SHAREBIKE_ServiceImpl_DATA() {
        put(axg.class, ehy.class);
    }
}
