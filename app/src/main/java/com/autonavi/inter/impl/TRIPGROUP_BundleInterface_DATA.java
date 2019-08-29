package com.autonavi.inter.impl;

import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.minimap.drive.inter.impl.AutoRemoteControllerImpl;
import java.util.HashMap;
import proguard.annotation.KeepName;

@KeepName
public final class TRIPGROUP_BundleInterface_DATA extends HashMap {
    public TRIPGROUP_BundleInterface_DATA() {
        put(afz.class, chb.class);
        put(IAutoRemoteController.class, AutoRemoteControllerImpl.class);
        put(afx.class, emw.class);
    }
}
