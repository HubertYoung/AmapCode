package com.alipay.sdk.packet;

import android.text.TextUtils;
import com.alipay.sdk.app.statistic.c;

public final class a {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String[] split = str.split("&");
        if (split.length == 0) {
            return "";
        }
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        for (String str6 : split) {
            if (TextUtils.isEmpty(str2)) {
                if (!str6.contains("biz_type")) {
                    str2 = null;
                } else {
                    str2 = e(str6);
                }
            }
            if (TextUtils.isEmpty(str3)) {
                if (!str6.contains("biz_no")) {
                    str3 = null;
                } else {
                    str3 = e(str6);
                }
            }
            if (TextUtils.isEmpty(str4)) {
                if (!str6.contains(c.J) || str6.startsWith(c.I)) {
                    str4 = null;
                } else {
                    str4 = e(str6);
                }
            }
            if (TextUtils.isEmpty(str5)) {
                if (!str6.contains("app_userid")) {
                    str5 = null;
                } else {
                    str5 = e(str6);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str2)) {
            StringBuilder sb2 = new StringBuilder("biz_type=");
            sb2.append(str2);
            sb2.append(";");
            sb.append(sb2.toString());
        }
        if (!TextUtils.isEmpty(str3)) {
            StringBuilder sb3 = new StringBuilder("biz_no=");
            sb3.append(str3);
            sb3.append(";");
            sb.append(sb3.toString());
        }
        if (!TextUtils.isEmpty(str4)) {
            StringBuilder sb4 = new StringBuilder("trade_no=");
            sb4.append(str4);
            sb4.append(";");
            sb.append(sb4.toString());
        }
        if (!TextUtils.isEmpty(str5)) {
            StringBuilder sb5 = new StringBuilder("app_userid=");
            sb5.append(str5);
            sb5.append(";");
            sb.append(sb5.toString());
        }
        String sb6 = sb.toString();
        if (sb6.endsWith(";")) {
            sb6 = sb6.substring(0, sb6.length() - 1);
        }
        return sb6;
    }

    private static String b(String str) {
        if (!str.contains("biz_type")) {
            return null;
        }
        return e(str);
    }

    private static String c(String str) {
        if (!str.contains("biz_no")) {
            return null;
        }
        return e(str);
    }

    private static String d(String str) {
        if (!str.contains(c.J) || str.startsWith(c.I)) {
            return null;
        }
        return e(str);
    }

    private static String e(String str) {
        String[] split = str.split("=");
        if (split.length <= 1) {
            return null;
        }
        String str2 = split[1];
        return str2.contains("\"") ? str2.replaceAll("\"", "") : str2;
    }

    private static String f(String str) {
        if (!str.contains("app_userid")) {
            return null;
        }
        return e(str);
    }
}
