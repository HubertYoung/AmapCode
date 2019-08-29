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

public class ab implements Serializable, Cloneable, org.apache.thrift.a<ab, a> {
    public static final Map<a, b> c;
    private static final j d = new j("XmPushActionCheckClientInfo");
    private static final org.apache.thrift.protocol.b e = new org.apache.thrift.protocol.b("miscConfigVersion", 8, 1);
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("pluginConfigVersion", 8, 2);
    public int a;
    public int b;
    private BitSet g = new BitSet(2);

    public enum a {
        MISC_CONFIG_VERSION(1, "miscConfigVersion"),
        PLUGIN_CONFIG_VERSION(2, "pluginConfigVersion");
        
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
        enumMap.put(a.MISC_CONFIG_VERSION, new b("miscConfigVersion", 1, new c(8)));
        enumMap.put(a.PLUGIN_CONFIG_VERSION, new b("pluginConfigVersion", 1, new c(8)));
        c = Collections.unmodifiableMap(enumMap);
        b.a(ab.class, c);
    }

    public ab a(int i) {
        this.a = i;
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
            if (r1 == 0) goto L_0x0032
            short r1 = r0.c
            r2 = 1
            r3 = 8
            switch(r1) {
                case 1: goto L_0x0024;
                case 2: goto L_0x0016;
                default: goto L_0x0010;
            }
        L_0x0010:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0016:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0010
            int r0 = r5.i()
            r4.b = r0
            r4.b(r2)
            goto L_0x0000
        L_0x0024:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0010
            int r0 = r5.i()
            r4.a = r0
            r4.a(r2)
            goto L_0x0000
        L_0x0032:
            boolean r5 = r4.a()
            if (r5 != 0) goto L_0x0050
            org.apache.thrift.protocol.f r5 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'miscConfigVersion' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x0050:
            boolean r5 = r4.b()
            if (r5 != 0) goto L_0x006e
            org.apache.thrift.protocol.f r5 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'pluginConfigVersion' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x006e:
            r4.c()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.ab.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.g.set(0, z);
    }

    public boolean a() {
        return this.g.get(0);
    }

    public boolean a(ab abVar) {
        return abVar != null && this.a == abVar.a && this.b == abVar.b;
    }

    /* renamed from: b */
    public int compareTo(ab abVar) {
        if (!getClass().equals(abVar.getClass())) {
            return getClass().getName().compareTo(abVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(abVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, abVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(abVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a(this.b, abVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        return 0;
    }

    public ab b(int i) {
        this.b = i;
        b(true);
        return this;
    }

    public void b(e eVar) {
        c();
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
        return this.g.get(1);
    }

    public void c() {
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ab)) {
            return a((ab) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCheckClientInfo(");
        sb.append("miscConfigVersion:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("pluginConfigVersion:");
        sb.append(this.b);
        sb.append(")");
        return sb.toString();
    }
}
