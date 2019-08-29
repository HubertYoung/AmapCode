package com.autonavi.inter.impl;

import com.amap.bundle.planhome.voice.IVoiceRouteDispatcherImp;
import com.amap.bundle.voiceservice.dispatch.IVoiceRouteDispatcher;
import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.amap.bundle.planhome.voice.IVoiceRouteDispatcherImp"}, inters = {"com.amap.bundle.voiceservice.dispatch.IVoiceRouteDispatcher"}, module = "planhome")
@KeepName
public final class PLANHOME_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public PLANHOME_ServiceImpl_DATA() {
        put(IVoiceRouteDispatcher.class, IVoiceRouteDispatcherImp.class);
    }
}
