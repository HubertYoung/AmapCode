package com.autonavi.minimap.ajx3.util;

import android.text.TextUtils;

public class UriUtils {
    public static String appendToPath(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            int indexOf = str.indexOf(63);
            if (indexOf > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(str.substring(0, indexOf));
                sb.append(str2);
                sb.append(str.substring(indexOf));
                return sb.toString();
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(str2);
        return sb2.toString();
    }

    public static String removeParams(String str) {
        if (!TextUtils.isEmpty(str)) {
            int indexOf = str.indexOf(63);
            if (indexOf > 0) {
                return str.substring(0, indexOf);
            }
        }
        return str;
    }

    public static String addParam(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        if (str.indexOf(63) > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("&");
            sb.append(str2);
            sb.append("=");
            sb.append(str3);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("?");
        sb2.append(str2);
        sb2.append("=");
        sb2.append(str3);
        return sb2.toString();
    }
}
