package com.uc.webview.export.internal.uc.startup;

import android.os.SystemClock;
import android.util.Pair;
import com.uc.webview.export.annotations.Reflection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: ProGuard */
public class StartupStats {
    private static ConcurrentHashMap<Integer, Pair<Long, Long>> a = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, Long> b = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, String> c = new ConcurrentHashMap<>();
    private static Runnable d;

    public static void a() {
        a(1);
        b(2, SystemClock.elapsedRealtime());
    }

    @Reflection
    public static Object invoke(int i, Object... objArr) {
        switch (i) {
            case 100:
                a(objArr[0].intValue());
                break;
            case 101:
                b(objArr[0].intValue(), objArr[1].longValue());
                break;
            case 102:
                a(objArr[0].intValue(), objArr[1].longValue());
                break;
            case 103:
                a(objArr[0].intValue(), objArr[1]);
                break;
            case 104:
                d = objArr[0];
                break;
            case 105:
                HashMap hashMap = new HashMap();
                for (Entry next : a.entrySet()) {
                    hashMap.put(next.getKey(), ((Pair) next.getValue()).first);
                }
                HashMap hashMap2 = new HashMap();
                for (Entry next2 : b.entrySet()) {
                    hashMap2.put(next2.getKey(), String.valueOf(next2.getValue()));
                }
                for (Entry next3 : c.entrySet()) {
                    hashMap2.put(next3.getKey(), next3.getValue());
                }
                return new Pair(hashMap, hashMap2);
        }
        return null;
    }

    public static void a(int i) {
        if (!a.containsKey(Integer.valueOf(i))) {
            a.put(Integer.valueOf(i), new Pair(Long.valueOf(System.currentTimeMillis()), Long.valueOf(SystemClock.currentThreadTimeMillis())));
        }
    }

    private static void b(int i, long j) {
        if (!a.containsKey(Integer.valueOf(i))) {
            a.put(Integer.valueOf(i), new Pair(Long.valueOf(j), Long.valueOf(0)));
            if (i == 111 && d != null) {
                d.run();
            }
        }
    }

    public static void a(int i, long j) {
        if (!b.containsKey(Integer.valueOf(i))) {
            b.put(Integer.valueOf(i), Long.valueOf(j));
        }
    }

    public static void a(int i, String str) {
        if (!c.containsKey(Integer.valueOf(i))) {
            c.put(Integer.valueOf(i), str);
        }
    }
}
