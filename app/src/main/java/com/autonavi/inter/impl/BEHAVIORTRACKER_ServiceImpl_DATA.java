package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.amap.bundle.behaviortracker.GDBehaviorTrackerImpl"}, inters = {"com.amap.bundle.behaviortracker.api.IGDBehaviorTracker"}, module = "behaviortracker")
@KeepName
public final class BEHAVIORTRACKER_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public BEHAVIORTRACKER_ServiceImpl_DATA() {
        put(ke.class, kb.class);
    }
}
