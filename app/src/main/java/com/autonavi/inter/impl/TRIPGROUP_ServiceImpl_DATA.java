package com.autonavi.inter.impl;

import com.amap.bundle.tripgroup.api.IVehicleInfoEvent;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.drive.inter.impl.DefaultVoiceLzlImpl;
import com.autonavi.minimap.drive.inter.impl.VehicleInfoEventImpl;
import com.autonavi.minimap.drive.inter.impl.VoicePackageManagerImpl;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.drive.navi.navitts_dependencies.externalimport.impl.ExternalServiceImpl", "com.autonavi.minimap.drive.inter.impl.VehicleInfoEventImpl", "com.autonavi.minimap.drive.inter.impl.DefaultVoiceLzlImpl", "com.autonavi.minimap.drive.inter.impl.VoicePackageManagerImpl"}, inters = {"com.autonavi.minimap.drive.navi.navitts_dependencies.externalimport.IExternalService", "com.amap.bundle.tripgroup.api.IVehicleInfoEvent", "com.amap.bundle.tripgroup.api.IDefaultVoiceLzl", "com.amap.bundle.tripgroup.api.IVoicePackageManager"}, module = "tripgroup")
@KeepName
public final class TRIPGROUP_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public TRIPGROUP_ServiceImpl_DATA() {
        put(dhf.class, dhg.class);
        put(IVehicleInfoEvent.class, VehicleInfoEventImpl.class);
        put(afy.class, DefaultVoiceLzlImpl.class);
        put(IVoicePackageManager.class, VoicePackageManagerImpl.class);
    }
}
