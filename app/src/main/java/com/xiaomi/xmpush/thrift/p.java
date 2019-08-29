package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
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
import org.apache.thrift.protocol.c;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.j;

public class p implements Serializable, Cloneable, org.apache.thrift.a<p, a> {
    public static final Map<a, b> d;
    private static final j e = new j("LocationInfo");
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("wifiList", 15, 1);
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("cellList", 15, 2);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b(WidgetType.GPS, ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 3);
    public List<y> a;
    public List<c> b;
    public l c;

    public enum a {
        WIFI_LIST(1, "wifiList"),
        CELL_LIST(2, "cellList"),
        GPS(3, WidgetType.GPS);
        
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
        enumMap.put(a.WIFI_LIST, new b("wifiList", 2, new d(15, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, y.class))));
        enumMap.put(a.CELL_LIST, new b("cellList", 2, new d(15, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, c.class))));
        enumMap.put(a.GPS, new b(WidgetType.GPS, 2, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, l.class)));
        d = Collections.unmodifiableMap(enumMap);
        b.a(p.class, d);
    }

    public p a(l lVar) {
        this.c = lVar;
        return this;
    }

    public p a(List<y> list) {
        this.a = list;
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r5) {
        /*
            r4 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r5.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0073
            short r1 = r0.c
            r2 = 0
            r3 = 15
            switch(r1) {
                case 1: goto L_0x004e;
                case 2: goto L_0x0029;
                case 3: goto L_0x0016;
                default: goto L_0x0010;
            }
        L_0x0010:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0016:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x0010
            com.xiaomi.xmpush.thrift.l r0 = new com.xiaomi.xmpush.thrift.l
            r0.<init>()
            r4.c = r0
            com.xiaomi.xmpush.thrift.l r0 = r4.c
            r0.a(r5)
            goto L_0x0000
        L_0x0029:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0010
            org.apache.thrift.protocol.c r0 = r5.d()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r3 = r0.b
            r1.<init>(r3)
            r4.b = r1
        L_0x003a:
            int r1 = r0.b
            if (r2 >= r1) goto L_0x0000
            com.xiaomi.xmpush.thrift.c r1 = new com.xiaomi.xmpush.thrift.c
            r1.<init>()
            r1.a(r5)
            java.util.List<com.xiaomi.xmpush.thrift.c> r3 = r4.b
            r3.add(r1)
            int r2 = r2 + 1
            goto L_0x003a
        L_0x004e:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0010
            org.apache.thrift.protocol.c r0 = r5.d()
            java.util.ArrayList r1 = new java.util.ArrayList
            int r3 = r0.b
            r1.<init>(r3)
            r4.a = r1
        L_0x005f:
            int r1 = r0.b
            if (r2 >= r1) goto L_0x0000
            com.xiaomi.xmpush.thrift.y r1 = new com.xiaomi.xmpush.thrift.y
            r1.<init>()
            r1.a(r5)
            java.util.List<com.xiaomi.xmpush.thrift.y> r3 = r4.a
            r3.add(r1)
            int r2 = r2 + 1
            goto L_0x005f
        L_0x0073:
            r4.e()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.p.a(org.apache.thrift.protocol.e):void");
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(p pVar) {
        if (pVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = pVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.equals(pVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = pVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.equals(pVar.b))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = pVar.d();
        return (!d2 && !d3) || (d2 && d3 && this.c.a(pVar.c));
    }

    /* renamed from: b */
    public int compareTo(p pVar) {
        if (!getClass().equals(pVar.getClass())) {
            return getClass().getName().compareTo(pVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(pVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a((List) this.a, (List) pVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(pVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a((List) this.b, (List) pVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(pVar.d()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (d()) {
            int a4 = org.apache.thrift.b.a((Comparable) this.c, (Comparable) pVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        return 0;
    }

    public p b(List<c> list) {
        this.b = list;
        return this;
    }

    public void b(e eVar) {
        e();
        if (this.a != null && a()) {
            eVar.a(f);
            eVar.a(new c(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.a.size()));
            for (y b2 : this.a) {
                b2.b(eVar);
            }
        }
        if (this.b != null && b()) {
            eVar.a(g);
            eVar.a(new c(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.b.size()));
            for (c b3 : this.b) {
                b3.b(eVar);
            }
        }
        if (this.c != null && d()) {
            eVar.a(h);
            this.c.b(eVar);
        }
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public l c() {
        return this.c;
    }

    public boolean d() {
        return this.c != null;
    }

    public void e() {
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof p)) {
            return a((p) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z;
        StringBuilder sb = new StringBuilder("LocationInfo(");
        if (a()) {
            sb.append("wifiList:");
            if (this.a == null) {
                sb.append("null");
            } else {
                sb.append(this.a);
            }
            z = false;
        } else {
            z = true;
        }
        if (b()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("cellList:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
            z = false;
        }
        if (d()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("gps:");
            if (this.c == null) {
                sb.append("null");
            } else {
                sb.append(this.c);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
