package com.alipay.sdk.util;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.app.statistic.a;
import com.alipay.sdk.app.statistic.c;

public final class h {
    public static final String a = "pref_trade_token";
    public static final String b = ";";
    public static final String c = "result={";
    public static final String d = "}";
    public static final String e = "trade_token=\"";
    public static final String f = "\"";
    public static final String g = "trade_token=";

    private static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(";");
        String str2 = null;
        for (int i = 0; i < split.length; i++) {
            if (split[i].startsWith(c) && split[i].endsWith(d)) {
                String[] split2 = split[i].substring(8, split[i].length() - 1).split("&");
                int i2 = 0;
                while (true) {
                    if (i2 < split2.length) {
                        if (split2[i2].startsWith(e) && split2[i2].endsWith("\"")) {
                            str2 = split2[i2].substring(13, split2[i2].length() - 1);
                            break;
                        } else if (split2[i2].startsWith(g)) {
                            str2 = split2[i2].substring(12);
                            break;
                        } else {
                            i2++;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return str2;
    }

    private static String a(Context context) {
        return i.b(context, a, "");
    }

    private static void a(Context context, String str) {
        try {
            String str2 = null;
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split(";");
                String str3 = null;
                for (int i = 0; i < split.length; i++) {
                    if (split[i].startsWith(c) && split[i].endsWith(d)) {
                        String[] split2 = split[i].substring(8, split[i].length() - 1).split("&");
                        int i2 = 0;
                        while (true) {
                            if (i2 < split2.length) {
                                if (split2[i2].startsWith(e) && split2[i2].endsWith("\"")) {
                                    str3 = split2[i2].substring(13, split2[i2].length() - 1);
                                    break;
                                } else if (split2[i2].startsWith(g)) {
                                    str3 = split2[i2].substring(12);
                                    break;
                                } else {
                                    i2++;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
                str2 = str3;
            }
            if (!TextUtils.isEmpty(str2)) {
                i.a(context, a, str2);
            }
        } catch (Throwable th) {
            a.a((String) c.b, (String) c.z, th);
        }
    }
}
