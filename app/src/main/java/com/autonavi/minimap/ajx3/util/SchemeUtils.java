package com.autonavi.minimap.ajx3.util;

import android.text.TextUtils;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;

public class SchemeUtils {
    public static String isLocalSupport(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return AjxFileInfo.getFullPathByUri(str);
    }
}
