package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
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

public class al implements Serializable, Cloneable, org.apache.thrift.a<al, a> {
    public static final Map<a, b> g;
    private static final j h = new j("XmPushActionSendFeedback");
    private static final org.apache.thrift.protocol.b i = new org.apache.thrift.protocol.b("debug", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b("target", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 2);
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("id", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("appId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("feedbacks", 13, 5);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("category", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 6);
    public String a;
    public x b;
    public String c;
    public String d;
    public Map<String, String> e;
    public String f;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        FEEDBACKS(5, "feedbacks"),
        CATEGORY(6, "category");
        
        private static final Map<String, a> g = null;
        private final short h;
        private final String i;

        static {
            g = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                g.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.h = s;
            this.i = str;
        }

        public final String a() {
            return this.i;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.TARGET, new b("target", 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, x.class)));
        enumMap.put(a.ID, new b("id", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.APP_ID, new b("appId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.FEEDBACKS, new b("feedbacks", 2, new e(13, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES), new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES))));
        enumMap.put(a.CATEGORY, new b("category", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        g = Collections.unmodifiableMap(enumMap);
        b.a(al.class, g);
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
            r2 = 11
            switch(r1) {
                case 1: goto L_0x0073;
                case 2: goto L_0x0060;
                case 3: goto L_0x0055;
                case 4: goto L_0x004a;
                case 5: goto L_0x0020;
                case 6: goto L_0x0015;
                default: goto L_0x000f;
            }
        L_0x000f:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r6, r0)
            goto L_0x0000
        L_0x0015:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r6.l()
            r5.f = r0
            goto L_0x0000
        L_0x0020:
            byte r1 = r0.b
            r2 = 13
            if (r1 != r2) goto L_0x000f
            org.apache.thrift.protocol.d r0 = r6.c()
            java.util.HashMap r1 = new java.util.HashMap
            int r2 = r0.c
            int r2 = r2 * 2
            r1.<init>(r2)
            r5.e = r1
            r1 = 0
        L_0x0036:
            int r2 = r0.c
            if (r1 >= r2) goto L_0x0000
            java.lang.String r2 = r6.l()
            java.lang.String r3 = r6.l()
            java.util.Map<java.lang.String, java.lang.String> r4 = r5.e
            r4.put(r2, r3)
            int r1 = r1 + 1
            goto L_0x0036
        L_0x004a:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r6.l()
            r5.d = r0
            goto L_0x0000
        L_0x0055:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r6.l()
            r5.c = r0
            goto L_0x0000
        L_0x0060:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x000f
            com.xiaomi.xmpush.thrift.x r0 = new com.xiaomi.xmpush.thrift.x
            r0.<init>()
            r5.b = r0
            com.xiaomi.xmpush.thrift.x r0 = r5.b
            r0.a(r6)
            goto L_0x0000
        L_0x0073:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r6.l()
            r5.a = r0
            goto L_0x0000
        L_0x007e:
            r5.g()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.al.a(org.apache.thrift.protocol.e):void");
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(al alVar) {
        if (alVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = alVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(alVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = alVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.a(alVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = alVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.c.equals(alVar.c))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = alVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.d.equals(alVar.d))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = alVar.e();
        if ((e2 || e3) && (!e2 || !e3 || !this.e.equals(alVar.e))) {
            return false;
        }
        boolean f2 = f();
        boolean f3 = alVar.f();
        return (!f2 && !f3) || (f2 && f3 && this.f.equals(alVar.f));
    }

    /* renamed from: b */
    public int compareTo(al alVar) {
        if (!getClass().equals(alVar.getClass())) {
            return getClass().getName().compareTo(alVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(alVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, alVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(alVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) alVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(alVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, alVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(alVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, alVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(alVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e()) {
            int a6 = org.apache.thrift.b.a((Map) this.e, (Map) alVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        int compareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(alVar.f()));
        if (compareTo6 != 0) {
            return compareTo6;
        }
        if (f()) {
            int a7 = org.apache.thrift.b.a(this.f, alVar.f);
            if (a7 != 0) {
                return a7;
            }
        }
        return 0;
    }

    public void b(org.apache.thrift.protocol.e eVar) {
        g();
        if (this.a != null && a()) {
            eVar.a(i);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(j);
            this.b.b(eVar);
        }
        if (this.c != null) {
            eVar.a(k);
            eVar.a(this.c);
        }
        if (this.d != null) {
            eVar.a(l);
            eVar.a(this.d);
        }
        if (this.e != null && e()) {
            eVar.a(m);
            eVar.a(new d(ClientRpcPack.SYMMETRIC_ENCRYPT_AES, ClientRpcPack.SYMMETRIC_ENCRYPT_AES, this.e.size()));
            for (Entry next : this.e.entrySet()) {
                eVar.a((String) next.getKey());
                eVar.a((String) next.getValue());
            }
        }
        if (this.f != null && f()) {
            eVar.a(n);
            eVar.a(this.f);
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
        if (obj != null && (obj instanceof al)) {
            return a((al) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f != null;
    }

    public void g() {
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

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("XmPushActionSendFeedback(");
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
            sb.append("feedbacks:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("category:");
            sb.append(this.f == null ? "null" : this.f);
        }
        sb.append(")");
        return sb.toString();
    }
}
