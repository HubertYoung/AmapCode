package com.alipay.mobile.common.utils;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class ViewUtil {
    public ViewUtil() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static String maskString(String target, String mask, int start, int len) {
        StringBuilder masks = new StringBuilder();
        if (mask.length() > 1) {
            mask = mask.substring(0, 1);
        }
        for (int i = 0; i < len; i++) {
            masks.append(mask);
        }
        return target.substring(0, start) + masks.toString() + target.substring(start + len);
    }
}
