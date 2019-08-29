package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.offline.intent.inter.IOfflineIntentDispatcherFactory;
import com.autonavi.minimap.offline.intent.inter.impl.OfflineIntentDispatcherFactoryImpl;
import com.autonavi.minimap.offline.model.IFilePathHelper;
import com.autonavi.minimap.offline.model.impl.FilePathHelperImpl;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.offline.model.impl.FilePathHelperImpl", "com.autonavi.minimap.offline.intent.inter.impl.OfflineIntentDispatcherFactoryImpl"}, inters = {"com.autonavi.minimap.offline.model.IFilePathHelper", "com.autonavi.minimap.offline.intent.inter.IOfflineIntentDispatcherFactory"}, module = "offlinemap-api")
@KeepName
public final class OFFLINEMAP_API_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public OFFLINEMAP_API_ServiceImpl_DATA() {
        put(IFilePathHelper.class, FilePathHelperImpl.class);
        put(IOfflineIntentDispatcherFactory.class, OfflineIntentDispatcherFactoryImpl.class);
    }
}
