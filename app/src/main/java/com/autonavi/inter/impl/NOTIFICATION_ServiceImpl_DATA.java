package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.IPushConfigService;
import com.autonavi.minimap.bundle.notification.util.PushConfigServiceImpl;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.bundle.notification.util.ShortcutBadgeServiceImpl", "com.autonavi.minimap.bundle.notification.util.PushConfigServiceImpl"}, inters = {"notification.api.IShortcutBadgeService", "com.autonavi.minimap.IPushConfigService"}, module = "notification")
@KeepName
public final class NOTIFICATION_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public NOTIFICATION_ServiceImpl_DATA() {
        put(fhd.class, dbt.class);
        put(IPushConfigService.class, PushConfigServiceImpl.class);
    }
}
