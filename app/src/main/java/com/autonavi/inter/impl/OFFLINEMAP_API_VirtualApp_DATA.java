package com.autonavi.inter.impl;

import com.autonavi.bundle.offline.OfflineVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class OFFLINEMAP_API_VirtualApp_DATA extends ArrayList<Class<?>> {
    public OFFLINEMAP_API_VirtualApp_DATA() {
        add(OfflineVApp.class);
    }
}
