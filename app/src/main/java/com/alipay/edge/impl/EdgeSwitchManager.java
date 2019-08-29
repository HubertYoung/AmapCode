package com.alipay.edge.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class EdgeSwitchManager {
    private static EdgeSwitchManager c = null;
    private static int d = 0;
    private static String e = "";
    private SharedPreferences a;
    private Context b = null;

    private EdgeSwitchManager(Context context) {
        this.b = context;
    }

    public static EdgeSwitchManager a(Context context) {
        if (c == null) {
            synchronized (EdgeSwitchManager.class) {
                try {
                    if (c == null) {
                        if (context == null) {
                            return null;
                        }
                        EdgeSwitchManager edgeSwitchManager = new EdgeSwitchManager(context.getApplicationContext());
                        c = edgeSwitchManager;
                        edgeSwitchManager.a = edgeSwitchManager.b.getSharedPreferences("sdk_monitor", 0);
                    }
                }
            }
        }
        return c;
    }

    public final synchronized void a() {
        if (d == 0 && "".equals(e)) {
            e = String.valueOf(System.currentTimeMillis() / 1000);
        }
        d++;
    }

    public static String b() {
        return String.valueOf(d);
    }

    public static String c() {
        return e;
    }

    public final synchronized void d() {
        try {
            this.a.edit().clear().commit();
        } catch (Throwable unused) {
        }
    }

    public final synchronized void a(boolean z) {
        a("edge_crash_1.1", z);
    }

    public final synchronized boolean e() {
        return b("edge_crash_1.1", false).booleanValue();
    }

    public final synchronized void b(boolean z) {
        a("edge_switch", z);
    }

    public final synchronized boolean f() {
        return b("edge_switch", true).booleanValue();
    }

    public final synchronized void c(boolean z) {
        a("edge_switch_report", z);
    }

    public final synchronized boolean g() {
        return b("edge_switch_report", false).booleanValue();
    }

    public final synchronized boolean h() {
        return b("edge_clear_cache", false).booleanValue();
    }

    public final synchronized boolean i() {
        return b("edge_self_safe", false).booleanValue();
    }

    private void a(String str, boolean z) {
        if (this.a != null) {
            Editor edit = this.a.edit();
            if (edit != null) {
                edit.putBoolean(str, z);
                edit.commit();
            }
        }
    }

    private Boolean b(String str, boolean z) {
        if (this.a == null) {
            return Boolean.valueOf(z);
        }
        return Boolean.valueOf(this.a.getBoolean(str, z));
    }
}
