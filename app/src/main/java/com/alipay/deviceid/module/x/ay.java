package com.alipay.deviceid.module.x;

import android.content.Context;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ay {
    public static boolean a() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] strArr = {"2016-11-10 2016-11-11", "2016-12-11 2016-12-12"};
        int i = 0;
        while (i < 2) {
            try {
                String[] split = strArr[i].split(Token.SEPARATOR);
                if (split != null) {
                    if (split.length == 2) {
                        Date date = new Date();
                        StringBuilder sb = new StringBuilder();
                        sb.append(split[0]);
                        sb.append(" 00:00:00");
                        Date parse = simpleDateFormat.parse(sb.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(split[1]);
                        sb2.append(" 23:59:59");
                        Date parse2 = simpleDateFormat.parse(sb2.toString());
                        new StringBuilder("[*] startTime = ").append(simpleDateFormat.format(parse));
                        if (date.after(parse) && date.before(parse2)) {
                            StringBuilder sb3 = new StringBuilder("[*] current time:");
                            sb3.append(simpleDateFormat.format(date));
                            sb3.append(" is in rush hour.");
                            return true;
                        }
                    }
                }
                i++;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static synchronized boolean a(Context context, String str) {
        boolean z;
        synchronized (ay.class) {
            try {
                if (Math.abs(System.currentTimeMillis() - ax.b(context, str)) < 86400000) {
                    z = true;
                }
            } catch (Throwable th) {
                aa.a(th);
            }
            z = false;
        }
        return z;
    }
}
