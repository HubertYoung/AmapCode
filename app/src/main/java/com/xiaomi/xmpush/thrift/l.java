package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
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
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.j;

public class l implements Serializable, Cloneable, org.apache.thrift.a<l, a> {
    public static final Map<a, b> e;
    private static final j f = new j("GPS");
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("location", ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, 1);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("provider", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 2);
    private static final org.apache.thrift.protocol.b i = new org.apache.thrift.protocol.b("period", 10, 3);
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b(CameraControllerManager.MY_POILOCATION_ACR, 4, 4);
    public o a;
    public String b;
    public long c;
    public double d;
    private BitSet k = new BitSet(2);

    public enum a {
        LOCATION(1, "location"),
        PROVIDER(2, "provider"),
        PERIOD(3, "period"),
        ACCURACY(4, CameraControllerManager.MY_POILOCATION_ACR);
        
        private static final Map<String, a> e = null;
        private final short f;
        private final String g;

        static {
            e = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                e.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.f = s;
            this.g = str;
        }

        public final String a() {
            return this.g;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.LOCATION, new b("location", 1, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, o.class)));
        enumMap.put(a.PROVIDER, new b("provider", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.PERIOD, new b("period", 2, new c(10)));
        enumMap.put(a.ACCURACY, new b(CameraControllerManager.MY_POILOCATION_ACR, 2, new c(4)));
        e = Collections.unmodifiableMap(enumMap);
        b.a(l.class, e);
    }

    public l a(double d2) {
        this.d = d2;
        b(true);
        return this;
    }

    public l a(long j2) {
        this.c = j2;
        a(true);
        return this;
    }

    public l a(o oVar) {
        this.a = oVar;
        return this;
    }

    public l a(String str) {
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
            r2 = 1
            switch(r1) {
                case 1: goto L_0x0040;
                case 2: goto L_0x0033;
                case 3: goto L_0x0023;
                case 4: goto L_0x0014;
                default: goto L_0x000e;
            }
        L_0x000e:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0014:
            byte r1 = r0.b
            r3 = 4
            if (r1 != r3) goto L_0x000e
            double r0 = r5.k()
            r4.d = r0
            r4.b(r2)
            goto L_0x0000
        L_0x0023:
            byte r1 = r0.b
            r3 = 10
            if (r1 != r3) goto L_0x000e
            long r0 = r5.j()
            r4.c = r0
            r4.a(r2)
            goto L_0x0000
        L_0x0033:
            byte r1 = r0.b
            r2 = 11
            if (r1 != r2) goto L_0x000e
            java.lang.String r0 = r5.l()
            r4.b = r0
            goto L_0x0000
        L_0x0040:
            byte r1 = r0.b
            r2 = 12
            if (r1 != r2) goto L_0x000e
            com.xiaomi.xmpush.thrift.o r0 = new com.xiaomi.xmpush.thrift.o
            r0.<init>()
            r4.a = r0
            com.xiaomi.xmpush.thrift.o r0 = r4.a
            r0.a(r5)
            goto L_0x0000
        L_0x0053:
            r4.e()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.l.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.k.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(l lVar) {
        if (lVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = lVar.a();
        if ((a2 || a3) && (!a2 || !a3 || !this.a.a(lVar.a))) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = lVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.equals(lVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = lVar.c();
        if ((c2 || c3) && (!c2 || !c3 || this.c != lVar.c)) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = lVar.d();
        return (!d2 && !d3) || (d2 && d3 && this.d == lVar.d);
    }

    /* renamed from: b */
    public int compareTo(l lVar) {
        if (!getClass().equals(lVar.getClass())) {
            return getClass().getName().compareTo(lVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(lVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a((Comparable) this.a, (Comparable) lVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(lVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a(this.b, lVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(lVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, lVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(lVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, lVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        e();
        if (this.a != null) {
            eVar.a(g);
            this.a.b(eVar);
        }
        if (this.b != null && b()) {
            eVar.a(h);
            eVar.a(this.b);
        }
        if (c()) {
            eVar.a(i);
            eVar.a(this.c);
        }
        if (d()) {
            eVar.a(j);
            eVar.a(this.d);
        }
        eVar.a();
    }

    public void b(boolean z) {
        this.k.set(1, z);
    }

    public boolean b() {
        return this.b != null;
    }

    public boolean c() {
        return this.k.get(0);
    }

    public boolean d() {
        return this.k.get(1);
    }

    public void e() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder("Required field 'location' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof l)) {
            return a((l) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GPS(");
        sb.append("location:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        if (b()) {
            sb.append(", ");
            sb.append("provider:");
            sb.append(this.b == null ? "null" : this.b);
        }
        if (c()) {
            sb.append(", ");
            sb.append("period:");
            sb.append(this.c);
        }
        if (d()) {
            sb.append(", ");
            sb.append("accuracy:");
            sb.append(this.d);
        }
        sb.append(")");
        return sb.toString();
    }
}
