package com.autonavi.inter.impl;

import com.amap.bundle.network.NetworkVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class NETWORK_VirtualApp_DATA extends ArrayList<Class<?>> {
    public NETWORK_VirtualApp_DATA() {
        add(NetworkVApp.class);
    }
}
