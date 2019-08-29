package com.alipay.mobile.common.logging.api.utils;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map.Entry;

public class LoggingUtils {
    public static String convertExtParams2String(HashMap<String, String> hashMaps) {
        if (hashMaps == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Entry entry : hashMaps.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (!TextUtils.isEmpty(key)) {
                if (sb.length() != 0) {
                    sb.append("&");
                }
                sb.append(key).append(":").append(value);
            }
        }
        return sb.toString();
    }

    public static StringBuilder convertExtParams2StringBuilder(StringBuilder sb, String key, String value, boolean needAddAnd) {
        if (sb != null && !TextUtils.isEmpty(key)) {
            if (needAddAnd) {
                sb.append("&");
            }
            sb.append(key).append(":").append(value);
        }
        return sb;
    }
}
