package com.autonavi.inter.impl;

import com.amap.bundle.drive.CarTruckInfoManagerImpl;
import com.amap.bundle.drive.api.ICarTruckInfoManager;
import com.amap.bundle.drive.api.IMotorGuideManager;
import com.amap.bundle.drive.api.ITruckGuideManager;
import com.amap.bundle.drive.guide.MotorGuideManagerImpl;
import com.amap.bundle.drive.guide.TruckGuideManagerImpl;
import com.amap.bundle.drive.offline.DriveOfflineManagerImpl;
import com.amap.bundle.drive.offline.IDriveOfflineManager;
import com.amap.bundle.drive.voice.VoiceDriveDispatcherImp;
import com.amap.bundle.voiceservice.dispatch.IVoiceDriveDispatcher;
import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.amap.bundle.drive.result.DriveRouteManagerIml", "com.amap.bundle.drive.DriveUtilImpl", "com.amap.bundle.drive.offline.DriveOfflineManagerImpl", "com.amap.bundle.drive.guide.MotorGuideManagerImpl", "com.amap.bundle.drive.CarTruckInfoManagerImpl", "com.amap.bundle.drive.DriveNaviManagerImpl", "com.amap.bundle.drive.voice.VoiceDriveDispatcherImp", "com.amap.bundle.drive.guide.TruckGuideManagerImpl"}, inters = {"com.autonavi.minimap.drive.route.IDriveRouteManager", "com.autonavi.minimap.drive.tools.IDriveUtil", "com.amap.bundle.drive.offline.IDriveOfflineManager", "com.amap.bundle.drive.api.IMotorGuideManager", "com.amap.bundle.drive.api.ICarTruckInfoManager", "com.autonavi.minimap.drive.navi.IDriveNaviManager", "com.amap.bundle.voiceservice.dispatch.IVoiceDriveDispatcher", "com.amap.bundle.drive.api.ITruckGuideManager"}, module = "drive")
@KeepName
public final class DRIVE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public DRIVE_ServiceImpl_DATA() {
        put(dhz.class, on.class);
        put(djk.class, mp.class);
        put(IDriveOfflineManager.class, DriveOfflineManagerImpl.class);
        put(IMotorGuideManager.class, MotorGuideManagerImpl.class);
        put(ICarTruckInfoManager.class, CarTruckInfoManagerImpl.class);
        put(dfm.class, mm.class);
        put(IVoiceDriveDispatcher.class, VoiceDriveDispatcherImp.class);
        put(ITruckGuideManager.class, TruckGuideManagerImpl.class);
    }
}
