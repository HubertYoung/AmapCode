package com.autonavi.inter.impl;

import com.autonavi.bundle.setting.SettingVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class SETTING_VirtualApp_DATA extends ArrayList<Class<?>> {
    public SETTING_VirtualApp_DATA() {
        add(SettingVApp.class);
    }
}
