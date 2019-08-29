package com.xiaomi.slim;

import android.text.TextUtils;
import com.xiaomi.push.protobuf.b.c;
import com.xiaomi.push.service.aq.b;
import java.util.HashMap;

class a {
    public static void a(b bVar, String str, com.xiaomi.smack.a aVar) {
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String str8;
        c cVar = new c();
        if (!TextUtils.isEmpty(bVar.c)) {
            cVar.a(bVar.c);
        }
        if (!TextUtils.isEmpty(bVar.f)) {
            cVar.d(bVar.f);
        }
        if (!TextUtils.isEmpty(bVar.g)) {
            cVar.e(bVar.g);
        }
        cVar.b(bVar.e ? "1" : "0");
        cVar.c(!TextUtils.isEmpty(bVar.d) ? bVar.d : "XIAOMI-SASL");
        b bVar2 = new b();
        bVar2.c(bVar.b);
        bVar2.a(Integer.parseInt(bVar.h));
        bVar2.b(bVar.a);
        bVar2.a((String) "BIND", (String) null);
        bVar2.a(bVar2.h());
        StringBuilder sb = new StringBuilder("[Slim]: bind id=");
        sb.append(bVar2.h());
        com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
        HashMap hashMap = new HashMap();
        hashMap.put("challenge", str);
        hashMap.put("token", bVar.c);
        hashMap.put("chid", bVar.h);
        hashMap.put("from", bVar.b);
        hashMap.put("id", bVar2.h());
        hashMap.put("to", "xiaomi.com");
        if (bVar.e) {
            str2 = "kick";
            str3 = "1";
        } else {
            str2 = "kick";
            str3 = "0";
        }
        hashMap.put(str2, str3);
        if (!TextUtils.isEmpty(bVar.f)) {
            str4 = "client_attrs";
            str5 = bVar.f;
        } else {
            str4 = "client_attrs";
            str5 = "";
        }
        hashMap.put(str4, str5);
        if (!TextUtils.isEmpty(bVar.g)) {
            str6 = "cloud_attrs";
            str7 = bVar.g;
        } else {
            str6 = "cloud_attrs";
            str7 = "";
        }
        hashMap.put(str6, str7);
        if (bVar.d.equals("XIAOMI-PASS") || bVar.d.equals("XMPUSH-PASS")) {
            str8 = com.xiaomi.channel.commonutils.string.b.a(bVar.d, null, hashMap, bVar.i);
        } else {
            bVar.d.equals("XIAOMI-SASL");
            str8 = null;
        }
        cVar.f(str8);
        bVar2.a(cVar.c(), (String) null);
        aVar.b(bVar2);
    }

    public static void a(String str, String str2, com.xiaomi.smack.a aVar) {
        b bVar = new b();
        bVar.c(str2);
        bVar.a(Integer.parseInt(str));
        bVar.a((String) "UBND", (String) null);
        aVar.b(bVar);
    }
}
