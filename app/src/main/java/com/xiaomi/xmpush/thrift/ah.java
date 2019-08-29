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

public class ah implements Serializable, Cloneable, org.apache.thrift.a<ah, a> {
    public static final Map<a, b> d;
    private static final j e = new j("XmPushActionNormalConfig");
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("normalConfigs", 15, 1);
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("appId", 10, 4);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("packageName", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 5);
    public List<q> a;
    public long b;
    public String c;
    private BitSet i = new BitSet(1);

    public enum a {
        NORMAL_CONFIGS(1, "normalConfigs"),
        APP_ID(4, "appId"),
        PACKAGE_NAME(5, "packageName");
        
        private static final Map<String, a> d = null;
        private final short e;
        private final String f;

        static {
            d = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                d.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.e = s;
            this.f = str;
        }

        public final String a() {
            return this.f;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.NORMAL_CONFIGS, new b("normalConfigs", 1, new d(15, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, q.class))));
        enumMap.put(a.APP_ID, new b("appId", 2, new c(10)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        d = Collections.unmodifiableMap(enumMap);
        b.a(ah.class, d);
    }

    public List<q> a() {
        return this.a;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r5) {
        /*
            r4 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r5.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x005b
            short r1 = r0.c
            r2 = 1
            if (r1 == r2) goto L_0x0033
            switch(r1) {
                case 4: goto L_0x0023;
                case 5: goto L_0x0016;
                default: goto L_0x0010;
            }
        L_0x0010:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0016:
            byte r1 = r0.b
            r2 = 11
            if (r1 != r2) goto L_0x0010
            java.lang.String r0 = r5.l()
            r4.c = r0
            goto L_0x0000
        L_0x0023:
            byte r1 = r0.b
            r3 = 10
            if (r1 != r3) goto L_0x0010
            long r0 = r5.j()
            r4.b = r0
            r4.a(r2)
            goto L_0x0000
        L_0x0033:
            byte r1 = r0.b
            r2 = 15
            if (r1 != r2) goto L_0x0010
            org.apache.thrift.protocol.c r0 = r5.d()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.b
            r1.<init>(r2)
            r4.a = r1
            r1 = 0
        L_0x0047:
            int r2 = r0.b
            if (r1 >= r2) goto L_0x0000
            com.xiaomi.xmpush.thrift.q r2 = new com.xiaomi.xmpush.thrift.q
            r2.<init>()
            r2.a(r5)
            java.util.List<com.xiaomi.xmpush.thrift.q> r3 = r4.a
            r3.add(r2)
            int r1 = r1 + 1
            goto L_0x0047
        L_0x005b:
            r4.e()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.ah.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.i.set(0, z);
    }

    public boolean a(ah ahVar) {
        if (ahVar == null) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = ahVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.a.equals(ahVar.a))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = ahVar.c();
        if ((c2 || c3) && (!c2 || !c3 || this.b != ahVar.b)) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = ahVar.d();
        return (!d2 && !d3) || (d2 && d3 && this.c.equals(ahVar.c));
    }

    /* renamed from: b */
    public int compareTo(ah ahVar) {
        if (!getClass().equals(ahVar.getClass())) {
            return getClass().getName().compareTo(ahVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ahVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a((List) this.a, (List) ahVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ahVar.c()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (c()) {
            int a3 = org.apache.thrift.b.a(this.b, ahVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ahVar.d()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (d()) {
            int a4 = org.apache.thrift.b.a(this.c, ahVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        e();
        if (this.a != null) {
            eVar.a(f);
            eVar.a(new org.apache.thrift.protocol.c(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.a.size()));
            for (q b2 : this.a) {
                b2.b(eVar);
            }
        }
        if (c()) {
            eVar.a(g);
            eVar.a(this.b);
        }
        if (this.c != null && d()) {
            eVar.a(h);
            eVar.a(this.c);
        }
        eVar.a();
    }

    public boolean b() {
        return this.a != null;
    }

    public boolean c() {
        return this.i.get(0);
    }

    public boolean d() {
        return this.c != null;
    }

    public void e() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder("Required field 'normalConfigs' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ah)) {
            return a((ah) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionNormalConfig(");
        sb.append("normalConfigs:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        if (c()) {
            sb.append(", ");
            sb.append("appId:");
            sb.append(this.b);
        }
        if (d()) {
            sb.append(", ");
            sb.append("packageName:");
            sb.append(this.c == null ? "null" : this.c);
        }
        sb.append(")");
        return sb.toString();
    }
}
