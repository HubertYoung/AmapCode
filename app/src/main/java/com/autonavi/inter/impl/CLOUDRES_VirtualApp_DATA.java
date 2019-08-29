package com.autonavi.inter.impl;

import com.amap.bundle.cloudres.CloudResApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class CLOUDRES_VirtualApp_DATA extends ArrayList<Class<?>> {
    public CLOUDRES_VirtualApp_DATA() {
        add(CloudResApp.class);
    }
}
