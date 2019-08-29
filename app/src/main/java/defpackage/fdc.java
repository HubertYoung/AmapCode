package defpackage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* renamed from: fdc reason: default package */
/* compiled from: RemoteConfig */
public final class fdc {
    private static Map<String, Integer> w;
    public boolean a;
    public boolean b;
    public long c;
    public boolean d;
    @Deprecated
    public boolean e;
    public boolean f;
    public boolean g;
    public boolean h;
    public boolean i;
    public long j;
    public String k;
    public String l;
    public String m;
    public String n;
    public String o;
    public long p;
    public int q;
    public int r;
    public final Set<String> s;
    public final Set<String> t;
    public boolean u;
    private Map<String, String> v;

    /* renamed from: fdc$a */
    /* compiled from: RemoteConfig */
    static class a {
        /* access modifiers changed from: private */
        public static fdc a = new fdc(0);
    }

    /* synthetic */ fdc(byte b2) {
        this();
    }

    public static fdc a() {
        return a.a;
    }

    private fdc() {
        this.v = null;
        this.a = true;
        this.b = false;
        this.c = 24;
        this.d = true;
        this.e = true;
        this.f = true;
        this.g = true;
        this.h = false;
        this.i = false;
        this.j = 10;
        this.k = "";
        this.l = "";
        this.m = "";
        this.n = "";
        this.o = "";
        this.p = 20;
        this.q = -1;
        this.r = -1;
        this.s = new HashSet();
        this.t = new HashSet();
        this.u = true;
    }

    static {
        HashMap hashMap = new HashMap();
        w = hashMap;
        hashMap.put("2G", Integer.valueOf(32768));
        w.put("3G", Integer.valueOf(65536));
        w.put("4G", Integer.valueOf(524288));
        w.put("WIFI", Integer.valueOf(524288));
        w.put("UNKONWN", Integer.valueOf(131072));
        w.put("NET_NO", Integer.valueOf(131072));
    }
}
