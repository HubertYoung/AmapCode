package com.alipay.android.phone.inside.log.field;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.cons.Constants;

public abstract class AbstractLogField {
    public static final String[] a = {",", "，"};

    public abstract String a();

    protected static String a(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(a(strArr[0]));
        for (int i = 1; i < strArr.length; i++) {
            sb.append(a[0]);
            String a2 = a(strArr[i]);
            if (!TextUtils.isEmpty(a2)) {
                a2 = a2.replace(Constants.a[0], Constants.a[1]).replace(Constants.b[0], Constants.b[1]).replace(Constants.c[0], Constants.c[1]).replace(Constants.d[0], Constants.d[1]).replace(Constants.e[0], Constants.e[1]).replace(Constants.f[0], Constants.f[1]).replace(Constants.g[0], Constants.g[1]);
            }
            sb.append(a2);
        }
        return sb.toString();
    }

    protected static String b(String... strArr) {
        if (strArr == null || strArr.length <= 0) {
            return "-";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArr.length - 1; i++) {
            sb.append(strArr[i]);
            sb.append(" || ");
        }
        sb.append(strArr[strArr.length - 1]);
        return sb.toString();
    }

    private static String a(String str) {
        return TextUtils.isEmpty(str) ? "-" : str;
    }
}
