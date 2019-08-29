package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.thrift.meta_data.b;
import org.apache.thrift.meta_data.c;
import org.apache.thrift.meta_data.d;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class m implements Serializable, Cloneable, org.apache.thrift.a<m, a> {
    public static final Map<a, b> k;
    private static final j l = new j("GeoFencing");
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("name", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 2);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("appId", 10, 3);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("createTime", 10, 5);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("type", 8, 6);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("circleCenter", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 7);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("circleRadius", 4, 9);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("polygonPoints", 15, 10);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("coordinateProvider", 8, 11);
    public String a;
    public String b;
    public long c;
    public String d;
    public long e;
    public n f;
    public o g;
    public double h;
    public List<o> i;
    public j j;
    private BitSet w = new BitSet(3);

    public enum a {
        ID(1, "id"),
        NAME(2, "name"),
        APP_ID(3, "appId"),
        PACKAGE_NAME(4, "packageName"),
        CREATE_TIME(5, "createTime"),
        TYPE(6, "type"),
        CIRCLE_CENTER(7, "circleCenter"),
        CIRCLE_RADIUS(9, "circleRadius"),
        POLYGON_POINTS(10, "polygonPoints"),
        COORDINATE_PROVIDER(11, "coordinateProvider");
        
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
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.NAME, new b("name", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 1, new c(10)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CREATE_TIME, new b("createTime", 1, new c(10)));
        enumMap.put(a.TYPE, new b("type", 1, new org.apache.thrift.meta_data.a(16, n.class)));
        enumMap.put(a.CIRCLE_CENTER, new b("circleCenter", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, o.class)));
        enumMap.put(a.CIRCLE_RADIUS, new b("circleRadius", 2, new c(4)));
        enumMap.put(a.POLYGON_POINTS, new b("polygonPoints", 2, new d(15, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, o.class))));
        enumMap.put(a.COORDINATE_PROVIDER, new b("coordinateProvider", 1, new org.apache.thrift.meta_data.a(16, j.class)));
        k = Collections.unmodifiableMap(enumMap);
        b.a(m.class, k);
    }

    public m a(double d2) {
        this.h = d2;
        c(true);
        return this;
    }

    public m a(long j2) {
        this.c = j2;
        a(true);
        return this;
    }

    public m a(j jVar) {
        this.j = jVar;
        return this;
    }

    public m a(n nVar) {
        this.f = nVar;
        return this;
    }

    public m a(o oVar) {
        this.g = oVar;
        return this;
    }

    public m a(String str) {
        this.a = str;
        return this;
    }

    public m a(List<o> list) {
        this.i = list;
        return this;
    }

    public String a() {
        return this.a;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r7) {
        /*
            r6 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r7.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x00c5
            short r1 = r0.c
            r2 = 10
            r3 = 8
            r4 = 11
            r5 = 1
            switch(r1) {
                case 1: goto L_0x00b9;
                case 2: goto L_0x00ad;
                case 3: goto L_0x009e;
                case 4: goto L_0x0092;
                case 5: goto L_0x0083;
                case 6: goto L_0x0073;
                case 7: goto L_0x0060;
                case 8: goto L_0x0014;
                case 9: goto L_0x0051;
                case 10: goto L_0x0029;
                case 11: goto L_0x001a;
                default: goto L_0x0014;
            }
        L_0x0014:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r7, r0)
            goto L_0x0000
        L_0x001a:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            int r0 = r7.i()
            com.xiaomi.xmpush.thrift.j r0 = com.xiaomi.xmpush.thrift.j.a(r0)
            r6.j = r0
            goto L_0x0000
        L_0x0029:
            byte r1 = r0.b
            r2 = 15
            if (r1 != r2) goto L_0x0014
            org.apache.thrift.protocol.c r0 = r7.d()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.b
            r1.<init>(r2)
            r6.i = r1
            r1 = 0
        L_0x003d:
            int r2 = r0.b
            if (r1 >= r2) goto L_0x0000
            com.xiaomi.xmpush.thrift.o r2 = new com.xiaomi.xmpush.thrift.o
            r2.<init>()
            r2.a(r7)
            java.util.List<com.xiaomi.xmpush.thrift.o> r3 = r6.i
            r3.add(r2)
            int r1 = r1 + 1
            goto L_0x003d
        L_0x0051:
            byte r1 = r0.b
            r2 = 4
            if (r1 != r2) goto L_0x0014
            double r0 = r7.k()
            r6.h = r0
            r6.c(r5)
            goto L_0x0000
        L_0x0060:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x0014
            com.xiaomi.xmpush.thrift.o r0 = new com.xiaomi.xmpush.thrift.o
            r0.<init>()
            r6.g = r0
            com.xiaomi.xmpush.thrift.o r0 = r6.g
            r0.a(r7)
            goto L_0x0000
        L_0x0073:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            int r0 = r7.i()
            com.xiaomi.xmpush.thrift.n r0 = com.xiaomi.xmpush.thrift.n.a(r0)
            r6.f = r0
            goto L_0x0000
        L_0x0083:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0014
            long r0 = r7.j()
            r6.e = r0
            r6.b(r5)
            goto L_0x0000
        L_0x0092:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0014
            java.lang.String r0 = r7.l()
            r6.d = r0
            goto L_0x0000
        L_0x009e:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0014
            long r0 = r7.j()
            r6.c = r0
            r6.a(r5)
            goto L_0x0000
        L_0x00ad:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0014
            java.lang.String r0 = r7.l()
            r6.b = r0
            goto L_0x0000
        L_0x00b9:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0014
            java.lang.String r0 = r7.l()
            r6.a = r0
            goto L_0x0000
        L_0x00c5:
            boolean r7 = r6.f()
            if (r7 != 0) goto L_0x00e3
            org.apache.thrift.protocol.f r7 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'appId' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r6.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0)
            throw r7
        L_0x00e3:
            boolean r7 = r6.j()
            if (r7 != 0) goto L_0x0101
            org.apache.thrift.protocol.f r7 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'createTime' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r6.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0)
            throw r7
        L_0x0101:
            r6.u()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.m.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.w.set(0, z);
    }

    public boolean a(m mVar) {
        if (mVar == null) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = mVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.a.equals(mVar.a))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = mVar.d();
        if (((d2 || d3) && (!d2 || !d3 || !this.b.equals(mVar.b))) || this.c != mVar.c) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = mVar.h();
        if (((h2 || h3) && (!h2 || !h3 || !this.d.equals(mVar.d))) || this.e != mVar.e) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = mVar.l();
        if ((l2 || l3) && (!l2 || !l3 || !this.f.equals(mVar.f))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = mVar.n();
        if ((n2 || n3) && (!n2 || !n3 || !this.g.a(mVar.g))) {
            return false;
        }
        boolean p2 = p();
        boolean p3 = mVar.p();
        if ((p2 || p3) && (!p2 || !p3 || this.h != mVar.h)) {
            return false;
        }
        boolean r2 = r();
        boolean r3 = mVar.r();
        if ((r2 || r3) && (!r2 || !r3 || !this.i.equals(mVar.i))) {
            return false;
        }
        boolean t2 = t();
        boolean t3 = mVar.t();
        return (!t2 && !t3) || (t2 && t3 && this.j.equals(mVar.j));
    }

    /* renamed from: b */
    public int compareTo(m mVar) {
        if (!getClass().equals(mVar.getClass())) {
            return getClass().getName().compareTo(mVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(mVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a(this.a, mVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(mVar.d()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (d()) {
            int a3 = org.apache.thrift.b.a(this.b, mVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(mVar.f()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (f()) {
            int a4 = org.apache.thrift.b.a(this.c, mVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(mVar.h()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (h()) {
            int a5 = org.apache.thrift.b.a(this.d, mVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(mVar.j()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (j()) {
            int a6 = org.apache.thrift.b.a(this.e, mVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(mVar.l()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (l()) {
            int a7 = org.apache.thrift.b.a((Comparable) this.f, (Comparable) mVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(mVar.n()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (n()) {
            int a8 = org.apache.thrift.b.a((Comparable) this.g, (Comparable) mVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(mVar.p()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (p()) {
            int a9 = org.apache.thrift.b.a(this.h, mVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(mVar.r()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (r()) {
            int a10 = org.apache.thrift.b.a((List) this.i, (List) mVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(mVar.t()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (t()) {
            int a11 = org.apache.thrift.b.a((Comparable) this.j, (Comparable) mVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        return 0;
    }

    public m b(long j2) {
        this.e = j2;
        b(true);
        return this;
    }

    public m b(String str) {
        this.b = str;
        return this;
    }

    public void b(e eVar) {
        u();
        if (this.a != null) {
            eVar.a(m);
            eVar.a(this.a);
        }
        if (this.b != null) {
            eVar.a(n);
            eVar.a(this.b);
        }
        eVar.a(o);
        eVar.a(this.c);
        if (this.d != null) {
            eVar.a(p);
            eVar.a(this.d);
        }
        eVar.a(q);
        eVar.a(this.e);
        if (this.f != null) {
            eVar.a(r);
            eVar.a(this.f.a());
        }
        if (this.g != null && n()) {
            eVar.a(s);
            this.g.b(eVar);
        }
        if (p()) {
            eVar.a(t);
            eVar.a(this.h);
        }
        if (this.i != null && r()) {
            eVar.a(u);
            eVar.a(new org.apache.thrift.protocol.c(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.i.size()));
            for (o b2 : this.i) {
                b2.b(eVar);
            }
        }
        if (this.j != null) {
            eVar.a(v);
            eVar.a(this.j.a());
        }
        eVar.a();
    }

    public void b(boolean z) {
        this.w.set(1, z);
    }

    public boolean b() {
        return this.a != null;
    }

    public m c(String str) {
        this.d = str;
        return this;
    }

    public String c() {
        return this.b;
    }

    public void c(boolean z) {
        this.w.set(2, z);
    }

    public boolean d() {
        return this.b != null;
    }

    public long e() {
        return this.c;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof m)) {
            return a((m) obj);
        }
        return false;
    }

    public boolean f() {
        return this.w.get(0);
    }

    public String g() {
        return this.d;
    }

    public boolean h() {
        return this.d != null;
    }

    public int hashCode() {
        return 0;
    }

    public long i() {
        return this.e;
    }

    public boolean j() {
        return this.w.get(1);
    }

    public n k() {
        return this.f;
    }

    public boolean l() {
        return this.f != null;
    }

    public o m() {
        return this.g;
    }

    public boolean n() {
        return this.g != null;
    }

    public double o() {
        return this.h;
    }

    public boolean p() {
        return this.w.get(2);
    }

    public List<o> q() {
        return this.i;
    }

    public boolean r() {
        return this.i != null;
    }

    public j s() {
        return this.j;
    }

    public boolean t() {
        return this.j != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GeoFencing(");
        sb.append("id:");
        sb.append(this.a == null ? "null" : this.a);
        sb.append(", ");
        sb.append("name:");
        sb.append(this.b == null ? "null" : this.b);
        sb.append(", ");
        sb.append("appId:");
        sb.append(this.c);
        sb.append(", ");
        sb.append("packageName:");
        sb.append(this.d == null ? "null" : this.d);
        sb.append(", ");
        sb.append("createTime:");
        sb.append(this.e);
        sb.append(", ");
        sb.append("type:");
        if (this.f == null) {
            sb.append("null");
        } else {
            sb.append(this.f);
        }
        if (n()) {
            sb.append(", ");
            sb.append("circleCenter:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (p()) {
            sb.append(", ");
            sb.append("circleRadius:");
            sb.append(this.h);
        }
        if (r()) {
            sb.append(", ");
            sb.append("polygonPoints:");
            if (this.i == null) {
                sb.append("null");
            } else {
                sb.append(this.i);
            }
        }
        sb.append(", ");
        sb.append("coordinateProvider:");
        if (this.j == null) {
            sb.append("null");
        } else {
            sb.append(this.j);
        }
        sb.append(")");
        return sb.toString();
    }

    public void u() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.b == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'name' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        } else if (this.d == null) {
            StringBuilder sb3 = new StringBuilder("Required field 'packageName' was not present! Struct: ");
            sb3.append(toString());
            throw new f(sb3.toString());
        } else if (this.f == null) {
            StringBuilder sb4 = new StringBuilder("Required field 'type' was not present! Struct: ");
            sb4.append(toString());
            throw new f(sb4.toString());
        } else if (this.j == null) {
            StringBuilder sb5 = new StringBuilder("Required field 'coordinateProvider' was not present! Struct: ");
            sb5.append(toString());
            throw new f(sb5.toString());
        }
    }
}
