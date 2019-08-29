package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
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

public class z implements Serializable, Cloneable, org.apache.thrift.a<z, a> {
    private static final b A = new b("messageTs", 10, 5);
    private static final b B = new b("topic", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 6);
    private static final b C = new b("aliasName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final b D = new b("request", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 8);
    private static final b E = new b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 9);
    private static final b F = new b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 10);
    private static final b G = new b("isOnline", 2, 11);
    private static final b H = new b("regId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 12);
    private static final b I = new b("callbackUrl", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 13);
    private static final b J = new b("userAccount", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 14);
    private static final b K = new b("deviceStatus", 6, 15);
    private static final b L = new b("geoMsgStatus", 6, 16);
    private static final b M = new b("imeiMd5", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 20);
    private static final b N = new b("deviceId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 21);
    private static final b O = new b("passThrough", 8, 22);
    private static final b P = new b("extra", 13, 23);
    public static final Map<a, org.apache.thrift.meta_data.b> u;
    private static final j v = new j("XmPushActionAckMessage");
    private static final b w = new b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final b x = new b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final b y = new b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final b z = new b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private BitSet Q = new BitSet(5);
    public String a;
    public x b;
    public String c;
    public String d;
    public long e;
    public String f;
    public String g;
    public an h;
    public String i;
    public String j;
    public boolean k = false;
    public String l;
    public String m;
    public String n;
    public short o;
    public short p;
    public String q;
    public String r;
    public int s;
    public Map<String, String> t;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        MESSAGE_TS(5, "messageTs"),
        TOPIC(6, "topic"),
        ALIAS_NAME(7, "aliasName"),
        REQUEST(8, "request"),
        PACKAGE_NAME(9, "packageName"),
        CATEGORY(10, "category"),
        IS_ONLINE(11, "isOnline"),
        REG_ID(12, "regId"),
        CALLBACK_URL(13, "callbackUrl"),
        USER_ACCOUNT(14, "userAccount"),
        DEVICE_STATUS(15, "deviceStatus"),
        GEO_MSG_STATUS(16, "geoMsgStatus"),
        IMEI_MD5(20, "imeiMd5"),
        DEVICE_ID(21, "deviceId"),
        PASS_THROUGH(22, "passThrough"),
        EXTRA(23, "extra");
        
        private static final Map<String, a> u = null;
        private final short v;
        private final String w;

        static {
            u = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                u.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.v = s;
            this.w = str;
        }

        public final String a() {
            return this.w;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new org.apache.thrift.meta_data.b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new org.apache.thrift.meta_data.b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new org.apache.thrift.meta_data.b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new org.apache.thrift.meta_data.b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.MESSAGE_TS, new org.apache.thrift.meta_data.b("messageTs", 1, new c(10)));
        enumMap.put(a.TOPIC, new org.apache.thrift.meta_data.b("topic", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.ALIAS_NAME, new org.apache.thrift.meta_data.b("aliasName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REQUEST, new org.apache.thrift.meta_data.b("request", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, an.class)));
        enumMap.put(a.PACKAGE_NAME, new org.apache.thrift.meta_data.b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CATEGORY, new org.apache.thrift.meta_data.b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.IS_ONLINE, new org.apache.thrift.meta_data.b("isOnline", 2, new c(2)));
        enumMap.put(a.REG_ID, new org.apache.thrift.meta_data.b("regId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CALLBACK_URL, new org.apache.thrift.meta_data.b("callbackUrl", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.USER_ACCOUNT, new org.apache.thrift.meta_data.b("userAccount", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.DEVICE_STATUS, new org.apache.thrift.meta_data.b("deviceStatus", 2, new c(6)));
        enumMap.put(a.GEO_MSG_STATUS, new org.apache.thrift.meta_data.b("geoMsgStatus", 2, new c(6)));
        enumMap.put(a.IMEI_MD5, new org.apache.thrift.meta_data.b("imeiMd5", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.DEVICE_ID, new org.apache.thrift.meta_data.b("deviceId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PASS_THROUGH, new org.apache.thrift.meta_data.b("passThrough", 2, new c(8)));
        enumMap.put(a.EXTRA, new org.apache.thrift.meta_data.b("extra", 2, new e(13, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES), new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        u = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(z.class, u);
    }

    public z a(long j2) {
        this.e = j2;
        a(true);
        return this;
    }

    public z a(String str) {
        this.c = str;
        return this;
    }

    public z a(short s2) {
        this.o = s2;
        c(true);
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r8) {
        /*
            r7 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r8.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0143
            short r1 = r0.c
            r2 = 12
            r3 = 6
            r4 = 2
            r5 = 1
            r6 = 11
            switch(r1) {
                case 1: goto L_0x0137;
                case 2: goto L_0x0125;
                case 3: goto L_0x0119;
                case 4: goto L_0x010d;
                case 5: goto L_0x00fc;
                case 6: goto L_0x00f0;
                case 7: goto L_0x00e4;
                case 8: goto L_0x00d2;
                case 9: goto L_0x00c6;
                case 10: goto L_0x00ba;
                case 11: goto L_0x00ab;
                case 12: goto L_0x009f;
                case 13: goto L_0x0093;
                case 14: goto L_0x0087;
                case 15: goto L_0x0078;
                case 16: goto L_0x006a;
                case 17: goto L_0x0014;
                case 18: goto L_0x0014;
                case 19: goto L_0x0014;
                case 20: goto L_0x005f;
                case 21: goto L_0x0054;
                case 22: goto L_0x0044;
                case 23: goto L_0x001a;
                default: goto L_0x0014;
            }
        L_0x0014:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r8, r0)
            goto L_0x0000
        L_0x001a:
            byte r1 = r0.b
            r2 = 13
            if (r1 != r2) goto L_0x0014
            org.apache.thrift.protocol.d r0 = r8.c()
            java.util.HashMap r1 = new java.util.HashMap
            int r2 = r0.c
            int r2 = r2 * 2
            r1.<init>(r2)
            r7.t = r1
            r1 = 0
        L_0x0030:
            int r2 = r0.c
            if (r1 >= r2) goto L_0x0000
            java.lang.String r2 = r8.l()
            java.lang.String r3 = r8.l()
            java.util.Map<java.lang.String, java.lang.String> r4 = r7.t
            r4.put(r2, r3)
            int r1 = r1 + 1
            goto L_0x0030
        L_0x0044:
            byte r1 = r0.b
            r2 = 8
            if (r1 != r2) goto L_0x0014
            int r0 = r8.i()
            r7.s = r0
            r7.e(r5)
            goto L_0x0000
        L_0x0054:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.r = r0
            goto L_0x0000
        L_0x005f:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.q = r0
            goto L_0x0000
        L_0x006a:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            short r0 = r8.h()
            r7.p = r0
            r7.d(r5)
            goto L_0x0000
        L_0x0078:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            short r0 = r8.h()
            r7.o = r0
            r7.c(r5)
            goto L_0x0000
        L_0x0087:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.n = r0
            goto L_0x0000
        L_0x0093:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.m = r0
            goto L_0x0000
        L_0x009f:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.l = r0
            goto L_0x0000
        L_0x00ab:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0014
            boolean r0 = r8.f()
            r7.k = r0
            r7.b(r5)
            goto L_0x0000
        L_0x00ba:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.j = r0
            goto L_0x0000
        L_0x00c6:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.i = r0
            goto L_0x0000
        L_0x00d2:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0014
            com.xiaomi.xmpush.thrift.an r0 = new com.xiaomi.xmpush.thrift.an
            r0.<init>()
            r7.h = r0
            com.xiaomi.xmpush.thrift.an r0 = r7.h
            r0.a(r8)
            goto L_0x0000
        L_0x00e4:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.g = r0
            goto L_0x0000
        L_0x00f0:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.f = r0
            goto L_0x0000
        L_0x00fc:
            byte r1 = r0.b
            r2 = 10
            if (r1 != r2) goto L_0x0014
            long r0 = r8.j()
            r7.e = r0
            r7.a(r5)
            goto L_0x0000
        L_0x010d:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.d = r0
            goto L_0x0000
        L_0x0119:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.c = r0
            goto L_0x0000
        L_0x0125:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0014
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r7.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r7.b
            r0.a(r8)
            goto L_0x0000
        L_0x0137:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0014
            java.lang.String r0 = r8.l()
            r7.a = r0
            goto L_0x0000
        L_0x0143:
            boolean r8 = r7.e()
            if (r8 != 0) goto L_0x0161
            org.apache.thrift.protocol.f r8 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'messageTs' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r7.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r8.<init>(r0)
            throw r8
        L_0x0161:
            r7.u()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.z.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z2) {
        this.Q.set(0, z2);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(z zVar) {
        if (zVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = zVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(zVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = zVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(zVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = zVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.c.equals(zVar.c))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = zVar.d();
        if (((d2 || d3) && (!d2 || !d3 || !this.d.equals(zVar.d))) || this.e != zVar.e) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = zVar.f();
        if ((f2 || f3) && (!f2 || !f3 || !this.f.equals(zVar.f))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = zVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.g.equals(zVar.g))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = zVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.h.a(zVar.h))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = zVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.i.equals(zVar.i))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = zVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.j.equals(zVar.j))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = zVar.k();
        if ((k2 || k3) && (!k2 || !k3 || this.k != zVar.k)) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = zVar.l();
        if ((l2 || l3) && (!l2 || !l3 || !this.l.equals(zVar.l))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = zVar.m();
        if ((m2 || m3) && (!m2 || !m3 || !this.m.equals(zVar.m))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = zVar.n();
        if ((n2 || n3) && (!n2 || !n3 || !this.n.equals(zVar.n))) {
            return false;
        }
        boolean o2 = o();
        boolean o3 = zVar.o();
        if ((o2 || o3) && (!o2 || !o3 || this.o != zVar.o)) {
            return false;
        }
        boolean p2 = p();
        boolean p3 = zVar.p();
        if ((p2 || p3) && (!p2 || !p3 || this.p != zVar.p)) {
            return false;
        }
        boolean q2 = q();
        boolean q3 = zVar.q();
        if ((q2 || q3) && (!q2 || !q3 || !this.q.equals(zVar.q))) {
            return false;
        }
        boolean r2 = r();
        boolean r3 = zVar.r();
        if ((r2 || r3) && (!r2 || !r3 || !this.r.equals(zVar.r))) {
            return false;
        }
        boolean s2 = s();
        boolean s3 = zVar.s();
        if ((s2 || s3) && (!s2 || !s3 || this.s != zVar.s)) {
            return false;
        }
        boolean t2 = t();
        boolean t3 = zVar.t();
        return (!t2 && !t3) || (t2 && t3 && this.t.equals(zVar.t));
    }

    /* renamed from: b */
    public int compareTo(z zVar) {
        if (!getClass().equals(zVar.getClass())) {
            return getClass().getName().compareTo(zVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(zVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, zVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(zVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) zVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(zVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, zVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(zVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, zVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(zVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e()) {
            int a6 = org.apache.thrift.b.a(this.e, zVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(zVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f()) {
            int a7 = org.apache.thrift.b.a(this.f, zVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(zVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g()) {
            int a8 = org.apache.thrift.b.a(this.g, zVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(zVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h()) {
            int a9 = org.apache.thrift.b.a((Comparable) this.h, (Comparable) zVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(zVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i()) {
            int a10 = org.apache.thrift.b.a(this.i, zVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(zVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j()) {
            int a11 = org.apache.thrift.b.a(this.j, zVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        int compareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(zVar.k()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (k()) {
            int a12 = org.apache.thrift.b.a(this.k, zVar.k);
            if (a12 != 0) {
                return a12;
            }
        }
        int compareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(zVar.l()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (l()) {
            int a13 = org.apache.thrift.b.a(this.l, zVar.l);
            if (a13 != 0) {
                return a13;
            }
        }
        int compareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(zVar.m()));
        if (compareTo13 != 0) {
            return compareTo13;
        }
        if (m()) {
            int a14 = org.apache.thrift.b.a(this.m, zVar.m);
            if (a14 != 0) {
                return a14;
            }
        }
        int compareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(zVar.n()));
        if (compareTo14 != 0) {
            return compareTo14;
        }
        if (n()) {
            int a15 = org.apache.thrift.b.a(this.n, zVar.n);
            if (a15 != 0) {
                return a15;
            }
        }
        int compareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(zVar.o()));
        if (compareTo15 != 0) {
            return compareTo15;
        }
        if (o()) {
            int a16 = org.apache.thrift.b.a(this.o, zVar.o);
            if (a16 != 0) {
                return a16;
            }
        }
        int compareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(zVar.p()));
        if (compareTo16 != 0) {
            return compareTo16;
        }
        if (p()) {
            int a17 = org.apache.thrift.b.a(this.p, zVar.p);
            if (a17 != 0) {
                return a17;
            }
        }
        int compareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(zVar.q()));
        if (compareTo17 != 0) {
            return compareTo17;
        }
        if (q()) {
            int a18 = org.apache.thrift.b.a(this.q, zVar.q);
            if (a18 != 0) {
                return a18;
            }
        }
        int compareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(zVar.r()));
        if (compareTo18 != 0) {
            return compareTo18;
        }
        if (r()) {
            int a19 = org.apache.thrift.b.a(this.r, zVar.r);
            if (a19 != 0) {
                return a19;
            }
        }
        int compareTo19 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(zVar.s()));
        if (compareTo19 != 0) {
            return compareTo19;
        }
        if (s()) {
            int a20 = org.apache.thrift.b.a(this.s, zVar.s);
            if (a20 != 0) {
                return a20;
            }
        }
        int compareTo20 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(zVar.t()));
        if (compareTo20 != 0) {
            return compareTo20;
        }
        if (t()) {
            int a21 = org.apache.thrift.b.a((Map) this.t, (Map) zVar.t);
            if (a21 != 0) {
                return a21;
            }
        }
        return 0;
    }

    public z b(String str) {
        this.d = str;
        return this;
    }

    public z b(short s2) {
        this.p = s2;
        d(true);
        return this;
    }

    public void b(org.apache.thrift.protocol.e eVar) {
        u();
        if (this.a != null && a()) {
            eVar.a(w);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(x);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(y);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(z);
            eVar.a(this.d);
        }
        eVar.a(A);
        eVar.a(this.e);
        if (this.f != null && f()) {
            eVar.a(B);
            eVar.a(this.f);
        }
        if (this.g != null && g()) {
            eVar.a(C);
            eVar.a(this.g);
        }
        if (this.h != null && h()) {
            eVar.a(D);
            this.h.b(eVar);
        }
        if (this.i != null && i()) {
            eVar.a(E);
            eVar.a(this.i);
        }
        if (this.j != null && j()) {
            eVar.a(F);
            eVar.a(this.j);
        }
        if (k()) {
            eVar.a(G);
            eVar.a(this.k);
        }
        if (this.l != null && l()) {
            eVar.a(H);
            eVar.a(this.l);
        }
        if (this.m != null && m()) {
            eVar.a(I);
            eVar.a(this.m);
        }
        if (this.n != null && n()) {
            eVar.a(J);
            eVar.a(this.n);
        }
        if (o()) {
            eVar.a(K);
            eVar.a(this.o);
        }
        if (p()) {
            eVar.a(L);
            eVar.a(this.p);
        }
        if (this.q != null && q()) {
            eVar.a(M);
            eVar.a(this.q);
        }
        if (this.r != null && r()) {
            eVar.a(N);
            eVar.a(this.r);
        }
        if (s()) {
            eVar.a(O);
            eVar.a(this.s);
        }
        if (this.t != null && t()) {
            eVar.a(P);
            eVar.a(new d(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.t.size()));
            for (Entry next : this.t.entrySet()) {
                eVar.a((String) next.getKey());
                eVar.a((String) next.getValue());
            }
        }
        eVar.a();
    }

    public void b(boolean z2) {
        this.Q.set(1, z2);
    }

    public boolean b() {
        return this.b != null;
    }

    public z c(String str) {
        this.f = str;
        return this;
    }

    public void c(boolean z2) {
        this.Q.set(2, z2);
    }

    public boolean c() {
        return this.c != null;
    }

    public z d(String str) {
        this.g = str;
        return this;
    }

    public void d(boolean z2) {
        this.Q.set(3, z2);
    }

    public boolean d() {
        return this.d != null;
    }

    public void e(boolean z2) {
        this.Q.set(4, z2);
    }

    public boolean e() {
        return this.Q.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof z)) {
            return a((z) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f != null;
    }

    public boolean g() {
        return this.g != null;
    }

    public boolean h() {
        return this.h != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.i != null;
    }

    public boolean j() {
        return this.j != null;
    }

    public boolean k() {
        return this.Q.get(1);
    }

    public boolean l() {
        return this.l != null;
    }

    public boolean m() {
        return this.m != null;
    }

    public boolean n() {
        return this.n != null;
    }

    public boolean o() {
        return this.Q.get(2);
    }

    public boolean p() {
        return this.Q.get(3);
    }

    public boolean q() {
        return this.q != null;
    }

    public boolean r() {
        return this.r != null;
    }

    public boolean s() {
        return this.Q.get(4);
    }

    public boolean t() {
        return this.t != null;
    }

    public String toString() {
        boolean z2;
        StringBuilder sb = new StringBuilder("XmPushActionAckMessage(");
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
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.e);
        if (f()) {
            sb.append(", ");
            sb.append("topic:");
            sb.append(this.f == null ? "null" : this.f);
        }
        if (g()) {
            sb.append(", ");
            sb.append("aliasName:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (h()) {
            sb.append(", ");
            sb.append("request:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.i == null ? "null" : this.i);
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            sb.append(this.j == null ? "null" : this.j);
        }
        if (k()) {
            sb.append(", ");
            sb.append("isOnline:");
            sb.append(this.k);
        }
        if (l()) {
            sb.append(", ");
            sb.append("regId:");
            sb.append(this.l == null ? "null" : this.l);
        }
        if (m()) {
            sb.append(", ");
            sb.append("callbackUrl:");
            sb.append(this.m == null ? "null" : this.m);
        }
        if (n()) {
            sb.append(", ");
            sb.append("userAccount:");
            sb.append(this.n == null ? "null" : this.n);
        }
        if (o()) {
            sb.append(", ");
            sb.append("deviceStatus:");
            sb.append(this.o);
        }
        if (p()) {
            sb.append(", ");
            sb.append("geoMsgStatus:");
            sb.append(this.p);
        }
        if (q()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            sb.append(this.q == null ? "null" : this.q);
        }
        if (r()) {
            sb.append(", ");
            sb.append("deviceId:");
            sb.append(this.r == null ? "null" : this.r);
        }
        if (s()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.s);
        }
        if (t()) {
            sb.append(", ");
            sb.append("extra:");
            if (this.t == null) {
                sb.append("null");
            } else {
                sb.append(this.t);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public void u() {
        if (this.c == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.d == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'appId' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        }
    }
}
