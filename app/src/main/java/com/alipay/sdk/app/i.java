package com.alipay.sdk.app;

import com.alipay.sdk.util.h;

public final class i {
    public static String a;

    private static void a(String str) {
        a = str;
    }

    private static String b() {
        return a;
    }

    public static String a() {
        j a2 = j.a(j.CANCELED.h);
        return a(a2.h, a2.i, "");
    }

    private static String c() {
        j a2 = j.a(j.DOUBLE_REQUEST.h);
        return a(a2.h, a2.i, "");
    }

    private static String d() {
        j a2 = j.a(j.PARAMS_ERROR.h);
        return a(a2.h, a2.i, "");
    }

    public static String a(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("resultStatus={");
        sb.append(i);
        sb.append("};memo={");
        sb.append(str);
        sb.append("};result={");
        sb.append(str2);
        sb.append(h.d);
        return sb.toString();
    }
}
