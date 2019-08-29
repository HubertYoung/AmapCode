package com.xiaomi.xmpush.thrift;

import com.alibaba.wireless.security.securitybodysdk.BuildConfig;
import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.thrift.meta_data.b;
import org.apache.thrift.meta_data.c;
import org.apache.thrift.meta_data.e;
import org.apache.thrift.protocol.d;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class u implements Serializable, Cloneable, org.apache.thrift.a<u, a> {
    public static final Map<a, b> m;
    private static final j n = new j("PushMetaInfo");
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("messageTs", 10, 2);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("topic", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("title", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("description", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("notifyType", 8, 6);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("url", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("passThrough", 8, 8);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b(KEY_TYPE.PUSH_KEY_NOTIFY_ID, 8, 9);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("extra", 13, 10);
    private static final org.apache.thrift.protocol.b y = new org.apache.thrift.protocol.b(BuildConfig.FLAVOR, 13, 11);
    private static final org.apache.thrift.protocol.b z = new org.apache.thrift.protocol.b("ignoreRegInfo", 2, 12);
    private BitSet A;
    public String a;
    public long b;
    public String c;
    public String d;
    public String e;
    public int f;
    public String g;
    public int h;
    public int i;
    public Map<String, String> j;
    public Map<String, String> k;
    public boolean l;

    public enum a {
        ID(1, "id"),
        MESSAGE_TS(2, "messageTs"),
        TOPIC(3, "topic"),
        TITLE(4, "title"),
        DESCRIPTION(5, "description"),
        NOTIFY_TYPE(6, "notifyType"),
        URL(7, "url"),
        PASS_THROUGH(8, "passThrough"),
        NOTIFY_ID(9, KEY_TYPE.PUSH_KEY_NOTIFY_ID),
        EXTRA(10, "extra"),
        INTERNAL(11, BuildConfig.FLAVOR),
        IGNORE_REG_INFO(12, "ignoreRegInfo");
        
        private static final Map<String, a> m = null;
        private final short n;
        private final String o;

        static {
            m = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                m.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.n = s;
            this.o = str;
        }

        public final String a() {
            return this.o;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.MESSAGE_TS, new b("messageTs", 1, new c(10)));
        enumMap.put(a.TOPIC, new b("topic", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TITLE, new b("title", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.DESCRIPTION, new b("description", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.NOTIFY_TYPE, new b("notifyType", 2, new c(8)));
        enumMap.put(a.URL, new b("url", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PASS_THROUGH, new b("passThrough", 2, new c(8)));
        enumMap.put(a.NOTIFY_ID, new b(KEY_TYPE.PUSH_KEY_NOTIFY_ID, 2, new c(8)));
        enumMap.put(a.EXTRA, new b("extra", 2, new e(13, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES), new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.INTERNAL, new b(BuildConfig.FLAVOR, 2, new e(13, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES), new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.IGNORE_REG_INFO, new b("ignoreRegInfo", 2, new c(2)));
        m = Collections.unmodifiableMap(enumMap);
        b.a(u.class, m);
    }

    public u() {
        this.A = new BitSet(5);
        this.l = false;
    }

    public u(u uVar) {
        this.A = new BitSet(5);
        this.A.clear();
        this.A.or(uVar.A);
        if (uVar.c()) {
            this.a = uVar.a;
        }
        this.b = uVar.b;
        if (uVar.g()) {
            this.c = uVar.c;
        }
        if (uVar.i()) {
            this.d = uVar.d;
        }
        if (uVar.k()) {
            this.e = uVar.e;
        }
        this.f = uVar.f;
        if (uVar.n()) {
            this.g = uVar.g;
        }
        this.h = uVar.h;
        this.i = uVar.i;
        if (uVar.t()) {
            HashMap hashMap = new HashMap();
            for (Entry next : uVar.j.entrySet()) {
                hashMap.put((String) next.getKey(), (String) next.getValue());
            }
            this.j = hashMap;
        }
        if (uVar.v()) {
            HashMap hashMap2 = new HashMap();
            for (Entry next2 : uVar.k.entrySet()) {
                hashMap2.put((String) next2.getKey(), (String) next2.getValue());
            }
            this.k = hashMap2;
        }
        this.l = uVar.l;
    }

    public u a() {
        return new u(this);
    }

    public u a(int i2) {
        this.f = i2;
        b(true);
        return this;
    }

    public u a(String str) {
        this.a = str;
        return this;
    }

    public u a(Map<String, String> map) {
        this.j = map;
        return this;
    }

    public void a(String str, String str2) {
        if (this.j == null) {
            this.j = new HashMap();
        }
        this.j.put(str, str2);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r9) {
        /*
            r8 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r9.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x00f2
            short r1 = r0.c
            r2 = 0
            r3 = 13
            r4 = 8
            r5 = 2
            r6 = 11
            r7 = 1
            switch(r1) {
                case 1: goto L_0x00e6;
                case 2: goto L_0x00d5;
                case 3: goto L_0x00c9;
                case 4: goto L_0x00bd;
                case 5: goto L_0x00b1;
                case 6: goto L_0x00a2;
                case 7: goto L_0x0096;
                case 8: goto L_0x0087;
                case 9: goto L_0x0078;
                case 10: goto L_0x0051;
                case 11: goto L_0x002a;
                case 12: goto L_0x001c;
                default: goto L_0x0016;
            }
        L_0x0016:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r9, r0)
            goto L_0x0000
        L_0x001c:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0016
            boolean r0 = r9.f()
            r8.l = r0
            r8.e(r7)
            goto L_0x0000
        L_0x002a:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0016
            org.apache.thrift.protocol.d r0 = r9.c()
            java.util.HashMap r1 = new java.util.HashMap
            int r3 = r0.c
            int r3 = r3 * 2
            r1.<init>(r3)
            r8.k = r1
        L_0x003d:
            int r1 = r0.c
            if (r2 >= r1) goto L_0x0000
            java.lang.String r1 = r9.l()
            java.lang.String r3 = r9.l()
            java.util.Map<java.lang.String, java.lang.String> r4 = r8.k
            r4.put(r1, r3)
            int r2 = r2 + 1
            goto L_0x003d
        L_0x0051:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0016
            org.apache.thrift.protocol.d r0 = r9.c()
            java.util.HashMap r1 = new java.util.HashMap
            int r3 = r0.c
            int r3 = r3 * 2
            r1.<init>(r3)
            r8.j = r1
        L_0x0064:
            int r1 = r0.c
            if (r2 >= r1) goto L_0x0000
            java.lang.String r1 = r9.l()
            java.lang.String r3 = r9.l()
            java.util.Map<java.lang.String, java.lang.String> r4 = r8.j
            r4.put(r1, r3)
            int r2 = r2 + 1
            goto L_0x0064
        L_0x0078:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            int r0 = r9.i()
            r8.i = r0
            r8.d(r7)
            goto L_0x0000
        L_0x0087:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            int r0 = r9.i()
            r8.h = r0
            r8.c(r7)
            goto L_0x0000
        L_0x0096:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0016
            java.lang.String r0 = r9.l()
            r8.g = r0
            goto L_0x0000
        L_0x00a2:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0016
            int r0 = r9.i()
            r8.f = r0
            r8.b(r7)
            goto L_0x0000
        L_0x00b1:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0016
            java.lang.String r0 = r9.l()
            r8.e = r0
            goto L_0x0000
        L_0x00bd:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0016
            java.lang.String r0 = r9.l()
            r8.d = r0
            goto L_0x0000
        L_0x00c9:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0016
            java.lang.String r0 = r9.l()
            r8.c = r0
            goto L_0x0000
        L_0x00d5:
            byte r1 = r0.b
            r2 = 10
            if (r1 != r2) goto L_0x0016
            long r0 = r9.j()
            r8.b = r0
            r8.a(r7)
            goto L_0x0000
        L_0x00e6:
            byte r1 = r0.b
            if (r1 != r6) goto L_0x0016
            java.lang.String r0 = r9.l()
            r8.a = r0
            goto L_0x0000
        L_0x00f2:
            boolean r9 = r8.e()
            if (r9 != 0) goto L_0x0110
            org.apache.thrift.protocol.f r9 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'messageTs' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r8.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r9.<init>(r0)
            throw r9
        L_0x0110:
            r8.y()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.u.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z2) {
        this.A.set(0, z2);
    }

    public boolean a(u uVar) {
        if (uVar == null) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = uVar.c();
        if (((c2 || c3) && (!c2 || !c3 || !this.a.equals(uVar.a))) || this.b != uVar.b) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = uVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.c.equals(uVar.c))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = uVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.d.equals(uVar.d))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = uVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.e.equals(uVar.e))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = uVar.m();
        if ((m2 || m3) && (!m2 || !m3 || this.f != uVar.f)) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = uVar.n();
        if ((n2 || n3) && (!n2 || !n3 || !this.g.equals(uVar.g))) {
            return false;
        }
        boolean p2 = p();
        boolean p3 = uVar.p();
        if ((p2 || p3) && (!p2 || !p3 || this.h != uVar.h)) {
            return false;
        }
        boolean r2 = r();
        boolean r3 = uVar.r();
        if ((r2 || r3) && (!r2 || !r3 || this.i != uVar.i)) {
            return false;
        }
        boolean t2 = t();
        boolean t3 = uVar.t();
        if ((t2 || t3) && (!t2 || !t3 || !this.j.equals(uVar.j))) {
            return false;
        }
        boolean v2 = v();
        boolean v3 = uVar.v();
        if ((v2 || v3) && (!v2 || !v3 || !this.k.equals(uVar.k))) {
            return false;
        }
        boolean x2 = x();
        boolean x3 = uVar.x();
        return (!x2 && !x3) || (x2 && x3 && this.l == uVar.l);
    }

    /* renamed from: b */
    public int compareTo(u uVar) {
        if (!getClass().equals(uVar.getClass())) {
            return getClass().getName().compareTo(uVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(uVar.c()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (c()) {
            int a2 = org.apache.thrift.b.a(this.a, uVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(uVar.e()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (e()) {
            int a3 = org.apache.thrift.b.a(this.b, uVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(uVar.g()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (g()) {
            int a4 = org.apache.thrift.b.a(this.c, uVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(uVar.i()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (i()) {
            int a5 = org.apache.thrift.b.a(this.d, uVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(uVar.k()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (k()) {
            int a6 = org.apache.thrift.b.a(this.e, uVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(uVar.m()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (m()) {
            int a7 = org.apache.thrift.b.a(this.f, uVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(uVar.n()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (n()) {
            int a8 = org.apache.thrift.b.a(this.g, uVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(uVar.p()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (p()) {
            int a9 = org.apache.thrift.b.a(this.h, uVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(uVar.r()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (r()) {
            int a10 = org.apache.thrift.b.a(this.i, uVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(uVar.t()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (t()) {
            int a11 = org.apache.thrift.b.a((Map) this.j, (Map) uVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        int compareTo11 = Boolean.valueOf(v()).compareTo(Boolean.valueOf(uVar.v()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (v()) {
            int a12 = org.apache.thrift.b.a((Map) this.k, (Map) uVar.k);
            if (a12 != 0) {
                return a12;
            }
        }
        int compareTo12 = Boolean.valueOf(x()).compareTo(Boolean.valueOf(uVar.x()));
        if (compareTo12 != 0) {
            return compareTo12;
        }
        if (x()) {
            int a13 = org.apache.thrift.b.a(this.l, uVar.l);
            if (a13 != 0) {
                return a13;
            }
        }
        return 0;
    }

    public u b(int i2) {
        this.h = i2;
        c(true);
        return this;
    }

    public u b(String str) {
        this.c = str;
        return this;
    }

    public String b() {
        return this.a;
    }

    public void b(String str, String str2) {
        if (this.k == null) {
            this.k = new HashMap();
        }
        this.k.put(str, str2);
    }

    public void b(org.apache.thrift.protocol.e eVar) {
        y();
        if (this.a != null) {
            eVar.a(o);
            eVar.a(this.a);
        }
        eVar.a(p);
        eVar.a(this.b);
        if (this.c != null && g()) {
            eVar.a(q);
            eVar.a(this.c);
        }
        if (this.d != null && i()) {
            eVar.a(r);
            eVar.a(this.d);
        }
        if (this.e != null && k()) {
            eVar.a(s);
            eVar.a(this.e);
        }
        if (m()) {
            eVar.a(t);
            eVar.a(this.f);
        }
        if (this.g != null && n()) {
            eVar.a(u);
            eVar.a(this.g);
        }
        if (p()) {
            eVar.a(v);
            eVar.a(this.h);
        }
        if (r()) {
            eVar.a(w);
            eVar.a(this.i);
        }
        if (this.j != null && t()) {
            eVar.a(x);
            eVar.a(new d(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.j.size()));
            for (Entry next : this.j.entrySet()) {
                eVar.a((String) next.getKey());
                eVar.a((String) next.getValue());
            }
        }
        if (this.k != null && v()) {
            eVar.a(y);
            eVar.a(new d(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.k.size()));
            for (Entry next2 : this.k.entrySet()) {
                eVar.a((String) next2.getKey());
                eVar.a((String) next2.getValue());
            }
        }
        if (x()) {
            eVar.a(z);
            eVar.a(this.l);
        }
        eVar.a();
    }

    public void b(boolean z2) {
        this.A.set(1, z2);
    }

    public u c(int i2) {
        this.i = i2;
        d(true);
        return this;
    }

    public u c(String str) {
        this.d = str;
        return this;
    }

    public void c(boolean z2) {
        this.A.set(2, z2);
    }

    public boolean c() {
        return this.a != null;
    }

    public long d() {
        return this.b;
    }

    public u d(String str) {
        this.e = str;
        return this;
    }

    public void d(boolean z2) {
        this.A.set(3, z2);
    }

    public void e(boolean z2) {
        this.A.set(4, z2);
    }

    public boolean e() {
        return this.A.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof u)) {
            return a((u) obj);
        }
        return false;
    }

    public String f() {
        return this.c;
    }

    public boolean g() {
        return this.c != null;
    }

    public String h() {
        return this.d;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.d != null;
    }

    public String j() {
        return this.e;
    }

    public boolean k() {
        return this.e != null;
    }

    public int l() {
        return this.f;
    }

    public boolean m() {
        return this.A.get(1);
    }

    public boolean n() {
        return this.g != null;
    }

    public int o() {
        return this.h;
    }

    public boolean p() {
        return this.A.get(2);
    }

    public int q() {
        return this.i;
    }

    public boolean r() {
        return this.A.get(3);
    }

    public Map<String, String> s() {
        return this.j;
    }

    public boolean t() {
        return this.j != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PushMetaInfo(");
        sb.append("id:");
        sb.append(this.a == null ? "null" : this.a);
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.b);
        if (g()) {
            sb.append(", ");
            sb.append("topic:");
            sb.append(this.c == null ? "null" : this.c);
        }
        if (i()) {
            sb.append(", ");
            sb.append("title:");
            sb.append(this.d == null ? "null" : this.d);
        }
        if (k()) {
            sb.append(", ");
            sb.append("description:");
            sb.append(this.e == null ? "null" : this.e);
        }
        if (m()) {
            sb.append(", ");
            sb.append("notifyType:");
            sb.append(this.f);
        }
        if (n()) {
            sb.append(", ");
            sb.append("url:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (p()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.h);
        }
        if (r()) {
            sb.append(", ");
            sb.append("notifyId:");
            sb.append(this.i);
        }
        if (t()) {
            sb.append(", ");
            sb.append("extra:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
        }
        if (v()) {
            sb.append(", ");
            sb.append("internal:");
            if (this.k == null) {
                sb.append("null");
            } else {
                sb.append(this.k);
            }
        }
        if (x()) {
            sb.append(", ");
            sb.append("ignoreRegInfo:");
            sb.append(this.l);
        }
        sb.append(")");
        return sb.toString();
    }

    public Map<String, String> u() {
        return this.k;
    }

    public boolean v() {
        return this.k != null;
    }

    public boolean w() {
        return this.l;
    }

    public boolean x() {
        return this.A.get(4);
    }

    public void y() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }
}
