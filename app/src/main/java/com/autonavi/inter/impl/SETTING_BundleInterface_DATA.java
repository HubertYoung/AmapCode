package com.autonavi.inter.impl;

import com.autonavi.minimap.bundle.setting.api.ISettingService;
import java.util.HashMap;
import proguard.annotation.KeepName;

@KeepName
public final class SETTING_BundleInterface_DATA extends HashMap {
    public SETTING_BundleInterface_DATA() {
        put(ISettingService.class, bdb.class);
    }
}
