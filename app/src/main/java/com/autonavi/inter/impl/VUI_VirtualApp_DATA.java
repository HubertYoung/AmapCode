package com.autonavi.inter.impl;

import com.autonavi.bundle.vui.VUIVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class VUI_VirtualApp_DATA extends ArrayList<Class<?>> {
    public VUI_VirtualApp_DATA() {
        add(VUIVApp.class);
    }
}
