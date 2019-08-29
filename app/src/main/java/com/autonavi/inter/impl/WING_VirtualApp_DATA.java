package com.autonavi.inter.impl;

import com.autonavi.wing.VirtualAllLifecycleApplication;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class WING_VirtualApp_DATA extends ArrayList<Class<?>> {
    public WING_VirtualApp_DATA() {
        add(VirtualAllLifecycleApplication.class);
        add(esh.class);
    }
}
