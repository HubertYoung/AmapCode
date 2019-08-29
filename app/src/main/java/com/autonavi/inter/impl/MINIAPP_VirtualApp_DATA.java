package com.autonavi.inter.impl;

import com.autonavi.bundle.miniapp.MiniAppVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class MINIAPP_VirtualApp_DATA extends ArrayList<Class<?>> {
    public MINIAPP_VirtualApp_DATA() {
        add(MiniAppVApp.class);
    }
}
