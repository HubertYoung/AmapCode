package defpackage;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import mtopsdk.common.util.TBSdkLog;

/* renamed from: fff reason: default package */
/* compiled from: SwitchConfig */
public final class fff {
    public static final fda a = a.a;
    public static final Map<String, String> d = new ConcurrentHashMap(8);
    public static final HashSet<String> e = new HashSet<>(8);
    private static final fff f = new fff();
    private static final fdc g = fdc.a();
    private static fcv h;
    private static volatile Map<String, String> i = new ConcurrentHashMap(8);
    public volatile Set<String> b = null;
    public volatile Set<String> c = null;

    static {
        d.put("NETWORK_ERROR_MAPPING", "网络竟然崩溃了");
        d.put("FLOW_LIMIT_ERROR_MAPPING", "前方拥挤，亲稍等再试试");
        d.put("SERVICE_ERROR_MAPPING", "服务竟然出错了");
        e.add("FAIL_SYS_ACCESS_TOKEN_EXPIRED");
        e.add("FAIL_SYS_ILLEGAL_ACCESS_TOKEN");
    }

    private fff() {
    }

    public static fff a() {
        return f;
    }

    public static boolean b() {
        return a.a && g.a;
    }

    public static boolean c() {
        return a.b && g.b;
    }

    public static boolean d() {
        return a.c && g.d;
    }

    public static boolean e() {
        return a.e && g.f;
    }

    public static long f() {
        return g.j;
    }

    public static long g() {
        return g.p;
    }

    public static long h() {
        return g.c;
    }

    public static boolean i() {
        return g.g;
    }

    public static boolean j() {
        return a.f && g.h;
    }

    public static long a(String str) {
        long j = 0;
        if (fdd.b(str)) {
            return 0;
        }
        String str2 = i.get(str);
        if (fdd.b(str2)) {
            return 0;
        }
        try {
            j = Long.parseLong(str2);
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("[getIndividualApiLockInterval]parse individual apiLock interval error.apiKey=");
            sb.append(str);
            sb.append(" ---");
            sb.append(e2.toString());
            TBSdkLog.d("mtopsdk.SwitchConfig", sb.toString());
        }
        return j;
    }
}
