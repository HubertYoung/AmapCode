package com.autonavi.minimap.qrcode;

import android.content.SharedPreferences;

public enum FrontLightMode {
    ON,
    AUTO,
    OFF;

    private static FrontLightMode parse(String str) {
        return str == null ? OFF : valueOf(str);
    }

    public static FrontLightMode readPref(SharedPreferences sharedPreferences) {
        return parse(OFF.toString());
    }
}
