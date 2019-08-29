package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.map.mapinterface.IMapRequestManager;
import com.autonavi.minimap.basemap.common.inter.impl.MapEventListenerImpl;
import com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl;
import com.autonavi.minimap.bundle.maphome.api.IMapEventListener;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.basemap.intent.inner.OperationIntentDispatcherImpl", "com.autonavi.minimap.basemap.common.inter.impl.MapRequestManagerImpl", "com.autonavi.minimap.basemap.common.inter.impl.MapEventListenerImpl"}, inters = {"com.autonavi.minimap.basemap.intent.inner.IOperationIntentDispatcher", "com.autonavi.map.mapinterface.IMapRequestManager", "com.autonavi.minimap.bundle.maphome.api.IMapEventListener"}, module = "amap_module_operation")
@KeepName
public final class AMAP_MODULE_OPERATION_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public AMAP_MODULE_OPERATION_ServiceImpl_DATA() {
        put(cpn.class, cpo.class);
        put(IMapRequestManager.class, MapRequestManagerImpl.class);
        put(IMapEventListener.class, MapEventListenerImpl.class);
    }
}
