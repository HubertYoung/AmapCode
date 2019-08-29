package com.xiaomi.push.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.thrift.meta_data.c;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class b implements Serializable, Cloneable, org.apache.thrift.a<b, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> k;
    private static final j l = new j("StatsEvent");
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("chid", 3, 1);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("type", 8, 2);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("value", 8, 3);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("connpt", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("host", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("subvalue", 8, 6);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("annotation", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("user", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("time", 8, 9);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("clientIp", 8, 10);
    public byte a;
    public int b;
    public int c;
    public String d;
    public String e;
    public int f;
    public String g;
    public String h;
    public int i;
    public int j;
    private BitSet w = new BitSet(6);

    public enum a {
        CHID(1, "chid"),
        TYPE(2, "type"),
        VALUE(3, "value"),
        CONNPT(4, "connpt"),
        HOST(5, "host"),
        SUBVALUE(6, "subvalue"),
        ANNOTATION(7, "annotation"),
        USER(8, "user"),
        TIME(9, "time"),
        CLIENT_IP(10, "clientIp");
        
        private static final Map<String, a> k = null;
        private final short l;
        private final String m;

        static {
            k = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                k.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.l = s;
            this.m = str;
        }

        public final String a() {
            return this.m;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.CHID, new org.apache.thrift.meta_data.b("chid", 1, new c(3)));
        enumMap.put(a.TYPE, new org.apache.thrift.meta_data.b("type", 1, new c(8)));
        enumMap.put(a.VALUE, new org.apache.thrift.meta_data.b("value", 1, new c(8)));
        enumMap.put(a.CONNPT, new org.apache.thrift.meta_data.b("connpt", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.HOST, new org.apache.thrift.meta_data.b("host", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.SUBVALUE, new org.apache.thrift.meta_data.b("subvalue", 2, new c(8)));
        enumMap.put(a.ANNOTATION, new org.apache.thrift.meta_data.b("annotation", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.USER, new org.apache.thrift.meta_data.b("user", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TIME, new org.apache.thrift.meta_data.b("time", 2, new c(8)));
        enumMap.put(a.CLIENT_IP, new org.apache.thrift.meta_data.b("clientIp", 2, new c(8)));
        k = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(b.class, k);
    }

    public b a(byte b2) {
        this.a = b2;
        a(true);
        return this;
    }

    public b a(int i2) {
        this.b = i2;
        b(true);
        return this;
    }

    public b a(String str) {
        this.d = str;
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r6) {
        /*
            r5 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r6.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x009b
            short r1 = r0.c
            r2 = 11
            r3 = 8
            r4 = 1
            switch(r1) {
                case 1: goto L_0x008b;
                case 2: goto L_0x007c;
                case 3: goto L_0x006e;
                case 4: goto L_0x0063;
                case 5: goto L_0x0058;
                case 6: goto L_0x004a;
                case 7: goto L_0x003f;
                case 8: goto L_0x0034;
                case 9: goto L_0x0026;
                case 10: goto L_0x0018;
                default: goto L_0x0012;
            }
        L_0x0012:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r6, r0)
            goto L_0x0000
        L_0x0018:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0012
            int r0 = r6.i()
            r5.j = r0
            r5.f(r4)
            goto L_0x0000
        L_0x0026:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0012
            int r0 = r6.i()
            r5.i = r0
            r5.e(r4)
            goto L_0x0000
        L_0x0034:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.h = r0
            goto L_0x0000
        L_0x003f:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.g = r0
            goto L_0x0000
        L_0x004a:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0012
            int r0 = r6.i()
            r5.f = r0
            r5.d(r4)
            goto L_0x0000
        L_0x0058:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.e = r0
            goto L_0x0000
        L_0x0063:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.d = r0
            goto L_0x0000
        L_0x006e:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0012
            int r0 = r6.i()
            r5.c = r0
            r5.c(r4)
            goto L_0x0000
        L_0x007c:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0012
            int r0 = r6.i()
            r5.b = r0
            r5.b(r4)
            goto L_0x0000
        L_0x008b:
            byte r1 = r0.b
            r2 = 3
            if (r1 != r2) goto L_0x0012
            byte r0 = r6.g()
            r5.a = r0
            r5.a(r4)
            goto L_0x0000
        L_0x009b:
            boolean r6 = r5.a()
            if (r6 != 0) goto L_0x00b9
            org.apache.thrift.protocol.f r6 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'chid' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r5.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r6.<init>(r0)
            throw r6
        L_0x00b9:
            boolean r6 = r5.b()
            if (r6 != 0) goto L_0x00d7
            org.apache.thrift.protocol.f r6 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'type' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r5.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r6.<init>(r0)
            throw r6
        L_0x00d7:
            boolean r6 = r5.c()
            if (r6 != 0) goto L_0x00f5
            org.apache.thrift.protocol.f r6 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'value' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r5.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r6.<init>(r0)
            throw r6
        L_0x00f5:
            r5.k()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.thrift.b.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.w.set(0, z);
    }

    public boolean a() {
        return this.w.get(0);
    }

    public boolean a(b bVar) {
        if (bVar == null || this.a != bVar.a || this.b != bVar.b || this.c != bVar.c) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = bVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.d.equals(bVar.d))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = bVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.e.equals(bVar.e))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = bVar.f();
        if ((f2 || f3) && (!f2 || !f3 || this.f != bVar.f)) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = bVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.g.equals(bVar.g))) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = bVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.h.equals(bVar.h))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = bVar.i();
        if ((i2 || i3) && (!i2 || !i3 || this.i != bVar.i)) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = bVar.j();
        return (!j2 && !j3) || (j2 && j3 && this.j == bVar.j);
    }

    /* renamed from: b */
    public int compareTo(b bVar) {
        if (!getClass().equals(bVar.getClass())) {
            return getClass().getName().compareTo(bVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(bVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, bVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(bVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a(this.b, bVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(bVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, bVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(bVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, bVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(bVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e()) {
            int a6 = org.apache.thrift.b.a(this.e, bVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(bVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f()) {
            int a7 = org.apache.thrift.b.a(this.f, bVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(bVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g()) {
            int a8 = org.apache.thrift.b.a(this.g, bVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(bVar.h()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (h()) {
            int a9 = org.apache.thrift.b.a(this.h, bVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(bVar.i()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (i()) {
            int a10 = org.apache.thrift.b.a(this.i, bVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(bVar.j()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (j()) {
            int a11 = org.apache.thrift.b.a(this.j, bVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        return 0;
    }

    public b b(int i2) {
        this.c = i2;
        c(true);
        return this;
    }

    public b b(String str) {
        this.e = str;
        return this;
    }

    public void b(e eVar) {
        k();
        eVar.a(m);
        eVar.a(this.a);
        eVar.a(n);
        eVar.a(this.b);
        eVar.a(o);
        eVar.a(this.c);
        if (this.d != null) {
            eVar.a(p);
            eVar.a(this.d);
        }
        if (this.e != null && e()) {
            eVar.a(q);
            eVar.a(this.e);
        }
        if (f()) {
            eVar.a(r);
            eVar.a(this.f);
        }
        if (this.g != null && g()) {
            eVar.a(s);
            eVar.a(this.g);
        }
        if (this.h != null && h()) {
            eVar.a(t);
            eVar.a(this.h);
        }
        if (i()) {
            eVar.a(u);
            eVar.a(this.i);
        }
        if (j()) {
            eVar.a(v);
            eVar.a(this.j);
        }
        eVar.a();
    }

    public void b(boolean z) {
        this.w.set(1, z);
    }

    public boolean b() {
        return this.w.get(1);
    }

    public b c(int i2) {
        this.f = i2;
        d(true);
        return this;
    }

    public b c(String str) {
        this.g = str;
        return this;
    }

    public void c(boolean z) {
        this.w.set(2, z);
    }

    public boolean c() {
        return this.w.get(2);
    }

    public b d(int i2) {
        this.i = i2;
        e(true);
        return this;
    }

    public b d(String str) {
        this.h = str;
        return this;
    }

    public void d(boolean z) {
        this.w.set(3, z);
    }

    public boolean d() {
        return this.d != null;
    }

    public b e(int i2) {
        this.j = i2;
        f(true);
        return this;
    }

    public void e(boolean z) {
        this.w.set(4, z);
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof b)) {
            return a((b) obj);
        }
        return false;
    }

    public void f(boolean z) {
        this.w.set(5, z);
    }

    public boolean f() {
        return this.w.get(3);
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
        return this.w.get(4);
    }

    public boolean j() {
        return this.w.get(5);
    }

    public void k() {
        if (this.d == null) {
            StringBuilder sb = new StringBuilder("Required field 'connpt' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvent(");
        sb.append("chid:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("type:");
        sb.append(this.b);
        sb.append(", ");
        sb.append("value:");
        sb.append(this.c);
        sb.append(", ");
        sb.append("connpt:");
        sb.append(this.d == null ? "null" : this.d);
        if (e()) {
            sb.append(", ");
            sb.append("host:");
            sb.append(this.e == null ? "null" : this.e);
        }
        if (f()) {
            sb.append(", ");
            sb.append("subvalue:");
            sb.append(this.f);
        }
        if (g()) {
            sb.append(", ");
            sb.append("annotation:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (h()) {
            sb.append(", ");
            sb.append("user:");
            sb.append(this.h == null ? "null" : this.h);
        }
        if (i()) {
            sb.append(", ");
            sb.append("time:");
            sb.append(this.i);
        }
        if (j()) {
            sb.append(", ");
            sb.append("clientIp:");
            sb.append(this.j);
        }
        sb.append(")");
        return sb.toString();
    }
}
