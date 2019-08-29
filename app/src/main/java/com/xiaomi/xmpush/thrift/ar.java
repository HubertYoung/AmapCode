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
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class ar implements Serializable, Cloneable, org.apache.thrift.a<ar, a> {
    public static final Map<a, b> i;
    private static final j j = new j("XmPushActionUnRegistrationResult");
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("request", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 5);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("errorCode", 10, 6);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("reason", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    public String a;
    public x b;
    public String c;
    public String d;
    public aq e;
    public long f;
    public String g;
    public String h;
    private BitSet s = new BitSet(1);

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        REQUEST(5, "request"),
        ERROR_CODE(6, "errorCode"),
        REASON(7, "reason"),
        PACKAGE_NAME(8, "packageName");
        
        private static final Map<String, a> i = null;
        private final short j;
        private final String k;

        static {
            i = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                i.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.j = s;
            this.k = str;
        }

        public final String a() {
            return this.k;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REQUEST, new b("request", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, aq.class)));
        enumMap.put(a.ERROR_CODE, new b("errorCode", 1, new c(10)));
        enumMap.put(a.REASON, new b("reason", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        i = Collections.unmodifiableMap(enumMap);
        b.a(ar.class, i);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r5) {
        /*
            r4 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r5.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0082
            short r1 = r0.c
            r2 = 12
            r3 = 11
            switch(r1) {
                case 1: goto L_0x0076;
                case 2: goto L_0x0065;
                case 3: goto L_0x005a;
                case 4: goto L_0x004f;
                case 5: goto L_0x003e;
                case 6: goto L_0x002d;
                case 7: goto L_0x0022;
                case 8: goto L_0x0017;
                default: goto L_0x0011;
            }
        L_0x0011:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0017:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.h = r0
            goto L_0x0000
        L_0x0022:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.g = r0
            goto L_0x0000
        L_0x002d:
            byte r1 = r0.b
            r2 = 10
            if (r1 != r2) goto L_0x0011
            long r0 = r5.j()
            r4.f = r0
            r0 = 1
            r4.a(r0)
            goto L_0x0000
        L_0x003e:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            com.xiaomi.xmpush.thrift.aq r0 = new com.xiaomi.xmpush.thrift.aq
            r0.<init>()
            r4.e = r0
            com.xiaomi.xmpush.thrift.aq r0 = r4.e
            r0.a(r5)
            goto L_0x0000
        L_0x004f:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.d = r0
            goto L_0x0000
        L_0x005a:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.c = r0
            goto L_0x0000
        L_0x0065:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0011
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r4.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r4.b
            r0.a(r5)
            goto L_0x0000
        L_0x0076:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0011
            java.lang.String r0 = r5.l()
            r4.a = r0
            goto L_0x0000
        L_0x0082:
            boolean r5 = r4.f()
            if (r5 != 0) goto L_0x00a0
            org.apache.thrift.protocol.f r5 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'errorCode' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x00a0:
            r4.j()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.ar.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.s.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ar arVar) {
        if (arVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = arVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(arVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = arVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(arVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = arVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.c.equals(arVar.c))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = arVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.d.equals(arVar.d))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = arVar.e();
        if (((e2 || e3) && (!e2 || !e3 || !this.e.a(arVar.e))) || this.f != arVar.f) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = arVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.g.equals(arVar.g))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = arVar.i();
        return (!i2 && !i3) || (i2 && i3 && this.h.equals(arVar.h));
    }

    /* renamed from: b */
    public int compareTo(ar arVar) {
        if (!getClass().equals(arVar.getClass())) {
            return getClass().getName().compareTo(arVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(arVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, arVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(arVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) arVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(arVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, arVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(arVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, arVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(arVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e()) {
            int a6 = org.apache.thrift.b.a((Comparable) this.e, (Comparable) arVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(arVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f()) {
            int a7 = org.apache.thrift.b.a(this.f, arVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(arVar.g()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (g()) {
            int a8 = org.apache.thrift.b.a(this.g, arVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(arVar.i()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (i()) {
            int a9 = org.apache.thrift.b.a(this.h, arVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        j();
        if (this.a != null && a()) {
            eVar.a(k);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(l);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(m);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(n);
            eVar.a(this.d);
        }
        if (this.e != null && e()) {
            eVar.a(o);
            this.e.b(eVar);
        }
        eVar.a(p);
        eVar.a(this.f);
        if (this.g != null && g()) {
            eVar.a(q);
            eVar.a(this.g);
        }
        if (this.h != null && i()) {
            eVar.a(r);
            eVar.a(this.h);
        }
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public boolean c() {
        return this.c != null;
    }

    public boolean d() {
        return this.d != null;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ar)) {
            return a((ar) obj);
        }
        return false;
    }

    public boolean f() {
        return this.s.get(0);
    }

    public boolean g() {
        return this.g != null;
    }

    public String h() {
        return this.h;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.h != null;
    }

    public void j() {
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

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistrationResult(");
        if (a()) {
            sb.append("debug:");
            sb.append(this.a == null ? "null" : this.a);
            z = false;
        } else {
            z = true;
        }
        if (b()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("target:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
            z = false;
        }
        if (!z) {
            sb.append(", ");
        }
        sb.append("id:");
        sb.append(this.c == null ? "null" : this.c);
        sb.append(", ");
        sb.append("appId:");
        sb.append(this.d == null ? "null" : this.d);
        if (e()) {
            sb.append(", ");
            sb.append("request:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f);
        if (g()) {
            sb.append(", ");
            sb.append("reason:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.h == null ? "null" : this.h);
        }
        sb.append(")");
        return sb.toString();
    }
}
