package com.ta.utdid2.android.utils;

import android.os.Build.VERSION;

public class BuildCompatUtils {
    public static boolean isAtLeastQ() {
        if (VERSION.SDK_INT >= 29 || (VERSION.CODENAME.length() == 1 && VERSION.CODENAME.charAt(0) >= 'Q' && VERSION.CODENAME.charAt(0) <= 'Z')) {
            return true;
        }
        return false;
    }
}
