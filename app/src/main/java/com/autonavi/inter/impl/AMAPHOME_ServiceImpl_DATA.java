package com.autonavi.inter.impl;

import com.amap.bundle.voiceservice.dispatch.IVoiceOperationDispatcher;
import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.bundle.amaphome.dialog.UpdateRichTextDialog;
import com.autonavi.bundle.amaphome.vui.VoiceOperationDispatcherImpl;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.bundle.amaphome.compat.service.MainMapService", "com.autonavi.bundle.amaphome.dialog.UpdateRichTextDialog", "com.autonavi.bundle.amaphome.vui.VoiceOperationManagerImpl", "com.autonavi.bundle.amaphome.dialog.LocationCheckDialog", "com.autonavi.bundle.amaphome.vui.VoiceOperationDispatcherImpl", "com.autonavi.minimap.bundle.amaphome.ab.ABClassLoader", "com.autonavi.bundle.amaphome.dialog.MIUIV6Dialog", "com.autonavi.bundle.amaphome.compat.service.FeedService", "com.autonavi.bundle.amaphome.dialog.MainMapMsgDialog"}, inters = {"com.autonavi.minimap.bundle.maphome.service.IMainMapService", "com.amap.bundle.appupgrade.IUpdateRichTextDialog", "com.autonavi.bundle.amaphome.api.IVoiceOperationManager", "com.autonavi.map.suspend.refactor.gps.ILocationCheckDialog", "com.amap.bundle.voiceservice.dispatch.IVoiceOperationDispatcher", "com.autonavi.map.mvp.framework.IABClassLoader", "com.autonavi.minimap.basemap.common.inter.impl.IMIUIV6Dialog", "com.autonavi.minimap.bundle.maphome.service.IFeedService", "com.autonavi.minimap.bundle.msgbox.util.IMainMapMsgDialog"}, module = "amaphome")
@KeepName
public final class AMAPHOME_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public AMAPHOME_ServiceImpl_DATA() {
        put(IMainMapService.class, aqg.class);
        put(jq.class, UpdateRichTextDialog.class);
        put(apu.class, arv.class);
        put(ceh.class, aqy.class);
        put(IVoiceOperationDispatcher.class, VoiceOperationDispatcherImpl.class);
        put(buh.class, cul.class);
        put(cof.class, aqz.class);
        put(czg.class, aqd.class);
        put(dav.class, ara.class);
    }
}
