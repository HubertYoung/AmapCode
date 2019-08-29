package com.xiaomi.smack;

import com.xiaomi.channel.commonutils.misc.a;
import com.xiaomi.channel.commonutils.misc.d;
import java.util.Map;

public class b implements Cloneable {
    public static String b = "wcc-ml-test10.bj";
    public static final String c = d.b;
    public static String d;
    private String a;
    private String e;
    private int f;
    private boolean g = a.a;
    private boolean h = true;
    private String i;
    private e j;

    public b(Map<String, Integer> map, int i2, String str, e eVar) {
        a(map, i2, str, eVar);
    }

    public static final void a(String str) {
        d = str;
    }

    private void a(Map<String, Integer> map, int i2, String str, e eVar) {
        this.e = b();
        this.f = i2;
        this.a = str;
        this.j = eVar;
    }

    public static final String b() {
        return d != null ? d : a.a() ? "sandbox.xmpush.xiaomi.com" : a.b() ? c : "app.chat.xiaomi.net";
    }

    public void a(boolean z) {
        this.g = z;
    }

    public byte[] a() {
        return null;
    }

    public void b(String str) {
        this.i = str;
    }

    public String c() {
        return this.i;
    }

    public void c(String str) {
        this.e = str;
    }

    public int d() {
        return this.f;
    }

    public String e() {
        return this.e;
    }

    public boolean f() {
        return this.g;
    }
}
