package com.uc.webview.export.internal.uc.startup;

import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.sdk.util.h;
import com.uc.webview.export.annotations.Reflection;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ProGuard */
public class StartupTrace {
    private static volatile StartupTrace a = null;
    private static ConcurrentHashMap b = new ConcurrentHashMap();
    private static boolean c = false;
    private static HandlerThread d = null;
    private static Handler e = null;
    private static boolean f = false;
    private static a g;

    /* compiled from: ProGuard */
    class a {
        public int a;
        public String b;
        public long c;
        public long d;
        public String e;

        public final String toString() {
            StringBuilder sb = new StringBuilder("{name:");
            sb.append(this.b);
            sb.append(",t:");
            sb.append(this.a);
            sb.append(",ts:");
            sb.append(this.c);
            sb.append(",tid:");
            sb.append(this.d);
            sb.append(",tname:");
            sb.append(this.e);
            sb.append(h.d);
            return sb.toString();
        }
    }

    public static void a() {
    }

    @Reflection
    public static void traceEvent(String str) {
    }

    @Reflection
    public static void traceEventBegin(String str, String str2) {
    }

    @Reflection
    public static void traceEventEnd(String str) {
    }
}
