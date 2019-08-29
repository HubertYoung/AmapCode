package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
import java.nio.ByteBuffer;
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

public class af implements Serializable, Cloneable, org.apache.thrift.a<af, a> {
    public static final Map<a, b> i;
    private static final j j = new j("XmPushActionContainer");
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("action", 8, 1);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("encryptAction", 2, 2);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("isRequest", 2, 3);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("pushAction", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("appid", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 6);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 7);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("metaInfo", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 8);
    public a a;
    public boolean b = true;
    public boolean c = true;
    public ByteBuffer d;
    public String e;
    public String f;
    public x g;
    public u h;
    private BitSet s = new BitSet(2);

    public enum a {
        ACTION(1, "action"),
        ENCRYPT_ACTION(2, "encryptAction"),
        IS_REQUEST(3, "isRequest"),
        PUSH_ACTION(4, "pushAction"),
        APPID(5, "appid"),
        PACKAGE_NAME(6, "packageName"),
        TARGET(7, "target"),
        META_INFO(8, "metaInfo");
        
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
        enumMap.put(a.ACTION, new b("action", 1, new org.apache.thrift.meta_data.a(16, a.class)));
        enumMap.put(a.ENCRYPT_ACTION, new b("encryptAction", 1, new c(2)));
        enumMap.put(a.IS_REQUEST, new b("isRequest", 1, new c(2)));
        enumMap.put(a.PUSH_ACTION, new b("pushAction", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APPID, new b("appid", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new b("target", 1, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.META_INFO, new b("metaInfo", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, u.class)));
        i = Collections.unmodifiableMap(enumMap);
        b.a(af.class, i);
    }

    public a a() {
        return this.a;
    }

    public af a(a aVar) {
        this.a = aVar;
        return this;
    }

    public af a(u uVar) {
        this.h = uVar;
        return this;
    }

    public af a(x xVar) {
        this.g = xVar;
        return this;
    }

    public af a(String str) {
        this.e = str;
        return this;
    }

    public af a(ByteBuffer byteBuffer) {
        this.d = byteBuffer;
        return this;
    }

    public af a(boolean z) {
        this.b = z;
        b(true);
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r7) {
        /*
            r6 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r7.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x008a
            short r1 = r0.c
            r2 = 1
            r3 = 2
            r4 = 12
            r5 = 11
            switch(r1) {
                case 1: goto L_0x0078;
                case 2: goto L_0x006a;
                case 3: goto L_0x005c;
                case 4: goto L_0x0051;
                case 5: goto L_0x0046;
                case 6: goto L_0x003b;
                case 7: goto L_0x002a;
                case 8: goto L_0x0019;
                default: goto L_0x0013;
            }
        L_0x0013:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r7, r0)
            goto L_0x0000
        L_0x0019:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0013
            com.xiaomi.xmpush.thrift.u r0 = new com.xiaomi.xmpush.thrift.u
            r0.<init>()
            r6.h = r0
            com.xiaomi.xmpush.thrift.u r0 = r6.h
            r0.a(r7)
            goto L_0x0000
        L_0x002a:
            byte r1 = r0.b
            if (r1 != r4) goto L_0x0013
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r6.g = r0
            com.xiaomi.xmpush.thrift.x r0 = r6.g
            r0.a(r7)
            goto L_0x0000
        L_0x003b:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.lang.String r0 = r7.l()
            r6.f = r0
            goto L_0x0000
        L_0x0046:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.lang.String r0 = r7.l()
            r6.e = r0
            goto L_0x0000
        L_0x0051:
            byte r1 = r0.b
            if (r1 != r5) goto L_0x0013
            java.nio.ByteBuffer r0 = r7.m()
            r6.d = r0
            goto L_0x0000
        L_0x005c:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0013
            boolean r0 = r7.f()
            r6.c = r0
            r6.d(r2)
            goto L_0x0000
        L_0x006a:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0013
            boolean r0 = r7.f()
            r6.b = r0
            r6.b(r2)
            goto L_0x0000
        L_0x0078:
            byte r1 = r0.b
            r2 = 8
            if (r1 != r2) goto L_0x0013
            int r0 = r7.i()
            com.xiaomi.xmpush.thrift.a r0 = com.xiaomi.xmpush.thrift.a.a(r0)
            r6.a = r0
            goto L_0x0000
        L_0x008a:
            boolean r7 = r6.d()
            if (r7 != 0) goto L_0x00a8
            org.apache.thrift.protocol.f r7 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'encryptAction' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r6.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0)
            throw r7
        L_0x00a8:
            boolean r7 = r6.e()
            if (r7 != 0) goto L_0x00c6
            org.apache.thrift.protocol.f r7 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'isRequest' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r6.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.<init>(r0)
            throw r7
        L_0x00c6:
            r6.o()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.af.a(org.apache.thrift.protocol.e):void");
    }

    public boolean a(af afVar) {
        if (afVar == null) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = afVar.b();
        if (((b2 || b3) && (!b2 || !b3 || !this.a.equals(afVar.a))) || this.b != afVar.b || this.c != afVar.c) {
            return false;
        }
        boolean g2 = g();
        boolean g3 = afVar.g();
        if ((g2 || g3) && (!g2 || !g3 || !this.d.equals(afVar.d))) {
            return false;
        }
        boolean i2 = i();
        boolean i3 = afVar.i();
        if ((i2 || i3) && (!i2 || !i3 || !this.e.equals(afVar.e))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = afVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.f.equals(afVar.f))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = afVar.l();
        if ((l2 || l3) && (!l2 || !l3 || !this.g.a(afVar.g))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = afVar.n();
        return (!n2 && !n3) || (n2 && n3 && this.h.a(afVar.h));
    }

    /* renamed from: b */
    public int compareTo(af afVar) {
        if (!getClass().equals(afVar.getClass())) {
            return getClass().getName().compareTo(afVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(afVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a((Comparable) this.a, (Comparable) afVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(afVar.d()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (d()) {
            int a3 = org.apache.thrift.b.a(this.b, afVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(afVar.e()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (e()) {
            int a4 = org.apache.thrift.b.a(this.c, afVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(afVar.g()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (g()) {
            int a5 = org.apache.thrift.b.a((Comparable) this.d, (Comparable) afVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(afVar.i()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (i()) {
            int a6 = org.apache.thrift.b.a(this.e, afVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(afVar.k()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (k()) {
            int a7 = org.apache.thrift.b.a(this.f, afVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(afVar.l()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (l()) {
            int a8 = org.apache.thrift.b.a((Comparable) this.g, (Comparable) afVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(afVar.n()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (n()) {
            int a9 = org.apache.thrift.b.a((Comparable) this.h, (Comparable) afVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        return 0;
    }

    public af b(String str) {
        this.f = str;
        return this;
    }

    public void b(e eVar) {
        o();
        if (this.a != null) {
            eVar.a(k);
            eVar.a(this.a.a());
        }
        eVar.a(l);
        eVar.a(this.b);
        eVar.a(m);
        eVar.a(this.c);
        if (this.d != null) {
            eVar.a(n);
            eVar.a(this.d);
        }
        if (this.e != null && i()) {
            eVar.a(o);
            eVar.a(this.e);
        }
        if (this.f != null && k()) {
            eVar.a(p);
            eVar.a(this.f);
        }
        if (this.g != null) {
            eVar.a(q);
            this.g.b(eVar);
        }
        if (this.h != null && n()) {
            eVar.a(r);
            this.h.b(eVar);
        }
        eVar.a();
    }

    public void b(boolean z) {
        this.s.set(0, z);
    }

    public boolean b() {
        return this.a != null;
    }

    public af c(boolean z) {
        this.c = z;
        d(true);
        return this;
    }

    public boolean c() {
        return this.b;
    }

    public void d(boolean z) {
        this.s.set(1, z);
    }

    public boolean d() {
        return this.s.get(0);
    }

    public boolean e() {
        return this.s.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof af)) {
            return a((af) obj);
        }
        return false;
    }

    public byte[] f() {
        a(org.apache.thrift.b.a(this.d));
        return this.d.array();
    }

    public boolean g() {
        return this.d != null;
    }

    public String h() {
        return this.e;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.e != null;
    }

    public String j() {
        return this.f;
    }

    public boolean k() {
        return this.f != null;
    }

    public boolean l() {
        return this.g != null;
    }

    public u m() {
        return this.h;
    }

    public boolean n() {
        return this.h != null;
    }

    public void o() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder("Required field 'action' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.d == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'pushAction' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        } else if (this.g == null) {
            StringBuilder sb3 = new StringBuilder("Required field 'target' was not present! Struct: ");
            sb3.append(toString());
            throw new f(sb3.toString());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionContainer(");
        sb.append("action:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(", ");
        sb.append("encryptAction:");
        sb.append(this.b);
        sb.append(", ");
        sb.append("isRequest:");
        sb.append(this.c);
        sb.append(", ");
        sb.append("pushAction:");
        if (this.d == null) {
            sb.append("null");
        } else {
            org.apache.thrift.b.a(this.d, sb);
        }
        if (i()) {
            sb.append(", ");
            sb.append("appid:");
            sb.append(this.e == null ? "null" : this.e);
        }
        if (k()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.f == null ? "null" : this.f);
        }
        sb.append(", ");
        sb.append("target:");
        if (this.g == null) {
            sb.append("null");
        } else {
            sb.append(this.g);
        }
        if (n()) {
            sb.append(", ");
            sb.append("metaInfo:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
