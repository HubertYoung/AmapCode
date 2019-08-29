package com.autonavi.inter.impl;

import com.amap.bundle.drive.DriveNaviVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class DRIVE_VirtualApp_DATA extends ArrayList<Class<?>> {
    public DRIVE_VirtualApp_DATA() {
        add(DriveNaviVApp.class);
    }
}
