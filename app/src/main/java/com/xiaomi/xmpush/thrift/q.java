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

public class q implements Serializable, Cloneable, org.apache.thrift.a<q, a> {
    public static final Map<a, b> d;
    private static final j e = new j("NormalConfig");
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("version", 8, 1);
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("configItems", 15, 2);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("type", 8, 3);
    public int a;
    public List<s> b;
    public h c;
    private BitSet i = new BitSet(1);

    public enum a {
        VERSION(1, "version"),
        CONFIG_ITEMS(2, "configItems"),
        TYPE(3, "type");
        
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
        enumMap.put(a.VERSION, new b("version", 1, new c(8)));
        enumMap.put(a.CONFIG_ITEMS, new b("configItems", 1, new d(15, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, s.class))));
        enumMap.put(a.TYPE, new b("type", 1, new org.apache.thrift.meta_data.a(16, h.class)));
        d = Collections.unmodifiableMap(enumMap);
        b.a(q.class, d);
    }

    public int a() {
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
            r2 = 8
            switch(r1) {
                case 1: goto L_0x004c;
                case 2: goto L_0x0024;
                case 3: goto L_0x0015;
                default: goto L_0x000f;
            }
        L_0x000f:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0015:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            int r0 = r5.i()
            com.xiaomi.xmpush.thrift.h r0 = com.xiaomi.xmpush.thrift.h.a(r0)
            r4.c = r0
            goto L_0x0000
        L_0x0024:
            byte r1 = r0.b
            r2 = 15
            if (r1 != r2) goto L_0x000f
            org.apache.thrift.protocol.c r0 = r5.d()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.b
            r1.<init>(r2)
            r4.b = r1
            r1 = 0
        L_0x0038:
            int r2 = r0.b
            if (r1 >= r2) goto L_0x0000
            com.xiaomi.xmpush.thrift.s r2 = new com.xiaomi.xmpush.thrift.s
            r2.<init>()
            r2.a(r5)
            java.util.List<com.xiaomi.xmpush.thrift.s> r3 = r4.b
            r3.add(r2)
            int r1 = r1 + 1
            goto L_0x0038
        L_0x004c:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            int r0 = r5.i()
            r4.a = r0
            r0 = 1
            r4.a(r0)
            goto L_0x0000
        L_0x005b:
            boolean r5 = r4.b()
            if (r5 != 0) goto L_0x0079
            org.apache.thrift.protocol.f r5 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'version' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x0079:
            r4.f()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.q.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.i.set(0, z);
    }

    public boolean a(q qVar) {
        if (qVar == null || this.a != qVar.a) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = qVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.b.equals(qVar.b))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = qVar.e();
        return (!e2 && !e3) || (e2 && e3 && this.c.equals(qVar.c));
    }

    /* renamed from: b */
    public int compareTo(q qVar) {
        if (!getClass().equals(qVar.getClass())) {
            return getClass().getName().compareTo(qVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(qVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a(this.a, qVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(qVar.c()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (c()) {
            int a3 = org.apache.thrift.b.a((List) this.b, (List) qVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(qVar.e()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (e()) {
            int a4 = org.apache.thrift.b.a((Comparable) this.c, (Comparable) qVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        f();
        eVar.a(f);
        eVar.a(this.a);
        if (this.b != null) {
            eVar.a(g);
            eVar.a(new org.apache.thrift.protocol.c(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.b.size()));
            for (s b2 : this.b) {
                b2.b(eVar);
            }
        }
        if (this.c != null) {
            eVar.a(h);
            eVar.a(this.c.a());
        }
        eVar.a();
    }

    public boolean b() {
        return this.i.get(0);
    }

    public boolean c() {
        return this.b != null;
    }

    public h d() {
        return this.c;
    }

    public boolean e() {
        return this.c != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof q)) {
            return a((q) obj);
        }
        return false;
    }

    public void f() {
        if (this.b == null) {
            StringBuilder sb = new StringBuilder("Required field 'configItems' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.c == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'type' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NormalConfig(");
        sb.append("version:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("configItems:");
        if (this.b == null) {
            sb.append("null");
        } else {
            sb.append(this.b);
        }
        sb.append(", ");
        sb.append("type:");
        if (this.c == null) {
            sb.append("null");
        } else {
            sb.append(this.c);
        }
        sb.append(")");
        return sb.toString();
    }
}
