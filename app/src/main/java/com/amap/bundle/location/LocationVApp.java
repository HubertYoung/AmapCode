package com.amap.bundle.location;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.VirtualApp;
import com.autonavi.jni.ae.pos.LocManager;
import com.autonavi.wing.VirtualAllLifecycleApplication;

@VirtualApp(priority = 100)
public class LocationVApp extends VirtualAllLifecycleApplication {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppEnterForeground() {
        super.vAppEnterForeground();
        AMapLog.d("LocationVApp", "vAppEnterForeground");
        LocManager.setAMapStatu(0);
    }

    public void vAppEnterBackground() {
        super.vAppEnterBackground();
        AMapLog.d("LocationVApp", "vAppEnterBackground");
        LocManager.setAMapStatu(1);
    }
}
