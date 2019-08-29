package com.alibaba.baichuan.android.trade.utils.code;

import android.util.Base64;

public class Base64Utils {
    public static String getBase64(String str) {
        return Base64.encodeToString(str.getBytes(), 0);
    }

    public static String getFromBase64(String str) {
        return Base64.encodeToString(str.getBytes(), 0);
    }
}
