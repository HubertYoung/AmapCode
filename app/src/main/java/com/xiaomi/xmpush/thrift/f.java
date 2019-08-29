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
import org.apache.thrift.meta_data.b;
import org.apache.thrift.meta_data.c;
import org.apache.thrift.meta_data.e;
import org.apache.thrift.protocol.d;
import org.apache.thrift.protocol.j;

public class f implements Serializable, Cloneable, org.apache.thrift.a<f, a> {
    public static final Map<a, b> l;
    private static final j m = new j("ClientUploadDataItem");
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("channel", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("data", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 2);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("name", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("counter", 10, 4);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("timestamp", 10, 5);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("fromSdk", 2, 6);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("sourcePackage", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 9);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("extra", 13, 10);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("pkgName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 11);
    public String a;
    public String b;
    public String c;
    public long d;
    public long e;
    public boolean f;
    public String g;
    public String h;
    public String i;
    public Map<String, String> j;
    public String k;
    private BitSet y = new BitSet(3);

    public enum a {
        CHANNEL(1, "channel"),
        DATA(2, "data"),
        NAME(3, "name"),
        COUNTER(4, "counter"),
        TIMESTAMP(5, "timestamp"),
        FROM_SDK(6, "fromSdk"),
        CATEGORY(7, "category"),
        SOURCE_PACKAGE(8, "sourcePackage"),
        ID(9, "id"),
        EXTRA(10, "extra"),
        PKG_NAME(11, "pkgName");
        
        private static final Map<String, a> l = null;
        private final short m;
        private final String n;

        static {
            l = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                l.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.m = s;
            this.n = str;
        }

        public final String a() {
            return this.n;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.CHANNEL, new b("channel", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.DATA, new b("data", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.NAME, new b("name", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.COUNTER, new b("counter", 2, new c(10)));
        enumMap.put(a.TIMESTAMP, new b("timestamp", 2, new c(10)));
        enumMap.put(a.FROM_SDK, new b("fromSdk", 2, new c(2)));
        enumMap.put(a.CATEGORY, new b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.SOURCE_PACKAGE, new b("sourcePackage", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.ID, new b("id", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.EXTRA, new b("extra", 2, new e(13, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES), new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.PKG_NAME, new b("pkgName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        l = Collections.unmodifiableMap(enumMap);
        b.a(f.class, l);
    }

    public f a(long j2) {
        this.d = j2;
        a(true);
        return this;
    }

    public f a(String str) {
        this.a = str;
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
            if (r1 == 0) goto L_0x00bf
            short r1 = r0.c
            r2 = 10
            r3 = 2
            r4 = 1
            r5 = 11
            switch(r1) {
                case 1: goto L_0x00b3;
                case 2: goto L_0x00a7;
                case 3: goto L_0x009b;
                case 4: goto L_0x008c;
                case 5: goto L_0x007d;
                case 6: goto L_0x006f;
                case 7: goto L_0x0064;
                case 8: goto L_0x0059;
                case 9: goto L_0x004e;
                case 10: goto L_0x0024;
                case 11: goto L_0x0019;
                default: goto L_0x0013;
            }
        L_0x0013:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r7, r0)
            goto L_0x0000
        L_0x0019:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.lang.String r0 = r7.l()
            r6.k = r0
            goto L_0x0000
        L_0x0024:
            byte r1 = r0.b
            r2 = 13
            if (r1 != r2) goto L_0x0013
            org.apache.thrift.protocol.d r0 = r7.c()
            java.util.HashMap r1 = new java.util.HashMap
            int r2 = r0.c
            int r2 = r2 * 2
            r1.<init>(r2)
            r6.j = r1
            r1 = 0
        L_0x003a:
            int r2 = r0.c
            if (r1 >= r2) goto L_0x0000
            java.lang.String r2 = r7.l()
            java.lang.String r3 = r7.l()
            java.util.Map<java.lang.String, java.lang.String> r4 = r6.j
            r4.put(r2, r3)
            int r1 = r1 + 1
            goto L_0x003a
        L_0x004e:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.lang.String r0 = r7.l()
            r6.i = r0
            goto L_0x0000
        L_0x0059:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.lang.String r0 = r7.l()
            r6.h = r0
            goto L_0x0000
        L_0x0064:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.lang.String r0 = r7.l()
            r6.g = r0
            goto L_0x0000
        L_0x006f:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0013
            boolean r0 = r7.f()
            r6.f = r0
            r6.d(r4)
            goto L_0x0000
        L_0x007d:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0013
            long r0 = r7.j()
            r6.e = r0
            r6.b(r4)
            goto L_0x0000
        L_0x008c:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0013
            long r0 = r7.j()
            r6.d = r0
            r6.a(r4)
            goto L_0x0000
        L_0x009b:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.lang.String r0 = r7.l()
            r6.c = r0
            goto L_0x0000
        L_0x00a7:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.lang.String r0 = r7.l()
            r6.b = r0
            goto L_0x0000
        L_0x00b3:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.lang.String r0 = r7.l()
            r6.a = r0
            goto L_0x0000
        L_0x00bf:
            r6.r()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.f.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.y.set(0, z);
    }

    public boolean a(f fVar) {
        if (fVar == null) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = fVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.a.equals(fVar.a))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = fVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.b.equals(fVar.b))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = fVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.c.equals(fVar.c))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = fVar.f();
        if ((f2 || f3) && (!f2 || !f3 || this.d != fVar.d)) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = fVar.h();
        if ((h2 || h3) && (!h2 || !h3 || this.e != fVar.e)) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = fVar.i();
        if ((i2 || i3) && (!i2 || !i3 || this.f != fVar.f)) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = fVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.g.equals(fVar.g))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = fVar.l();
        if ((l2 || l3) && (!l2 || !l3 || !this.h.equals(fVar.h))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = fVar.n();
        if ((n2 || n3) && (!n2 || !n3 || !this.i.equals(fVar.i))) {
            return false;
        }
        boolean o2 = o();
        boolean o3 = fVar.o();
        if ((o2 || o3) && (!o2 || !o3 || !this.j.equals(fVar.j))) {
            return false;
        }
        boolean q2 = q();
        boolean q3 = fVar.q();
        return (!q2 && !q3) || (q2 && q3 && this.k.equals(fVar.k));
    }

    /* renamed from: b */
    public int compareTo(f fVar) {
        if (!getClass().equals(fVar.getClass())) {
            return getClass().getName().compareTo(fVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(fVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a(this.a, fVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(fVar.c()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (c()) {
            int a3 = org.apache.thrift.b.a(this.b, fVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(fVar.e()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (e()) {
            int a4 = org.apache.thrift.b.a(this.c, fVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(fVar.f()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (f()) {
            int a5 = org.apache.thrift.b.a(this.d, fVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(fVar.h()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (h()) {
            int a6 = org.apache.thrift.b.a(this.e, fVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(fVar.i()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (i()) {
            int a7 = org.apache.thrift.b.a(this.f, fVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(fVar.j()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (j()) {
            int a8 = org.apache.thrift.b.a(this.g, fVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(fVar.l()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (l()) {
            int a9 = org.apache.thrift.b.a(this.h, fVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(fVar.n()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (n()) {
            int a10 = org.apache.thrift.b.a(this.i, fVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(fVar.o()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (o()) {
            int a11 = org.apache.thrift.b.a((Map) this.j, (Map) fVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        int compareTo11 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(fVar.q()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (q()) {
            int a12 = org.apache.thrift.b.a(this.k, fVar.k);
            if (a12 != 0) {
                return a12;
            }
        }
        return 0;
    }

    public f b(long j2) {
        this.e = j2;
        b(true);
        return this;
    }

    public f b(String str) {
        this.b = str;
        return this;
    }

    public void b(org.apache.thrift.protocol.e eVar) {
        r();
        if (this.a != null && b()) {
            eVar.a(n);
            eVar.a(this.a);
        }
        if (this.b != null && c()) {
            eVar.a(o);
            eVar.a(this.b);
        }
        if (this.c != null && e()) {
            eVar.a(p);
            eVar.a(this.c);
        }
        if (f()) {
            eVar.a(q);
            eVar.a(this.d);
        }
        if (h()) {
            eVar.a(r);
            eVar.a(this.e);
        }
        if (i()) {
            eVar.a(s);
            eVar.a(this.f);
        }
        if (this.g != null && j()) {
            eVar.a(t);
            eVar.a(this.g);
        }
        if (this.h != null && l()) {
            eVar.a(u);
            eVar.a(this.h);
        }
        if (this.i != null && n()) {
            eVar.a(v);
            eVar.a(this.i);
        }
        if (this.j != null && o()) {
            eVar.a(w);
            eVar.a(new d(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.j.size()));
            for (Entry next : this.j.entrySet()) {
                eVar.a((String) next.getKey());
                eVar.a((String) next.getValue());
            }
        }
        if (this.k != null && q()) {
            eVar.a(x);
            eVar.a(this.k);
        }
        eVar.a();
    }

    public void b(boolean z) {
        this.y.set(1, z);
    }

    public boolean b() {
        return this.a != null;
    }

    public f c(String str) {
        this.c = str;
        return this;
    }

    public f c(boolean z) {
        this.f = z;
        d(true);
        return this;
    }

    public boolean c() {
        return this.b != null;
    }

    public f d(String str) {
        this.g = str;
        return this;
    }

    public String d() {
        return this.c;
    }

    public void d(boolean z) {
        this.y.set(2, z);
    }

    public f e(String str) {
        this.h = str;
        return this;
    }

    public boolean e() {
        return this.c != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof f)) {
            return a((f) obj);
        }
        return false;
    }

    public f f(String str) {
        this.i = str;
        return this;
    }

    public boolean f() {
        return this.y.get(0);
    }

    public long g() {
        return this.e;
    }

    public f g(String str) {
        this.k = str;
        return this;
    }

    public boolean h() {
        return this.y.get(1);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.y.get(2);
    }

    public boolean j() {
        return this.g != null;
    }

    public String k() {
        return this.h;
    }

    public boolean l() {
        return this.h != null;
    }

    public String m() {
        return this.i;
    }

    public boolean n() {
        return this.i != null;
    }

    public boolean o() {
        return this.j != null;
    }

    public String p() {
        return this.k;
    }

    public boolean q() {
        return this.k != null;
    }

    public void r() {
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("ClientUploadDataItem(");
        if (b()) {
            sb.append("channel:");
            sb.append(this.a == null ? "null" : this.a);
            z = false;
        } else {
            z = true;
        }
        if (c()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("data:");
            sb.append(this.b == null ? "null" : this.b);
            z = false;
        }
        if (e()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("name:");
            sb.append(this.c == null ? "null" : this.c);
            z = false;
        }
        if (f()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("counter:");
            sb.append(this.d);
            z = false;
        }
        if (h()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("timestamp:");
            sb.append(this.e);
            z = false;
        }
        if (i()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("fromSdk:");
            sb.append(this.f);
            z = false;
        }
        if (j()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("category:");
            sb.append(this.g == null ? "null" : this.g);
            z = false;
        }
        if (l()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("sourcePackage:");
            sb.append(this.h == null ? "null" : this.h);
            z = false;
        }
        if (n()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("id:");
            sb.append(this.i == null ? "null" : this.i);
            z = false;
        }
        if (o()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("extra:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
            z = false;
        }
        if (q()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("pkgName:");
            sb.append(this.k == null ? "null" : this.k);
        }
        sb.append(")");
        return sb.toString();
    }
}
