package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.trafficcondiction.TrafficConditionHelper"}, inters = {"com.autonavi.map.ITrafficConditionHelper"}, module = "trafficevent-api")
@KeepName
public final class TRAFFICEVENT_API_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public TRAFFICEVENT_API_ServiceImpl_DATA() {
        put(bqx.class, eme.class);
    }
}
