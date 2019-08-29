package com.alipay.deviceid.module.x;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class d {
    private static d b;
    private Map<String, String> a = null;

    public static d a() {
        if (b == null) {
            synchronized (d.class) {
                try {
                    if (b == null) {
                        b = new d();
                    }
                }
            }
        }
        return b;
    }

    private void b(Context context) {
        this.a = new TreeMap();
        Map<String, String> map = this.a;
        HashMap hashMap = new HashMap();
        hashMap.put("AC4", ax.b(context));
        map.putAll(hashMap);
        Map<String, String> map2 = this.a;
        m.a();
        HashMap hashMap2 = new HashMap();
        hashMap2.put("AE1", m.b());
        StringBuilder sb = new StringBuilder();
        sb.append(m.c() ? "1" : "0");
        hashMap2.put("AE2", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(m.a(context) ? "1" : "0");
        hashMap2.put("AE3", sb2.toString());
        hashMap2.put("AE4", m.d());
        hashMap2.put("AE5", m.e());
        hashMap2.put("AE6", m.f());
        hashMap2.put("AE7", m.g());
        hashMap2.put("AE8", m.h());
        hashMap2.put("AE9", m.i());
        hashMap2.put("AE10", m.j());
        hashMap2.put("AE11", m.k());
        hashMap2.put("AE12", m.l());
        hashMap2.put("AE13", m.m());
        hashMap2.put("AE14", m.n());
        hashMap2.put("AE15", m.o());
        map2.putAll(hashMap2);
        Map<String, String> map3 = this.a;
        k a2 = k.a();
        HashMap hashMap3 = new HashMap();
        av a3 = au.a(context);
        String a4 = k.a(context);
        String b2 = k.b(context);
        String k = k.k(context);
        String n = k.n(context);
        String m = k.m(context);
        boolean z = true;
        if (a3 != null && e.a(a3.a(), a4) && e.a(a3.b(), b2) && e.a(a3.c(), k) && e.a(a3.d(), n) && e.a(a3.e(), m)) {
            z = false;
        }
        if (a3 != null) {
            if (e.a(a4)) {
                a4 = a3.a();
            }
            if (e.a(b2)) {
                b2 = a3.b();
            }
            if (e.a(k)) {
                k = a3.c();
            }
            if (e.a(n)) {
                n = a3.d();
            }
            if (e.a(m)) {
                m = a3.e();
            }
        }
        if (z) {
            av avVar = new av(a4, b2, k, n, m);
            au.a(context, avVar);
        } else {
            Context context2 = context;
        }
        hashMap3.put("AD1", a4);
        hashMap3.put("AD2", b2);
        hashMap3.put("AD3", k.f(context));
        hashMap3.put("AD4", "");
        hashMap3.put("AD5", k.h(context));
        hashMap3.put("AD6", k.i(context));
        hashMap3.put("AD7", k.j(context));
        hashMap3.put("AD8", k);
        hashMap3.put("AD9", k.l(context));
        hashMap3.put("AD10", m);
        hashMap3.put("AD11", k.e());
        hashMap3.put("AD12", a2.f());
        hashMap3.put("AD13", k.g());
        hashMap3.put("AD14", k.h());
        hashMap3.put("AD15", k.i());
        hashMap3.put("AD16", k.j());
        hashMap3.put("AD17", "");
        hashMap3.put("AD18", n);
        hashMap3.put("AD20", k.k());
        hashMap3.put("AD22", "");
        hashMap3.put("AD23", k.l());
        hashMap3.put("AD24", e.f(k.g(context)));
        hashMap3.put("AD25", "");
        hashMap3.put("AD26", k.e(context));
        hashMap3.put("AD27", k.q());
        hashMap3.put("AD28", k.s());
        hashMap3.put("AD29", k.u());
        hashMap3.put("AD30", k.r());
        hashMap3.put("AD31", k.t());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(k.o() - (k.o() % 1000));
        hashMap3.put("AD32", sb3.toString());
        hashMap3.put("AD34", k.r(context));
        hashMap3.put("AD37", k.n());
        hashMap3.put("AD38", k.m());
        hashMap3.put("AD39", k.c(context));
        map3.putAll(hashMap3);
        this.a.putAll(c.a(context));
    }

    public final String a(Context context) {
        String str;
        b(context);
        Map<String, String> map = this.a;
        if (map == null) {
            str = null;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            ArrayList arrayList = new ArrayList(map.keySet());
            Collections.sort(arrayList);
            int i = 0;
            while (i < arrayList.size()) {
                String str2 = (String) arrayList.get(i);
                String str3 = map.get(str2);
                if (str3 == null) {
                    str3 = "";
                }
                StringBuilder sb = new StringBuilder();
                sb.append(i == 0 ? "" : "&");
                sb.append(str2);
                sb.append("=");
                sb.append(str3);
                stringBuffer.append(sb.toString());
                i++;
            }
            str = stringBuffer.toString();
        }
        return h.a(str);
    }

    public final Map<String, String> a(Context context, Map<String, String> map) {
        if (this.a == null) {
            b(context);
        }
        Map<String, String> map2 = this.a;
        HashMap hashMap = new HashMap();
        String a2 = e.a(map, "appName", "");
        String a3 = e.a(map, "appKeyClient", "");
        hashMap.put("AC1", "");
        hashMap.put("AC2", "");
        hashMap.put("AC3", "");
        hashMap.put("AC5", "");
        hashMap.put("AC6", "");
        hashMap.put("AC7", "");
        hashMap.put("AC8", a2);
        hashMap.put("AC9", a3);
        map2.putAll(hashMap);
        Map<String, String> map3 = this.a;
        HashMap hashMap2 = new HashMap();
        hashMap2.put("AE18", "");
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis() / 1000);
        hashMap2.put("AE22", sb.toString());
        map3.putAll(hashMap2);
        Map<String, String> map4 = this.a;
        k.a();
        HashMap hashMap3 = new HashMap();
        hashMap3.put("AD19", k.o(context));
        StringBuilder sb2 = new StringBuilder();
        sb2.append(k.o());
        hashMap3.put("AD32", sb2.toString());
        hashMap3.put("AD33", k.p());
        hashMap3.put("AD35", k.s(context));
        hashMap3.put("AD36", k.q(context));
        hashMap3.put("AD40", k.d(context));
        hashMap3.put("AD41", k.c());
        hashMap3.put("AD42", k.d());
        hashMap3.put("AD43", k.b());
        hashMap3.put("AL3", k.p(context));
        map4.putAll(hashMap3);
        this.a.putAll(c.a(map));
        return this.a;
    }
}
