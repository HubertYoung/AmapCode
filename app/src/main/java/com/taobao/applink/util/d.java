package com.taobao.applink.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;

@TargetApi(9)
public class d {
    private static String a = "";
    private static String b = "";
    private static String c = "";

    public static String a(Context context) {
        if (!TextUtils.isEmpty(a) && !"unknow".equals(a)) {
            return a;
        }
        String trim = "unknow".trim();
        a = trim;
        return trim;
    }
}
