package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.bundle.commute.CommuteServiceImpl"}, inters = {"com.autonavi.map.core.ICommuteService"}, module = "commute")
@KeepName
public final class COMMUTE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public COMMUTE_ServiceImpl_DATA() {
        put(brm.class, atz.class);
    }
}
