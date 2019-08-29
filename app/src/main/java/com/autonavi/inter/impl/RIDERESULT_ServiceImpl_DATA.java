package com.autonavi.inter.impl;

import com.amap.bundle.voiceservice.dispatch.IVoiceRideDispatcher;
import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.route.ride.voice.VoiceRideDispatcherImp;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.route.ride.voice.VoiceRideDispatcherImp"}, inters = {"com.amap.bundle.voiceservice.dispatch.IVoiceRideDispatcher"}, module = "rideresult")
@KeepName
public final class RIDERESULT_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public RIDERESULT_ServiceImpl_DATA() {
        put(IVoiceRideDispatcher.class, VoiceRideDispatcherImp.class);
    }
}
