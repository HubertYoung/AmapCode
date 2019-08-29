package com.autonavi.minimap.util;

import com.amap.bundle.mapstorage.MapSharePreference;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PerformanceSchemeConfig {

    @Retention(RetentionPolicy.SOURCE)
    public @interface AutoSecSwitchMode {
    }

    public static int a() {
        return new MapSharePreference((String) "PerformanceConfig").getIntValue("performance_autosec_mode", 0);
    }
}
