package com.alipay.mobile.beehive.util;

import android.os.Bundle;

public class BundleUtil {
    public static void setDefaultValueString(Bundle bundle, String key, String value) {
        if (!bundle.containsKey(key)) {
            bundle.putString(key, value);
        }
    }

    public static void setDefaultValueBoolean(Bundle bundle, String key, boolean value) {
        if (!bundle.containsKey(key)) {
            bundle.putBoolean(key, value);
        }
    }
}
