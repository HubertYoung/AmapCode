package com.autonavi.inter.impl;

import com.autonavi.minimap.drive.bundle.DriveVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class TRIPGROUP_VirtualApp_DATA extends ArrayList<Class<?>> {
    public TRIPGROUP_VirtualApp_DATA() {
        add(DriveVApp.class);
    }
}
