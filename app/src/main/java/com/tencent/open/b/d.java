package com.tencent.open.b;

import android.os.SystemClock;
import com.tencent.open.utils.Util;

/* compiled from: ProGuard */
public class d {
    protected static d a;

    protected d() {
    }

    public static synchronized d a() {
        d dVar;
        synchronized (d.class) {
            try {
                if (a == null) {
                    a = new d();
                }
                dVar = a;
            }
        }
        return dVar;
    }

    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) {
        g.a().a(Util.composeViaReportParams(str, str4, str5, str3, str2, str6, "", str7, str8, "", "", ""), str2, false);
    }

    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        g.a().a(Util.composeViaReportParams(str, str4, str5, str3, str2, str6, str7, "", "", str8, str9, str10), str2, false);
    }

    public void a(int i, String str, String str2, String str3, String str4, Long l, int i2, int i3, String str5) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - l.longValue();
        long j = 0;
        if (l.longValue() != 0 && elapsedRealtime >= 0) {
            j = elapsedRealtime;
        }
        StringBuffer stringBuffer = new StringBuffer("http://c.isdspeed.qq.com/code.cgi");
        stringBuffer.append("?domain=mobile.opensdk.com&cgi=opensdk&type=");
        stringBuffer.append(i);
        stringBuffer.append("&code=");
        stringBuffer.append(i2);
        stringBuffer.append("&time=");
        stringBuffer.append(j);
        stringBuffer.append("&rate=");
        stringBuffer.append(i3);
        stringBuffer.append("&uin=");
        String str6 = str2;
        stringBuffer.append(str6);
        stringBuffer.append("&data=");
        g.a().a(stringBuffer.toString(), "GET", Util.composeHaboCgiReportParams(String.valueOf(i), String.valueOf(i2), String.valueOf(j), String.valueOf(i3), str, str6, str3, str4, str5), true);
    }
}
