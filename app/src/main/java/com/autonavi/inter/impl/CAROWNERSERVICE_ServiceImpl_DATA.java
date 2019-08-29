package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.bundle.carownerservice.api.ISyncVehicles;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.basemap.inter.impl.SyncVehiclesImpl", "com.autonavi.map.db.inter.LocalDBManagerImpl"}, inters = {"com.autonavi.bundle.carownerservice.api.ISyncVehicles", "com.autonavi.map.db.inter.ILocalDBManager"}, module = "carownerservice")
@KeepName
public final class CAROWNERSERVICE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public CAROWNERSERVICE_ServiceImpl_DATA() {
        put(ISyncVehicles.class, cpu.class);
        put(bsw.class, bsx.class);
    }
}
