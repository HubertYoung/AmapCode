package com.autonavi.inter.impl;

import com.amap.bundle.voiceservice.dispatch.IVoicePoiSelectorDispatcher;
import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.bundle.vui.business.poiselector.voicecommon.VoicePoiSelectorDispatcherImpl;
import com.autonavi.minimap.vui.IVUIManager;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.bundle.vui.business.poiselector.voicecommon.VoicePoiSelectorDispatcherImpl", "com.autonavi.bundle.vui.impl.VUIOuterServiceImpl", "com.autonavi.vcs.VUIManagerImpl"}, inters = {"com.amap.bundle.voiceservice.dispatch.IVoicePoiSelectorDispatcher", "com.autonavi.bundle.vui.api.IVUIService", "com.autonavi.minimap.vui.IVUIManager"}, module = "vui")
@KeepName
public final class VUI_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public VUI_ServiceImpl_DATA() {
        put(IVoicePoiSelectorDispatcher.class, VoicePoiSelectorDispatcherImpl.class);
        put(bfo.class, bgc.class);
        put(IVUIManager.class, eqf.class);
    }
}
