package defpackage;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.statist.RequestStatistic;
import anetwork.channel.http.NetworkSdkSetting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: ds reason: default package */
/* compiled from: NetworkConfigCenter */
public final class ds {
    private static volatile boolean a = true;
    private static volatile boolean b = true;
    private static volatile boolean c = true;
    private static volatile int d = 5;
    private static volatile boolean e = true;
    private static volatile boolean f = true;
    private static volatile boolean g = false;
    private static volatile long h = 0;
    private static volatile boolean i = false;
    private static volatile ConcurrentHashMap<String, List<String>> j = null;
    private static final List<String> k = new ArrayList();
    private static volatile int l = 10000;
    private static volatile boolean m = true;
    private static volatile boolean n = false;
    private static volatile int o = 60000;
    private static volatile CopyOnWriteArrayList<String> p = null;
    private static volatile ConcurrentHashMap<String, List<String>> q = null;
    private static volatile boolean r = true;
    private static volatile dr s;

    public static void a() {
        h = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext()).getLong("Cache.Flag", 0);
    }

    public static void b() {
        a = false;
    }

    public static boolean c() {
        return a;
    }

    public static boolean d() {
        return b;
    }

    public static int e() {
        return d;
    }

    public static boolean f() {
        return c;
    }

    public static void a(dr drVar) {
        if (s != null) {
            s.b();
        }
        drVar.a();
        s = drVar;
    }

    public static boolean g() {
        return e;
    }

    public static boolean h() {
        return e && g;
    }

    public static boolean i() {
        return f;
    }

    public static boolean a(cs csVar) {
        if (csVar == null) {
            return false;
        }
        ConcurrentHashMap<String, List<String>> concurrentHashMap = j;
        if (concurrentHashMap == null) {
            return false;
        }
        List<String> list = concurrentHashMap.get(csVar.b);
        if (list == null) {
            return false;
        }
        if (list == k) {
            return true;
        }
        for (String startsWith : list) {
            if (csVar.c.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }

    public static int j() {
        return l;
    }

    public static boolean k() {
        return i;
    }

    public static boolean l() {
        return m;
    }

    public static boolean m() {
        return n;
    }

    public static int n() {
        return o;
    }

    public static boolean a(RequestStatistic requestStatistic) {
        if (requestStatistic == null) {
            return false;
        }
        CopyOnWriteArrayList<String> copyOnWriteArrayList = p;
        if (copyOnWriteArrayList == null || TextUtils.isEmpty(requestStatistic.host)) {
            return false;
        }
        Iterator<String> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            if (requestStatistic.host.equalsIgnoreCase(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean b(cs csVar) {
        if (csVar == null) {
            return false;
        }
        ConcurrentHashMap<String, List<String>> concurrentHashMap = q;
        if (concurrentHashMap == null) {
            return false;
        }
        List<String> list = concurrentHashMap.get(csVar.b);
        if (list == null) {
            return false;
        }
        if (list == k) {
            return true;
        }
        for (String startsWith : list) {
            if (csVar.c.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }

    public static boolean o() {
        return r;
    }
}
