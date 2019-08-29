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
import org.apache.thrift.meta_data.b;
import org.apache.thrift.meta_data.c;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.j;

public class s implements Serializable, Cloneable, org.apache.thrift.a<s, a> {
    public static final Map<a, b> h;
    private static final j i = new j("OnlineConfigItem");
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b("key", 8, 1);
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("type", 8, 2);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("clear", 2, 3);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("intValue", 8, 4);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("longValue", 10, 5);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("stringValue", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 6);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("boolValue", 2, 7);
    public int a;
    public int b;
    public boolean c;
    public int d;
    public long e;
    public String f;
    public boolean g;
    private BitSet q = new BitSet(6);

    public enum a {
        KEY(1, "key"),
        TYPE(2, "type"),
        CLEAR(3, "clear"),
        INT_VALUE(4, "intValue"),
        LONG_VALUE(5, "longValue"),
        STRING_VALUE(6, "stringValue"),
        BOOL_VALUE(7, "boolValue");
        
        private static final Map<String, a> h = null;
        private final short i;
        private final String j;

        static {
            h = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                h.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.i = s;
            this.j = str;
        }

        public final String a() {
            return this.j;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.KEY, new b("key", 2, new c(8)));
        enumMap.put(a.TYPE, new b("type", 2, new c(8)));
        enumMap.put(a.CLEAR, new b("clear", 2, new c(2)));
        enumMap.put(a.INT_VALUE, new b("intValue", 2, new c(8)));
        enumMap.put(a.LONG_VALUE, new b("longValue", 2, new c(10)));
        enumMap.put(a.STRING_VALUE, new b("stringValue", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.BOOL_VALUE, new b("boolValue", 2, new c(2)));
        h = Collections.unmodifiableMap(enumMap);
        b.a(s.class, h);
    }

    public int a() {
        return this.a;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r6) {
        /*
            r5 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r6.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x007a
            short r1 = r0.c
            r2 = 2
            r3 = 8
            r4 = 1
            switch(r1) {
                case 1: goto L_0x006c;
                case 2: goto L_0x005e;
                case 3: goto L_0x0050;
                case 4: goto L_0x0042;
                case 5: goto L_0x0032;
                case 6: goto L_0x0025;
                case 7: goto L_0x0017;
                default: goto L_0x0011;
            }
        L_0x0011:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r6, r0)
            goto L_0x0000
        L_0x0017:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            boolean r0 = r6.f()
            r5.g = r0
            r5.f(r4)
            goto L_0x0000
        L_0x0025:
            byte r1 = r0.b
            r2 = 11
            if (r1 != r2) goto L_0x0011
            java.lang.String r0 = r6.l()
            r5.f = r0
            goto L_0x0000
        L_0x0032:
            byte r1 = r0.b
            r2 = 10
            if (r1 != r2) goto L_0x0011
            long r0 = r6.j()
            r5.e = r0
            r5.e(r4)
            goto L_0x0000
        L_0x0042:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            int r0 = r6.i()
            r5.d = r0
            r5.d(r4)
            goto L_0x0000
        L_0x0050:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            boolean r0 = r6.f()
            r5.c = r0
            r5.c(r4)
            goto L_0x0000
        L_0x005e:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            int r0 = r6.i()
            r5.b = r0
            r5.b(r4)
            goto L_0x0000
        L_0x006c:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            int r0 = r6.i()
            r5.a = r0
            r5.a(r4)
            goto L_0x0000
        L_0x007a:
            r5.n()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.s.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.q.set(0, z);
    }

    public boolean a(s sVar) {
        if (sVar == null) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = sVar.b();
        if ((b2 || b3) && (!b2 || !b3 || this.a != sVar.a)) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = sVar.d();
        if ((d2 || d3) && (!d2 || !d3 || this.b != sVar.b)) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = sVar.e();
        if ((e2 || e3) && (!e2 || !e3 || this.c != sVar.c)) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = sVar.g();
        if ((g2 || g3) && (!g2 || !g3 || this.d != sVar.d)) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = sVar.i();
        if ((i2 || i3) && (!i2 || !i3 || this.e != sVar.e)) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = sVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.f.equals(sVar.f))) {
            return false;
        }
        boolean m2 = m();
        boolean m3 = sVar.m();
        return (!m2 && !m3) || (m2 && m3 && this.g == sVar.g);
    }

    /* renamed from: b */
    public int compareTo(s sVar) {
        if (!getClass().equals(sVar.getClass())) {
            return getClass().getName().compareTo(sVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(sVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a(this.a, sVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(sVar.d()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (d()) {
            int a3 = org.apache.thrift.b.a(this.b, sVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(sVar.e()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (e()) {
            int a4 = org.apache.thrift.b.a(this.c, sVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(sVar.g()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (g()) {
            int a5 = org.apache.thrift.b.a(this.d, sVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(sVar.i()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (i()) {
            int a6 = org.apache.thrift.b.a(this.e, sVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(sVar.k()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (k()) {
            int a7 = org.apache.thrift.b.a(this.f, sVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(sVar.m()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (m()) {
            int a8 = org.apache.thrift.b.a(this.g, sVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        n();
        if (b()) {
            eVar.a(j);
            eVar.a(this.a);
        }
        if (d()) {
            eVar.a(k);
            eVar.a(this.b);
        }
        if (e()) {
            eVar.a(l);
            eVar.a(this.c);
        }
        if (g()) {
            eVar.a(m);
            eVar.a(this.d);
        }
        if (i()) {
            eVar.a(n);
            eVar.a(this.e);
        }
        if (this.f != null && k()) {
            eVar.a(o);
            eVar.a(this.f);
        }
        if (m()) {
            eVar.a(p);
            eVar.a(this.g);
        }
        eVar.a();
    }

    public void b(boolean z) {
        this.q.set(1, z);
    }

    public boolean b() {
        return this.q.get(0);
    }

    public int c() {
        return this.b;
    }

    public void c(boolean z) {
        this.q.set(2, z);
    }

    public void d(boolean z) {
        this.q.set(3, z);
    }

    public boolean d() {
        return this.q.get(1);
    }

    public void e(boolean z) {
        this.q.set(4, z);
    }

    public boolean e() {
        return this.q.get(2);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof s)) {
            return a((s) obj);
        }
        return false;
    }

    public int f() {
        return this.d;
    }

    public void f(boolean z) {
        this.q.set(5, z);
    }

    public boolean g() {
        return this.q.get(3);
    }

    public long h() {
        return this.e;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.q.get(4);
    }

    public String j() {
        return this.f;
    }

    public boolean k() {
        return this.f != null;
    }

    public boolean l() {
        return this.g;
    }

    public boolean m() {
        return this.q.get(5);
    }

    public void n() {
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("OnlineConfigItem(");
        if (b()) {
            sb.append("key:");
            sb.append(this.a);
            z = false;
        } else {
            z = true;
        }
        if (d()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("type:");
            sb.append(this.b);
            z = false;
        }
        if (e()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("clear:");
            sb.append(this.c);
            z = false;
        }
        if (g()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("intValue:");
            sb.append(this.d);
            z = false;
        }
        if (i()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("longValue:");
            sb.append(this.e);
            z = false;
        }
        if (k()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("stringValue:");
            sb.append(this.f == null ? "null" : this.f);
            z = false;
        }
        if (m()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("boolValue:");
            sb.append(this.g);
        }
        sb.append(")");
        return sb.toString();
    }
}
