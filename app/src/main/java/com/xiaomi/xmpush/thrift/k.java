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
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class k implements Serializable, Cloneable, org.apache.thrift.a<k, a> {
    public static final Map<a, b> d;
    private static final j e = new j("DataCollectionItem");
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("collectedAt", 10, 1);
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("collectionType", 8, 2);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("content", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    public long a;
    public d b;
    public String c;
    private BitSet i = new BitSet(1);

    public enum a {
        COLLECTED_AT(1, "collectedAt"),
        COLLECTION_TYPE(2, "collectionType"),
        CONTENT(3, "content");
        
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
        enumMap.put(a.COLLECTED_AT, new b("collectedAt", 1, new c(10)));
        enumMap.put(a.COLLECTION_TYPE, new b("collectionType", 1, new org.apache.thrift.meta_data.a(16, d.class)));
        enumMap.put(a.CONTENT, new b("content", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        d = Collections.unmodifiableMap(enumMap);
        b.a(k.class, d);
    }

    public k a(long j) {
        this.a = j;
        a(true);
        return this;
    }

    public k a(d dVar) {
        this.b = dVar;
        return this;
    }

    public k a(String str) {
        this.c = str;
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r4) {
        /*
            r3 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r4.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0042
            short r1 = r0.c
            switch(r1) {
                case 1: goto L_0x0031;
                case 2: goto L_0x0020;
                case 3: goto L_0x0013;
                default: goto L_0x000d;
            }
        L_0x000d:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r4, r0)
            goto L_0x0000
        L_0x0013:
            byte r1 = r0.b
            r2 = 11
            if (r1 != r2) goto L_0x000d
            java.lang.String r0 = r4.l()
            r3.c = r0
            goto L_0x0000
        L_0x0020:
            byte r1 = r0.b
            r2 = 8
            if (r1 != r2) goto L_0x000d
            int r0 = r4.i()
            com.xiaomi.xmpush.thrift.d r0 = com.xiaomi.xmpush.thrift.d.a(r0)
            r3.b = r0
            goto L_0x0000
        L_0x0031:
            byte r1 = r0.b
            r2 = 10
            if (r1 != r2) goto L_0x000d
            long r0 = r4.j()
            r3.a = r0
            r0 = 1
            r3.a(r0)
            goto L_0x0000
        L_0x0042:
            boolean r4 = r3.a()
            if (r4 != 0) goto L_0x0060
            org.apache.thrift.protocol.f r4 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'collectedAt' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r3.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r4.<init>(r0)
            throw r4
        L_0x0060:
            r3.e()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.k.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.i.set(0, z);
    }

    public boolean a() {
        return this.i.get(0);
    }

    public boolean a(k kVar) {
        if (kVar == null || this.a != kVar.a) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = kVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.equals(kVar.b))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = kVar.d();
        return (!d2 && !d3) || (d2 && d3 && this.c.equals(kVar.c));
    }

    /* renamed from: b */
    public int compareTo(k kVar) {
        if (!getClass().equals(kVar.getClass())) {
            return getClass().getName().compareTo(kVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(kVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, kVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(kVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((Comparable) this.b, (Comparable) kVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(kVar.d()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (d()) {
            int a4 = org.apache.thrift.b.a(this.c, kVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        e();
        eVar.a(f);
        eVar.a(this.a);
        if (this.b != null) {
            eVar.a(g);
            eVar.a(this.b.a());
        }
        if (this.c != null) {
            eVar.a(h);
            eVar.a(this.c);
        }
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public String c() {
        return this.c;
    }

    public boolean d() {
        return this.c != null;
    }

    public void e() {
        if (this.b == null) {
            StringBuilder sb = new StringBuilder("Required field 'collectionType' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        } else if (this.c == null) {
            StringBuilder sb2 = new StringBuilder("Required field 'content' was not present! Struct: ");
            sb2.append(toString());
            throw new f(sb2.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof k)) {
            return a((k) obj);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DataCollectionItem(");
        sb.append("collectedAt:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("collectionType:");
        if (this.b == null) {
            sb.append("null");
        } else {
            sb.append(this.b);
        }
        sb.append(", ");
        sb.append("content:");
        sb.append(this.c == null ? "null" : this.c);
        sb.append(")");
        return sb.toString();
    }
}
