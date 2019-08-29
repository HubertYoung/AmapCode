package com.xiaomi.push.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.thrift.meta_data.b;
import org.apache.thrift.meta_data.d;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class c implements Serializable, Cloneable, org.apache.thrift.a<c, a> {
    public static final Map<a, b> d;
    private static final j e = new j("StatsEvents");
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("uuid", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("operator", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 2);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("events", 15, 3);
    public String a;
    public String b;
    public List<b> c;

    public enum a {
        UUID(1, "uuid"),
        OPERATOR(2, "operator"),
        EVENTS(3, "events");
        
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
        enumMap.put(a.UUID, new b("uuid", 1, new org.apache.thrift.meta_data.c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.OPERATOR, new b("operator", 2, new org.apache.thrift.meta_data.c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.EVENTS, new b("events", 1, new d(15, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, b.class))));
        d = Collections.unmodifiableMap(enumMap);
        b.a(c.class, d);
    }

    public c() {
    }

    public c(String str, List<b> list) {
        this();
        this.a = str;
        this.c = list;
    }

    public c a(String str) {
        this.b = str;
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r5) {
        /*
            r4 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r5.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0053
            short r1 = r0.c
            r2 = 11
            switch(r1) {
                case 1: goto L_0x0048;
                case 2: goto L_0x003d;
                case 3: goto L_0x0015;
                default: goto L_0x000f;
            }
        L_0x000f:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0015:
            byte r1 = r0.b
            r2 = 15
            if (r1 != r2) goto L_0x000f
            org.apache.thrift.protocol.c r0 = r5.d()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r2 = r0.b
            r1.<init>(r2)
            r4.c = r1
            r1 = 0
        L_0x0029:
            int r2 = r0.b
            if (r1 >= r2) goto L_0x0000
            com.xiaomi.push.thrift.b r2 = new com.xiaomi.push.thrift.b
            r2.<init>()
            r2.a(r5)
            java.util.List<com.xiaomi.push.thrift.b> r3 = r4.c
            r3.add(r2)
            int r1 = r1 + 1
            goto L_0x0029
        L_0x003d:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r5.l()
            r4.b = r0
            goto L_0x0000
        L_0x0048:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r5.l()
            r4.a = r0
            goto L_0x0000
        L_0x0053:
            r4.d()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.thrift.c.a(org.apache.thrift.protocol.e):void");
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(c cVar) {
        if (cVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = cVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(cVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = cVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.equals(cVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = cVar.c();
        return (!c2 && !c3) || (c2 && c3 && this.c.equals(cVar.c));
    }

    /* renamed from: b */
    public int compareTo(c cVar) {
        if (!getClass().equals(cVar.getClass())) {
            return getClass().getName().compareTo(cVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(cVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, cVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(cVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a(this.b, cVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(cVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a((List) this.c, (List) cVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        d();
        if (this.a != null) {
            eVar.a(f);
            eVar.a(this.a);
        }
        if (this.b != null && b()) {
            eVar.a(g);
            eVar.a(this.b);
        }
        if (this.c != null) {
            eVar.a(h);
            eVar.a(new org.apache.thrift.protocol.c(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.c.size()));
            for (b b2 : this.c) {
                b2.b(eVar);
            }
        }
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public boolean c() {
        return this.c != null;
    }

    public void d() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder("Required field 'uuid' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.c == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'events' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof c)) {
            return a((c) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvents(");
        sb.append("uuid:");
        sb.append(this.a == null ? "null" : this.a);
        if (b()) {
            sb.append(", ");
            sb.append("operator:");
            sb.append(this.b == null ? "null" : this.b);
        }
        sb.append(", ");
        sb.append("events:");
        if (this.c == null) {
            sb.append("null");
        } else {
            sb.append(this.c);
        }
        sb.append(")");
        return sb.toString();
    }
}
