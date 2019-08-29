package com.autonavi.inter.impl;

import com.autonavi.minimap.bundle.share.ShareVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class SHARE_VirtualApp_DATA extends ArrayList<Class<?>> {
    public SHARE_VirtualApp_DATA() {
        add(ShareVApp.class);
    }
}
