package com.xiaomi.push.service;

import android.content.Context;
import com.autonavi.common.SuperId;
import com.xiaomi.channel.commonutils.android.a;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.service.aq.b;
import java.util.Locale;

public class s {
    public final String a;
    public final String b;
    public final String c;
    public final String d;
    public final String e;
    public final String f;
    public final int g;

    public s(String str, String str2, String str3, String str4, String str5, String str6, int i) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = str5;
        this.f = str6;
        this.g = i;
    }

    public static boolean a() {
        try {
            return Class.forName("miui.os.Build").getField("IS_ALPHA_BUILD").getBoolean(null);
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean a(Context context) {
        return "com.xiaomi.xmsf".equals(context.getPackageName()) && a();
    }

    private static boolean b(Context context) {
        return context.getPackageName().equals("com.xiaomi.xmsf");
    }

    public b a(XMPushService xMPushService) {
        b bVar = new b(xMPushService);
        a(bVar, xMPushService, xMPushService.e(), SuperId.BIT_1_NEARBY_SEARCH);
        return bVar;
    }

    public b a(b bVar, Context context, e eVar, String str) {
        bVar.a = context.getPackageName();
        bVar.b = this.a;
        bVar.i = this.c;
        bVar.c = this.b;
        bVar.h = "5";
        bVar.d = "XMPUSH-PASS";
        bVar.e = false;
        String str2 = "";
        if (b(context)) {
            str2 = a.a(context);
        }
        bVar.f = String.format("%1$s:%2$s,%3$s:%4$s,%5$s:%6$s:%7$s:%8$s", new Object[]{"sdk_ver", Integer.valueOf(36), "cpvn", "3_6_2", "cpvc", Integer.valueOf(30602), "aapn", str2});
        bVar.g = String.format("%1$s:%2$s,%3$s:%4$s,%5$s:%6$s,sync:1", new Object[]{"appid", b(context) ? "1000271" : this.d, "locale", Locale.getDefault().toString(), Constants.EXTRA_KEY_MIID, p.a(context).c()});
        if (a(context)) {
            StringBuilder sb = new StringBuilder();
            sb.append(bVar.g);
            sb.append(String.format(",%1$s:%2$s", new Object[]{"ab", str}));
            bVar.g = sb.toString();
        }
        bVar.k = eVar;
        return bVar;
    }
}
