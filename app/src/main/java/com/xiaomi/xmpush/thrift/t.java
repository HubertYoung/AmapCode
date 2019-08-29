package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.mobile.rome.longlinkservice.LongLinkMsgConstants;
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

public class t implements Serializable, Cloneable, org.apache.thrift.a<t, a> {
    public static final Map<a, b> i;
    private static final j j = new j("PushMessage");
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("to", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 1);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 2);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b(LongLinkMsgConstants.LONGLINK_APPDATA, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("createAt", 10, 5);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("ttl", 10, 6);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("collapseKey", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 8);
    public x a;
    public String b;
    public String c;
    public String d;
    public long e;
    public long f;
    public String g;
    public String h;
    private BitSet s = new BitSet(2);

    public enum a {
        TO(1, "to"),
        ID(2, "id"),
        APP_ID(3, "appId"),
        PAYLOAD(4, LongLinkMsgConstants.LONGLINK_APPDATA),
        CREATE_AT(5, "createAt"),
        TTL(6, "ttl"),
        COLLAPSE_KEY(7, "collapseKey"),
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
        enumMap.put(a.TO, new b("to", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PAYLOAD, new b(LongLinkMsgConstants.LONGLINK_APPDATA, 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CREATE_AT, new b("createAt", 2, new c(10)));
        enumMap.put(a.TTL, new b("ttl", 2, new c(10)));
        enumMap.put(a.COLLAPSE_KEY, new b("collapseKey", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        i = Collections.unmodifiableMap(enumMap);
        b.a(t.class, i);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r6) {
        /*
            r5 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r6.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x007e
            short r1 = r0.c
            r2 = 1
            r3 = 10
            r4 = 11
            switch(r1) {
                case 1: goto L_0x006b;
                case 2: goto L_0x0060;
                case 3: goto L_0x0055;
                case 4: goto L_0x004a;
                case 5: goto L_0x003c;
                case 6: goto L_0x002e;
                case 7: goto L_0x0023;
                case 8: goto L_0x0018;
                default: goto L_0x0012;
            }
        L_0x0012:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r6, r0)
            goto L_0x0000
        L_0x0018:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.h = r0
            goto L_0x0000
        L_0x0023:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.g = r0
            goto L_0x0000
        L_0x002e:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0012
            long r0 = r6.j()
            r5.f = r0
            r5.b(r2)
            goto L_0x0000
        L_0x003c:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0012
            long r0 = r6.j()
            r5.e = r0
            r5.a(r2)
            goto L_0x0000
        L_0x004a:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.d = r0
            goto L_0x0000
        L_0x0055:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.c = r0
            goto L_0x0000
        L_0x0060:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0012
            java.lang.String r0 = r6.l()
            r5.b = r0
            goto L_0x0000
        L_0x006b:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x0012
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r5.a = r0
            com.xiaomi.xmpush.thrift.x r0 = r5.a
            r0.a(r6)
            goto L_0x0000
        L_0x007e:
            r5.m()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.t.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.s.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(t tVar) {
        if (tVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = tVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.a(tVar.a))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = tVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.b.equals(tVar.b))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = tVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.c.equals(tVar.c))) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = tVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.d.equals(tVar.d))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = tVar.i();
        if ((i2 || i3) && (!i2 || !i3 || this.e != tVar.e)) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = tVar.j();
        if ((j2 || j3) && (!j2 || !j3 || this.f != tVar.f)) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = tVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.g.equals(tVar.g))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = tVar.l();
        return (!l2 && !l3) || (l2 && l3 && this.h.equals(tVar.h));
    }

    /* renamed from: b */
    public int compareTo(t tVar) {
        if (!getClass().equals(tVar.getClass())) {
            return getClass().getName().compareTo(tVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(tVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a((Comparable) this.a, (Comparable) tVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(tVar.c()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (c()) {
            int a3 = org.apache.thrift.b.a(this.b, tVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(tVar.e()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (e()) {
            int a4 = org.apache.thrift.b.a(this.c, tVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(tVar.g()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (g()) {
            int a5 = org.apache.thrift.b.a(this.d, tVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(tVar.i()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (i()) {
            int a6 = org.apache.thrift.b.a(this.e, tVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(tVar.j()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (j()) {
            int a7 = org.apache.thrift.b.a(this.f, tVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(tVar.k()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (k()) {
            int a8 = org.apache.thrift.b.a(this.g, tVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(tVar.l()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (l()) {
            int a9 = org.apache.thrift.b.a(this.h, tVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        return 0;
    }

    public String b() {
        return this.b;
    }

    public void b(e eVar) {
        m();
        if (this.a != null && a()) {
            eVar.a(k);
            this.a.b(eVar);
        }
        if (this.b != null) {
            eVar.a(l);
            eVar.a(this.b);
        }
        if (this.c != null) {
            eVar.a(m);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(n);
            eVar.a(this.d);
        }
        if (i()) {
            eVar.a(o);
            eVar.a(this.e);
        }
        if (j()) {
            eVar.a(p);
            eVar.a(this.f);
        }
        if (this.g != null && k()) {
            eVar.a(q);
            eVar.a(this.g);
        }
        if (this.h != null && l()) {
            eVar.a(r);
            eVar.a(this.h);
        }
        eVar.a();
    }

    public void b(boolean z) {
        this.s.set(1, z);
    }

    public boolean c() {
        return this.b != null;
    }

    public String d() {
        return this.c;
    }

    public boolean e() {
        return this.c != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof t)) {
            return a((t) obj);
        }
        return false;
    }

    public String f() {
        return this.d;
    }

    public boolean g() {
        return this.d != null;
    }

    public long h() {
        return this.e;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.s.get(0);
    }

    public boolean j() {
        return this.s.get(1);
    }

    public boolean k() {
        return this.g != null;
    }

    public boolean l() {
        return this.h != null;
    }

    public void m() {
        if (this.b == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.c == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'appId' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        } else if (this.d == null) {
            StringBuilder sb3 = new StringBuilder("Required field 'payload' was not present! Struct: ");
            sb3.append(toString());
            throw new f(sb3.toString());
        }
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("PushMessage(");
        if (a()) {
            sb.append("to:");
            if (this.a == null) {
                sb.append("null");
            } else {
                sb.append(this.a);
            }
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            sb.append(", ");
        }
        sb.append("id:");
        sb.append(this.b == null ? "null" : this.b);
        sb.append(", ");
        sb.append("appId:");
        sb.append(this.c == null ? "null" : this.c);
        sb.append(", ");
        sb.append("payload:");
        sb.append(this.d == null ? "null" : this.d);
        if (i()) {
            sb.append(", ");
            sb.append("createAt:");
            sb.append(this.e);
        }
        if (j()) {
            sb.append(", ");
            sb.append("ttl:");
            sb.append(this.f);
        }
        if (k()) {
            sb.append(", ");
            sb.append("collapseKey:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (l()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.h == null ? "null" : this.h);
        }
        sb.append(")");
        return sb.toString();
    }
}
