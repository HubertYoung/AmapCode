package com.autonavi.inter.impl;

import com.amap.bundle.voiceservice.dispatch.IVoiceFootDispatcher;
import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.route.voice.VoiceFootDispatcherImp;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.route.voice.VoiceFootDispatcherImp"}, inters = {"com.amap.bundle.voiceservice.dispatch.IVoiceFootDispatcher"}, module = "footresult")
@KeepName
public final class FOOTRESULT_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public FOOTRESULT_ServiceImpl_DATA() {
        put(IVoiceFootDispatcher.class, VoiceFootDispatcherImp.class);
    }
}
