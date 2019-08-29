package com.xiaomi.xmpush.thrift;

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

public class o implements Serializable, Cloneable, org.apache.thrift.a<o, a> {
    public static final Map<a, b> c;
    private static final j d = new j("Location");
    private static final org.apache.thrift.protocol.b e = new org.apache.thrift.protocol.b("longitude", 4, 1);
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("latitude", 4, 2);
    public double a;
    public double b;
    private BitSet g = new BitSet(2);

    public enum a {
        LONGITUDE(1, "longitude"),
        LATITUDE(2, "latitude");
        
        private static final Map<String, a> c = null;
        private final short d;
        private final String e;

        static {
            c = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                c.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.d = s;
            this.e = str;
        }

        public final String a() {
            return this.e;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.LONGITUDE, new b("longitude", 1, new c(4)));
        enumMap.put(a.LATITUDE, new b("latitude", 1, new c(4)));
        c = Collections.unmodifiableMap(enumMap);
        b.a(o.class, c);
    }

    public double a() {
        return this.a;
    }

    public o a(double d2) {
        this.a = d2;
        a(true);
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r5) {
        /*
            r4 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r5.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0031
            short r1 = r0.c
            r2 = 1
            r3 = 4
            switch(r1) {
                case 1: goto L_0x0023;
                case 2: goto L_0x0015;
                default: goto L_0x000f;
            }
        L_0x000f:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0015:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x000f
            double r0 = r5.k()
            r4.b = r0
            r4.b(r2)
            goto L_0x0000
        L_0x0023:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x000f
            double r0 = r5.k()
            r4.a = r0
            r4.a(r2)
            goto L_0x0000
        L_0x0031:
            boolean r5 = r4.b()
            if (r5 != 0) goto L_0x004f
            org.apache.thrift.protocol.f r5 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'longitude' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x004f:
            boolean r5 = r4.d()
            if (r5 != 0) goto L_0x006d
            org.apache.thrift.protocol.f r5 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'latitude' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x006d:
            r4.e()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.o.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.g.set(0, z);
    }

    public boolean a(o oVar) {
        return oVar != null && this.a == oVar.a && this.b == oVar.b;
    }

    /* renamed from: b */
    public int compareTo(o oVar) {
        if (!getClass().equals(oVar.getClass())) {
            return getClass().getName().compareTo(oVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(oVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a(this.a, oVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(oVar.d()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (d()) {
            int a3 = org.apache.thrift.b.a(this.b, oVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        return 0;
    }

    public o b(double d2) {
        this.b = d2;
        b(true);
        return this;
    }

    public void b(e eVar) {
        e();
        eVar.a(e);
        eVar.a(this.a);
        eVar.a(f);
        eVar.a(this.b);
        eVar.a();
    }

    public void b(boolean z) {
        this.g.set(1, z);
    }

    public boolean b() {
        return this.g.get(0);
    }

    public double c() {
        return this.b;
    }

    public boolean d() {
        return this.g.get(1);
    }

    public void e() {
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof o)) {
            return a((o) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Location(");
        sb.append("longitude:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("latitude:");
        sb.append(this.b);
        sb.append(")");
        return sb.toString();
    }
}
