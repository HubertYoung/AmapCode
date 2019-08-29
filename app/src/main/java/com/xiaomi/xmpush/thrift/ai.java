package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.alipay.mobile.rome.longlinkservice.LongLinkMsgConstants;
import java.io.Serializable;
import java.nio.ByteBuffer;
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
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.d;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class ai implements Serializable, Cloneable, org.apache.thrift.a<ai, a> {
    public static final Map<a, b> l;
    private static final j m = new j("XmPushActionNotification");
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("type", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("requireAck", 2, 6);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b(LongLinkMsgConstants.LONGLINK_APPDATA, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 7);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("extra", 13, 8);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 9);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 10);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("binaryExtra", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 14);
    public String a;
    public x b;
    public String c;
    public String d;
    public String e;
    public boolean f;
    public String g;
    public Map<String, String> h;
    public String i;
    public String j;
    public ByteBuffer k;
    private BitSet y;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        TYPE(5, "type"),
        REQUIRE_ACK(6, "requireAck"),
        PAYLOAD(7, LongLinkMsgConstants.LONGLINK_APPDATA),
        EXTRA(8, "extra"),
        PACKAGE_NAME(9, "packageName"),
        CATEGORY(10, "category"),
        BINARY_EXTRA(14, "binaryExtra");
        
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
        enumMap.put(a.DEBUG, new b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TYPE, new b("type", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.REQUIRE_ACK, new b("requireAck", 1, new c(2)));
        enumMap.put(a.PAYLOAD, new b(LongLinkMsgConstants.LONGLINK_APPDATA, 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.EXTRA, new b("extra", 2, new e(13, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES), new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.CATEGORY, new b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.BINARY_EXTRA, new b("binaryExtra", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        l = Collections.unmodifiableMap(enumMap);
        b.a(ai.class, l);
    }

    public ai() {
        this.y = new BitSet(1);
        this.f = true;
    }

    public ai(String str, boolean z) {
        this();
        this.c = str;
        this.f = z;
        b(true);
    }

    public ai a(String str) {
        this.c = str;
        return this;
    }

    public ai a(ByteBuffer byteBuffer) {
        this.k = byteBuffer;
        return this;
    }

    public ai a(Map<String, String> map) {
        this.h = map;
        return this;
    }

    public ai a(boolean z) {
        this.f = z;
        b(true);
        return this;
    }

    public ai a(byte[] bArr) {
        a(ByteBuffer.wrap(bArr));
        return this;
    }

    public void a(String str, String str2) {
        if (this.h == null) {
            this.h = new HashMap();
        }
        this.h.put(str, str2);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r6) {
        /*
            r5 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r6.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x00c3
            short r1 = r0.c
            r2 = 14
            r3 = 11
            if (r1 == r2) goto L_0x00b7
            r2 = 2
            switch(r1) {
                case 1: goto L_0x00ab;
                case 2: goto L_0x0097;
                case 3: goto L_0x008b;
                case 4: goto L_0x007f;
                case 5: goto L_0x0074;
                case 6: goto L_0x0065;
                case 7: goto L_0x005a;
                case 8: goto L_0x0030;
                case 9: goto L_0x0025;
                case 10: goto L_0x001a;
                default: goto L_0x0014;
            }
        L_0x0014:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r6, r0)
            goto L_0x0000
        L_0x001a:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            java.lang.String r0 = r6.l()
            r5.j = r0
            goto L_0x0000
        L_0x0025:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            java.lang.String r0 = r6.l()
            r5.i = r0
            goto L_0x0000
        L_0x0030:
            byte r1 = r0.b
            r3 = 13
            if (r1 != r3) goto L_0x0014
            org.apache.thrift.protocol.d r0 = r6.c()
            java.util.HashMap r1 = new java.util.HashMap
            int r3 = r0.c
            int r3 = r3 * 2
            r1.<init>(r3)
            r5.h = r1
            r1 = 0
        L_0x0046:
            int r2 = r0.c
            if (r1 >= r2) goto L_0x0000
            java.lang.String r2 = r6.l()
            java.lang.String r3 = r6.l()
            java.util.Map<java.lang.String, java.lang.String> r4 = r5.h
            r4.put(r2, r3)
            int r1 = r1 + 1
            goto L_0x0046
        L_0x005a:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            java.lang.String r0 = r6.l()
            r5.g = r0
            goto L_0x0000
        L_0x0065:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x0014
            boolean r0 = r6.f()
            r5.f = r0
            r0 = 1
            r5.b(r0)
            goto L_0x0000
        L_0x0074:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            java.lang.String r0 = r6.l()
            r5.e = r0
            goto L_0x0000
        L_0x007f:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            java.lang.String r0 = r6.l()
            r5.d = r0
            goto L_0x0000
        L_0x008b:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            java.lang.String r0 = r6.l()
            r5.c = r0
            goto L_0x0000
        L_0x0097:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x0014
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r5.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r5.b
            r0.a(r6)
            goto L_0x0000
        L_0x00ab:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            java.lang.String r0 = r6.l()
            r5.a = r0
            goto L_0x0000
        L_0x00b7:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0014
            java.nio.ByteBuffer r0 = r6.m()
            r5.k = r0
            goto L_0x0000
        L_0x00c3:
            boolean r6 = r5.g()
            if (r6 != 0) goto L_0x00e1
            org.apache.thrift.protocol.f r6 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'requireAck' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r5.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r6.<init>(r0)
            throw r6
        L_0x00e1:
            r5.o()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.ai.a(org.apache.thrift.protocol.e):void");
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ai aiVar) {
        if (aiVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = aiVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(aiVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = aiVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(aiVar.b))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = aiVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.c.equals(aiVar.c))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = aiVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.d.equals(aiVar.d))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = aiVar.f();
        if (((f2 || f3) && (!f2 || !f3 || !this.e.equals(aiVar.e))) || this.f != aiVar.f) {
            return false;
        }
        boolean h2 = h();
        boolean h3 = aiVar.h();
        if ((h2 || h3) && (!h2 || !h3 || !this.g.equals(aiVar.g))) {
            return false;
        }
        boolean j2 = j();
        boolean j3 = aiVar.j();
        if ((j2 || j3) && (!j2 || !j3 || !this.h.equals(aiVar.h))) {
            return false;
        }
        boolean k2 = k();
        boolean k3 = aiVar.k();
        if ((k2 || k3) && (!k2 || !k3 || !this.i.equals(aiVar.i))) {
            return false;
        }
        boolean l2 = l();
        boolean l3 = aiVar.l();
        if ((l2 || l3) && (!l2 || !l3 || !this.j.equals(aiVar.j))) {
            return false;
        }
        boolean n2 = n();
        boolean n3 = aiVar.n();
        return (!n2 && !n3) || (n2 && n3 && this.k.equals(aiVar.k));
    }

    /* renamed from: b */
    public int compareTo(ai aiVar) {
        if (!getClass().equals(aiVar.getClass())) {
            return getClass().getName().compareTo(aiVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(aiVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, aiVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(aiVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) aiVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(aiVar.d()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (d()) {
            int a4 = org.apache.thrift.b.a(this.c, aiVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(aiVar.e()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (e()) {
            int a5 = org.apache.thrift.b.a(this.d, aiVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(aiVar.f()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (f()) {
            int a6 = org.apache.thrift.b.a(this.e, aiVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(aiVar.g()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (g()) {
            int a7 = org.apache.thrift.b.a(this.f, aiVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        int compareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(aiVar.h()));
        if (compareTo7 != 0) {
            return compareTo7;
        }
        if (h()) {
            int a8 = org.apache.thrift.b.a(this.g, aiVar.g);
            if (a8 != 0) {
                return a8;
            }
        }
        int compareTo8 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(aiVar.j()));
        if (compareTo8 != 0) {
            return compareTo8;
        }
        if (j()) {
            int a9 = org.apache.thrift.b.a((Map) this.h, (Map) aiVar.h);
            if (a9 != 0) {
                return a9;
            }
        }
        int compareTo9 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(aiVar.k()));
        if (compareTo9 != 0) {
            return compareTo9;
        }
        if (k()) {
            int a10 = org.apache.thrift.b.a(this.i, aiVar.i);
            if (a10 != 0) {
                return a10;
            }
        }
        int compareTo10 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(aiVar.l()));
        if (compareTo10 != 0) {
            return compareTo10;
        }
        if (l()) {
            int a11 = org.apache.thrift.b.a(this.j, aiVar.j);
            if (a11 != 0) {
                return a11;
            }
        }
        int compareTo11 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(aiVar.n()));
        if (compareTo11 != 0) {
            return compareTo11;
        }
        if (n()) {
            int a12 = org.apache.thrift.b.a((Comparable) this.k, (Comparable) aiVar.k);
            if (a12 != 0) {
                return a12;
            }
        }
        return 0;
    }

    public ai b(String str) {
        this.d = str;
        return this;
    }

    public void b(org.apache.thrift.protocol.e eVar) {
        o();
        if (this.a != null && a()) {
            eVar.a(n);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(o);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(p);
            eVar.a(this.c);
        }
        if (this.d != null && e()) {
            eVar.a(q);
            eVar.a(this.d);
        }
        if (this.e != null && f()) {
            eVar.a(r);
            eVar.a(this.e);
        }
        eVar.a(s);
        eVar.a(this.f);
        if (this.g != null && h()) {
            eVar.a(t);
            eVar.a(this.g);
        }
        if (this.h != null && j()) {
            eVar.a(u);
            eVar.a(new d(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.h.size()));
            for (Entry next : this.h.entrySet()) {
                eVar.a((String) next.getKey());
                eVar.a((String) next.getValue());
            }
        }
        if (this.i != null && k()) {
            eVar.a(v);
            eVar.a(this.i);
        }
        if (this.j != null && l()) {
            eVar.a(w);
            eVar.a(this.j);
        }
        if (this.k != null && n()) {
            eVar.a(x);
            eVar.a(this.k);
        }
        eVar.a();
    }

    public void b(boolean z) {
        this.y.set(0, z);
    }

    public boolean b() {
        return this.b != null;
    }

    public ai c(String str) {
        this.e = str;
        return this;
    }

    public String c() {
        return this.c;
    }

    public ai d(String str) {
        this.i = str;
        return this;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ai)) {
            return a((ai) obj);
        }
        return false;
    }

    public boolean f() {
        return this.e != null;
    }

    public boolean g() {
        return this.y.get(0);
    }

    public boolean h() {
        return this.g != null;
    }

    public int hashCode() {
        return 0;
    }

    public Map<String, String> i() {
        return this.h;
    }

    public boolean j() {
        return this.h != null;
    }

    public boolean k() {
        return this.i != null;
    }

    public boolean l() {
        return this.j != null;
    }

    public byte[] m() {
        a(org.apache.thrift.b.a(this.k));
        return this.k.array();
    }

    public boolean n() {
        return this.k != null;
    }

    public void o() {
        if (this.c == null) {
            StringBuilder sb = new StringBuilder("Required field 'id' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionNotification(");
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
        if (e()) {
            sb.append(", ");
            sb.append("appId:");
            sb.append(this.d == null ? "null" : this.d);
        }
        if (f()) {
            sb.append(", ");
            sb.append("type:");
            sb.append(this.e == null ? "null" : this.e);
        }
        sb.append(", ");
        sb.append("requireAck:");
        sb.append(this.f);
        if (h()) {
            sb.append(", ");
            sb.append("payload:");
            sb.append(this.g == null ? "null" : this.g);
        }
        if (j()) {
            sb.append(", ");
            sb.append("extra:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.i == null ? "null" : this.i);
        }
        if (l()) {
            sb.append(", ");
            sb.append("category:");
            sb.append(this.j == null ? "null" : this.j);
        }
        if (n()) {
            sb.append(", ");
            sb.append("binaryExtra:");
            if (this.k == null) {
                sb.append("null");
            } else {
                org.apache.thrift.b.a(this.k, sb);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
