package com.jiuyan.inimage.util;

import android.text.TextUtils;

/* compiled from: UrlUtil */
public class r {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            if ('?' == charArray[i]) {
                sb.append("%3F");
            } else if (' ' == charArray[i]) {
                sb.append("%20");
            } else if ('/' == charArray[i]) {
                sb.append("%2F");
            } else if ('%' == charArray[i]) {
                sb.append("%25");
            } else if ('#' == charArray[i]) {
                sb.append("%23");
            } else if ('&' == charArray[i]) {
                sb.append("%26");
            } else if ('=' == charArray[i]) {
                sb.append("%3D");
            } else {
                sb.append(charArray[i]);
            }
        }
        return sb.toString();
    }
}
