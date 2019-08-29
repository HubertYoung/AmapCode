package com.autonavi.bundle.cloudsync;

import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.modules.ModuleCloudSync;

public class CloudSyncVApp extends esh {
    public static final String a = "CloudSyncVApp";

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleCloudSync.class);
    }
}
