package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.taobao.accs.common.Constants;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.thrift.meta_data.c;
import org.apache.thrift.meta_data.e;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.b;
import org.apache.thrift.protocol.d;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class aj implements Serializable, Cloneable, org.apache.thrift.a<aj, a> {
    private static final b A = new b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final b B = new b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final b C = new b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final b D = new b("appVersion", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final b E = new b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 6);
    private static final b F = new b("token", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final b G = new b("deviceId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    private static final b H = new b("aliasName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 9);
    private static final b I = new b(Constants.KEY_SDK_VERSION, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 10);
    private static final b J = new b("regId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 11);
    private static final b K = new b("pushSdkVersionName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 12);
    private static final b L = new b("pushSdkVersionCode", 8, 13);
    private static final b M = new b(Constants.KEY_APP_VERSION_CODE, 8, 14);
    private static final b N = new b("androidId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 15);
    private static final b O = new b(Constants.KEY_IMEI, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 16);
    private static final b P = new b("serial", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 17);
    private static final b Q = new b("imeiMd5", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 18);
    private static final b R = new b("spaceId", 8, 19);
    private static final b S = new b("reason", 8, 20);
    private static final b T = new b("connectionAttrs", 13, 100);
    private static final b U = new b("cleanOldRegInfo", 2, 101);
    private static final b V = new b("oldRegId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 102);
    public static final Map<a, org.apache.thrift.meta_data.b> x;
    private static final j y = new j("XmPushActionRegistration");
    private static final b z = new b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private BitSet W = new BitSet(4);
    public String a;
    public x b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public int m;
    public int n;
    public String o;
    public String p;
    public String q;
    public String r;
    public int s;
    public w t;
    public Map<String, String> u;
    public boolean v = false;
    public String w;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        APP_VERSION(5, "appVersion"),
        PACKAGE_NAME(6, "packageName"),
        TOKEN(7, "token"),
        DEVICE_ID(8, "deviceId"),
        ALIAS_NAME(9, "aliasName"),
        SDK_VERSION(10, Constants.KEY_SDK_VERSION),
        REG_ID(11, "regId"),
        PUSH_SDK_VERSION_NAME(12, "pushSdkVersionName"),
        PUSH_SDK_VERSION_CODE(13, "pushSdkVersionCode"),
        APP_VERSION_CODE(14, Constants.KEY_APP_VERSION_CODE),
        ANDROID_ID(15, "androidId"),
        IMEI(16, Constants.KEY_IMEI),
        SERIAL(17, "serial"),
        IMEI_MD5(18, "imeiMd5"),
        SPACE_ID(19, "spaceId"),
        REASON(20, "reason"),
        CONNECTION_ATTRS(100, "connectionAttrs"),
        CLEAN_OLD_REG_INFO(101, "cleanOldRegInfo"),
        OLD_REG_ID(102, "oldRegId");
        
        private static final Map<String, a> x = null;
        private final short y;
        private final String z;

        static {
            x = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                x.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.y = s;
            this.z = str;
        }

        public final String a() {
            return this.z;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new org.apache.thrift.meta_data.b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new org.apache.thrift.meta_data.b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new org.apache.thrift.meta_data.b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new org.apache.thrift.meta_data.b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_VERSION, new org.apache.thrift.meta_data.b("appVersion", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new org.apache.thrift.meta_data.b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TOKEN, new org.apache.thrift.meta_data.b("token", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.DEVICE_ID, new org.apache.thrift.meta_data.b("deviceId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.ALIAS_NAME, new org.apache.thrift.meta_data.b("aliasName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.SDK_VERSION, new org.apache.thrift.meta_data.b(Constants.KEY_SDK_VERSION, 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REG_ID, new org.apache.thrift.meta_data.b("regId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PUSH_SDK_VERSION_NAME, new org.apache.thrift.meta_data.b("pushSdkVersionName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PUSH_SDK_VERSION_CODE, new org.apache.thrift.meta_data.b("pushSdkVersionCode", 2, new c(8)));
        enumMap.put(a.APP_VERSION_CODE, new org.apache.thrift.meta_data.b(Constants.KEY_APP_VERSION_CODE, 2, new c(8)));
        enumMap.put(a.ANDROID_ID, new org.apache.thrift.meta_data.b("androidId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.IMEI, new org.apache.thrift.meta_data.b(Constants.KEY_IMEI, 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.SERIAL, new org.apache.thrift.meta_data.b("serial", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.IMEI_MD5, new org.apache.thrift.meta_data.b("imeiMd5", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.SPACE_ID, new org.apache.thrift.meta_data.b("spaceId", 2, new c(8)));
        enumMap.put(a.REASON, new org.apache.thrift.meta_data.b("reason", 2, new org.apache.thrift.meta_data.a(16, w.class)));
        enumMap.put(a.CONNECTION_ATTRS, new org.apache.thrift.meta_data.b("connectionAttrs", 2, new e(13, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES), new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.CLEAN_OLD_REG_INFO, new org.apache.thrift.meta_data.b("cleanOldRegInfo", 2, new c(2)));
        enumMap.put(a.OLD_REG_ID, new org.apache.thrift.meta_data.b("oldRegId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        x = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(aj.class, x);
    }

    public void A() {
        if (this.c == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.d == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'appId' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        } else if (this.g == null) {
            StringBuilder sb3 = new StringBuilder("Required field 'token' was not present! Struct: ");
            sb3.append(toString());
            throw new f(sb3.toString());
        }
    }

    public aj a(int i2) {
        this.m = i2;
        a(true);
        return this;
    }

    public aj a(w wVar) {
        this.t = wVar;
        return this;
    }

    public aj a(String str) {
        this.c = str;
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r6) {
        /*
            r5 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r6.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0162
            short r1 = r0.c
            r2 = 8
            r3 = 1
            r4 = 11
            switch(r1) {
                case 1: goto L_0x0156;
                case 2: goto L_0x0142;
                case 3: goto L_0x0136;
                case 4: goto L_0x012a;
                case 5: goto L_0x011e;
                case 6: goto L_0x0112;
                case 7: goto L_0x0106;
                case 8: goto L_0x00fa;
                case 9: goto L_0x00ee;
                case 10: goto L_0x00e2;
                case 11: goto L_0x00d6;
                case 12: goto L_0x00ca;
                case 13: goto L_0x00bb;
                case 14: goto L_0x00ac;
                case 15: goto L_0x00a0;
                case 16: goto L_0x0094;
                case 17: goto L_0x0088;
                case 18: goto L_0x007c;
                case 19: goto L_0x006e;
                case 20: goto L_0x005f;
                default: goto L_0x0012;
            }
        L_0x0012:
            r2 = 2
            switch(r1) {
                case 100: goto L_0x0035;
                case 101: goto L_0x0027;
                case 102: goto L_0x001c;
                default: goto L_0x0016;
            }
        L_0x0016:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r6, r0)
            goto L_0x0000
        L_0x001c:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.w = r0
            goto L_0x0000
        L_0x0027:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0016
            boolean r0 = r6.f()
            r5.v = r0
            r5.d(r3)
            goto L_0x0000
        L_0x0035:
            byte r1 = r0.b
            r3 = 13
            if (r1 != r3) goto L_0x0016
            org.apache.thrift.protocol.d r0 = r6.c()
            java.util.HashMap r1 = new java.util.HashMap
            int r3 = r0.c
            int r3 = r3 * 2
            r1.<init>(r3)
            r5.u = r1
            r1 = 0
        L_0x004b:
            int r2 = r0.c
            if (r1 >= r2) goto L_0x0000
            java.lang.String r2 = r6.l()
            java.lang.String r3 = r6.l()
            java.util.Map<java.lang.String, java.lang.String> r4 = r5.u
            r4.put(r2, r3)
            int r1 = r1 + 1
            goto L_0x004b
        L_0x005f:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0016
            int r0 = r6.i()
            com.xiaomi.xmpush.thrift.w r0 = com.xiaomi.xmpush.thrift.w.a(r0)
            r5.t = r0
            goto L_0x0000
        L_0x006e:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0016
            int r0 = r6.i()
            r5.s = r0
            r5.c(r3)
            goto L_0x0000
        L_0x007c:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.r = r0
            goto L_0x0000
        L_0x0088:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.q = r0
            goto L_0x0000
        L_0x0094:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.p = r0
            goto L_0x0000
        L_0x00a0:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.o = r0
            goto L_0x0000
        L_0x00ac:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0016
            int r0 = r6.i()
            r5.n = r0
            r5.b(r3)
            goto L_0x0000
        L_0x00bb:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0016
            int r0 = r6.i()
            r5.m = r0
            r5.a(r3)
            goto L_0x0000
        L_0x00ca:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.l = r0
            goto L_0x0000
        L_0x00d6:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.k = r0
            goto L_0x0000
        L_0x00e2:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.j = r0
            goto L_0x0000
        L_0x00ee:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.i = r0
            goto L_0x0000
        L_0x00fa:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.h = r0
            goto L_0x0000
        L_0x0106:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.g = r0
            goto L_0x0000
        L_0x0112:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.f = r0
            goto L_0x0000
        L_0x011e:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.e = r0
            goto L_0x0000
        L_0x012a:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.d = r0
            goto L_0x0000
        L_0x0136:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.c = r0
            goto L_0x0000
        L_0x0142:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x0016
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r5.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r5.b
            r0.a(r6)
            goto L_0x0000
        L_0x0156:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            java.lang.String r0 = r6.l()
            r5.a = r0
            goto L_0x0000
        L_0x0162:
            r5.A()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.aj.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z2) {
        this.W.set(0, z2);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(aj ajVar) {
        if (ajVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = ajVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(ajVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = ajVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(ajVar.b))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = ajVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.c.equals(ajVar.c))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = ajVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.d.equals(ajVar.d))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = ajVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.e.equals(ajVar.e))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = ajVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.f.equals(ajVar.f))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = ajVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.g.equals(ajVar.g))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = ajVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.h.equals(ajVar.h))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = ajVar.l();
        if ((l2 || l3) && (!l2 || !l3 || !this.i.equals(ajVar.i))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = ajVar.m();
        if ((m2 || m3) && (!m2 || !m3 || !this.j.equals(ajVar.j))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = ajVar.n();
        if ((n2 || n3) && (!n2 || !n3 || !this.k.equals(ajVar.k))) {
            return false;
        }
        boolean o2 = o();
        boolean o3 = ajVar.o();
        if ((o2 || o3) && (!o2 || !o3 || !this.l.equals(ajVar.l))) {
            return false;
        }
        boolean p2 = p();
        boolean p3 = ajVar.p();
        if ((p2 || p3) && (!p2 || !p3 || this.m != ajVar.m)) {
            return false;
        }
        boolean q2 = q();
        boolean q3 = ajVar.q();
        if ((q2 || q3) && (!q2 || !q3 || this.n != ajVar.n)) {
            return false;
        }
        boolean r2 = r();
        boolean r3 = ajVar.r();
        if ((r2 || r3) && (!r2 || !r3 || !this.o.equals(ajVar.o))) {
            return false;
        }
        boolean s2 = s();
        boolean s3 = ajVar.s();
        if ((s2 || s3) && (!s2 || !s3 || !this.p.equals(ajVar.p))) {
            return false;
        }
        boolean t2 = t();
        boolean t3 = ajVar.t();
        if ((t2 || t3) && (!t2 || !t3 || !this.q.equals(ajVar.q))) {
            return false;
        }
        boolean u2 = u();
        boolean u3 = ajVar.u();
        if ((u2 || u3) && (!u2 || !u3 || !this.r.equals(ajVar.r))) {
            return false;
        }
        boolean v2 = v();
        boolean v3 = ajVar.v();
        if ((v2 || v3) && (!v2 || !v3 || this.s != ajVar.s)) {
            return false;
        }
        boolean w2 = w();
        boolean w3 = ajVar.w();
        if ((w2 || w3) && (!w2 || !w3 || !this.t.equals(ajVar.t))) {
            return false;
        }
        boolean x2 = x();
        boolean x3 = ajVar.x();
        if ((x2 || x3) && (!x2 || !x3 || !this.u.equals(ajVar.u))) {
            return false;
        }
        boolean y2 = y();
        boolean y3 = ajVar.y();
        if ((y2 || y3) && (!y2 || !y3 || this.v != ajVar.v)) {
            return false;
        }
        boolean z2 = z();
        boolean z3 = ajVar.z();
        return (!z2 && !z3) || (z2 && z3 && this.w.equals(ajVar.w));
    }

    /* renamed from: b */
    public int compareTo(aj ajVar) {
        if (!getClass().equals(ajVar.getClass())) {
            return getClass().getName().compareTo(ajVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(ajVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, ajVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ajVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) ajVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ajVar.d()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (d()) {
            int a4 = org.apache.thrift.b.a(this.c, ajVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ajVar.f()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (f()) {
            int a5 = org.apache.thrift.b.a(this.d, ajVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ajVar.g()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (g()) {
            int a6 = org.apache.thrift.b.a(this.e, ajVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(ajVar.h()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (h()) {
            int a7 = org.apache.thrift.b.a(this.f, ajVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(ajVar.j()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (j()) {
            int a8 = org.apache.thrift.b.a(this.g, ajVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(ajVar.k()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (k()) {
            int a9 = org.apache.thrift.b.a(this.h, ajVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(ajVar.l()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (l()) {
            int a10 = org.apache.thrift.b.a(this.i, ajVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(ajVar.m()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (m()) {
            int a11 = org.apache.thrift.b.a(this.j, ajVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        int compareTo11 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(ajVar.n()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (n()) {
            int a12 = org.apache.thrift.b.a(this.k, ajVar.k);
            if (a12 != 0) {
                return a12;
            }
        }
        int compareTo12 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(ajVar.o()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (o()) {
            int a13 = org.apache.thrift.b.a(this.l, ajVar.l);
            if (a13 != 0) {
                return a13;
            }
        }
        int compareTo13 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(ajVar.p()));
        if (compareTo13 != 0) {
            return compareTo13;
        }
        if (p()) {
            int a14 = org.apache.thrift.b.a(this.m, ajVar.m);
            if (a14 != 0) {
                return a14;
            }
        }
        int compareTo14 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(ajVar.q()));
        if (compareTo14 != 0) {
            return compareTo14;
        }
        if (q()) {
            int a15 = org.apache.thrift.b.a(this.n, ajVar.n);
            if (a15 != 0) {
                return a15;
            }
        }
        int compareTo15 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(ajVar.r()));
        if (compareTo15 != 0) {
            return compareTo15;
        }
        if (r()) {
            int a16 = org.apache.thrift.b.a(this.o, ajVar.o);
            if (a16 != 0) {
                return a16;
            }
        }
        int compareTo16 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(ajVar.s()));
        if (compareTo16 != 0) {
            return compareTo16;
        }
        if (s()) {
            int a17 = org.apache.thrift.b.a(this.p, ajVar.p);
            if (a17 != 0) {
                return a17;
            }
        }
        int compareTo17 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(ajVar.t()));
        if (compareTo17 != 0) {
            return compareTo17;
        }
        if (t()) {
            int a18 = org.apache.thrift.b.a(this.q, ajVar.q);
            if (a18 != 0) {
                return a18;
            }
        }
        int compareTo18 = Boolean.valueOf(u()).compareTo(Boolean.valueOf(ajVar.u()));
        if (compareTo18 != 0) {
            return compareTo18;
        }
        if (u()) {
            int a19 = org.apache.thrift.b.a(this.r, ajVar.r);
            if (a19 != 0) {
                return a19;
            }
        }
        int compareTo19 = Boolean.valueOf(v()).compareTo(Boolean.valueOf(ajVar.v()));
        if (compareTo19 != 0) {
            return compareTo19;
        }
        if (v()) {
            int a20 = org.apache.thrift.b.a(this.s, ajVar.s);
            if (a20 != 0) {
                return a20;
            }
        }
        int compareTo20 = Boolean.valueOf(w()).compareTo(Boolean.valueOf(ajVar.w()));
        if (compareTo20 != 0) {
            return compareTo20;
        }
        if (w()) {
            int a21 = org.apache.thrift.b.a((Comparable) this.t, (Comparable) ajVar.t);
            if (a21 != 0) {
                return a21;
            }
        }
        int compareTo21 = Boolean.valueOf(x()).compareTo(Boolean.valueOf(ajVar.x()));
        if (compareTo21 != 0) {
            return compareTo21;
        }
        if (x()) {
            int a22 = org.apache.thrift.b.a((Map) this.u, (Map) ajVar.u);
            if (a22 != 0) {
                return a22;
            }
        }
        int compareTo22 = Boolean.valueOf(y()).compareTo(Boolean.valueOf(ajVar.y()));
        if (compareTo22 != 0) {
            return compareTo22;
        }
        if (y()) {
            int a23 = org.apache.thrift.b.a(this.v, ajVar.v);
            if (a23 != 0) {
                return a23;
            }
        }
        int compareTo23 = Boolean.valueOf(z()).compareTo(Boolean.valueOf(ajVar.z()));
        if (compareTo23 != 0) {
            return compareTo23;
        }
        if (z()) {
            int a24 = org.apache.thrift.b.a(this.w, ajVar.w);
            if (a24 != 0) {
                return a24;
            }
        }
        return 0;
    }

    public aj b(int i2) {
        this.n = i2;
        b(true);
        return this;
    }

    public aj b(String str) {
        this.d = str;
        return this;
    }

    public void b(org.apache.thrift.protocol.e eVar) {
        A();
        if (this.a != null && a()) {
            eVar.a(z);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(A);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(B);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(C);
            eVar.a(this.d);
        }
        if (this.e != null && g()) {
            eVar.a(D);
            eVar.a(this.e);
        }
        if (this.f != null && h()) {
            eVar.a(E);
            eVar.a(this.f);
        }
        if (this.g != null) {
            eVar.a(F);
            eVar.a(this.g);
        }
        if (this.h != null && k()) {
            eVar.a(G);
            eVar.a(this.h);
        }
        if (this.i != null && l()) {
            eVar.a(H);
            eVar.a(this.i);
        }
        if (this.j != null && m()) {
            eVar.a(I);
            eVar.a(this.j);
        }
        if (this.k != null && n()) {
            eVar.a(J);
            eVar.a(this.k);
        }
        if (this.l != null && o()) {
            eVar.a(K);
            eVar.a(this.l);
        }
        if (p()) {
            eVar.a(L);
            eVar.a(this.m);
        }
        if (q()) {
            eVar.a(M);
            eVar.a(this.n);
        }
        if (this.o != null && r()) {
            eVar.a(N);
            eVar.a(this.o);
        }
        if (this.p != null && s()) {
            eVar.a(O);
            eVar.a(this.p);
        }
        if (this.q != null && t()) {
            eVar.a(P);
            eVar.a(this.q);
        }
        if (this.r != null && u()) {
            eVar.a(Q);
            eVar.a(this.r);
        }
        if (v()) {
            eVar.a(R);
            eVar.a(this.s);
        }
        if (this.t != null && w()) {
            eVar.a(S);
            eVar.a(this.t.a());
        }
        if (this.u != null && x()) {
            eVar.a(T);
            eVar.a(new d(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.u.size()));
            for (Entry next : this.u.entrySet()) {
                eVar.a((String) next.getKey());
                eVar.a((String) next.getValue());
            }
        }
        if (y()) {
            eVar.a(U);
            eVar.a(this.v);
        }
        if (this.w != null && z()) {
            eVar.a(V);
            eVar.a(this.w);
        }
        eVar.a();
    }

    public void b(boolean z2) {
        this.W.set(1, z2);
    }

    public boolean b() {
        return this.b != null;
    }

    public aj c(int i2) {
        this.s = i2;
        c(true);
        return this;
    }

    public aj c(String str) {
        this.e = str;
        return this;
    }

    public String c() {
        return this.c;
    }

    public void c(boolean z2) {
        this.W.set(2, z2);
    }

    public aj d(String str) {
        this.f = str;
        return this;
    }

    public void d(boolean z2) {
        this.W.set(3, z2);
    }

    public boolean d() {
        return this.c != null;
    }

    public aj e(String str) {
        this.g = str;
        return this;
    }

    public String e() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof aj)) {
            return a((aj) obj);
        }
        return false;
    }

    public aj f(String str) {
        this.h = str;
        return this;
    }

    public boolean f() {
        return this.d != null;
    }

    public aj g(String str) {
        this.l = str;
        return this;
    }

    public boolean g() {
        return this.e != null;
    }

    public aj h(String str) {
        this.o = str;
        return this;
    }

    public boolean h() {
        return this.f != null;
    }

    public int hashCode() {
        return 0;
    }

    public aj i(String str) {
        this.p = str;
        return this;
    }

    public String i() {
        return this.g;
    }

    public aj j(String str) {
        this.q = str;
        return this;
    }

    public boolean j() {
        return this.g != null;
    }

    public aj k(String str) {
        this.r = str;
        return this;
    }

    public boolean k() {
        return this.h != null;
    }

    public boolean l() {
        return this.i != null;
    }

    public boolean m() {
        return this.j != null;
    }

    public boolean n() {
        return this.k != null;
    }

    public boolean o() {
        return this.l != null;
    }

    public boolean p() {
        return this.W.get(0);
    }

    public boolean q() {
        return this.W.get(1);
    }

    public boolean r() {
        return this.o != null;
    }

    public boolean s() {
        return this.p != null;
    }

    public boolean t() {
        return this.q != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionRegistration(");
        if (a()) {
            sb.append("debug:");
            sb.append(this.a == null ? "null" : this.a);
            z2 = false;
        } else {
            z2 = true;
        }
        if (b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
            z2 = false;
        }
        if (!z2) {
            sb.append(", ");
        }
        sb.append("id:");
        sb.append(this.c == null ? "null" : this.c);
        sb.append(", ");
        sb.append("appId:");
        sb.append(this.d == null ? "null" : this.d);
        if (g()) {
            sb.append(", ");
            sb.append("appVersion:");
            sb.append(this.e == null ? "null" : this.e);
        }
        if (h()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.f == null ? "null" : this.f);
        }
        sb.append(", ");
        sb.append("token:");
        sb.append(this.g == null ? "null" : this.g);
        if (k()) {
            sb.append(", ");
            sb.append("deviceId:");
            sb.append(this.h == null ? "null" : this.h);
        }
        if (l()) {
            sb.append(", ");
            sb.append("aliasName:");
            sb.append(this.i == null ? "null" : this.i);
        }
        if (m()) {
            sb.append(", ");
            sb.append("sdkVersion:");
            sb.append(this.j == null ? "null" : this.j);
        }
        if (n()) {
            sb.append(", ");
            sb.append("regId:");
            sb.append(this.k == null ? "null" : this.k);
        }
        if (o()) {
            sb.append(", ");
            sb.append("pushSdkVersionName:");
            sb.append(this.l == null ? "null" : this.l);
        }
        if (p()) {
            sb.append(", ");
            sb.append("pushSdkVersionCode:");
            sb.append(this.m);
        }
        if (q()) {
            sb.append(", ");
            sb.append("appVersionCode:");
            sb.append(this.n);
        }
        if (r()) {
            sb.append(", ");
            sb.append("androidId:");
            sb.append(this.o == null ? "null" : this.o);
        }
        if (s()) {
            sb.append(", ");
            sb.append("imei:");
            sb.append(this.p == null ? "null" : this.p);
        }
        if (t()) {
            sb.append(", ");
            sb.append("serial:");
            sb.append(this.q == null ? "null" : this.q);
        }
        if (u()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            sb.append(this.r == null ? "null" : this.r);
        }
        if (v()) {
            sb.append(", ");
            sb.append("spaceId:");
            sb.append(this.s);
        }
        if (w()) {
            sb.append(", ");
            sb.append("reason:");
            if (this.t == null) {
                sb.append("null");
            } else {
                sb.append(this.t);
            }
        }
        if (x()) {
            sb.append(", ");
            sb.append("connectionAttrs:");
            if (this.u == null) {
                sb.append("null");
            } else {
                sb.append(this.u);
            }
        }
        if (y()) {
            sb.append(", ");
            sb.append("cleanOldRegInfo:");
            sb.append(this.v);
        }
        if (z()) {
            sb.append(", ");
            sb.append("oldRegId:");
            sb.append(this.w == null ? "null" : this.w);
        }
        sb.append(")");
        return sb.toString();
    }

    public boolean u() {
        return this.r != null;
    }

    public boolean v() {
        return this.W.get(2);
    }

    public boolean w() {
        return this.t != null;
    }

    public boolean x() {
        return this.u != null;
    }

    public boolean y() {
        return this.W.get(3);
    }

    public boolean z() {
        return this.w != null;
    }
}
