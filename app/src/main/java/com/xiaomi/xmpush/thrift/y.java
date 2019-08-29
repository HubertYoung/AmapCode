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

public class y implements Serializable, Cloneable, org.apache.thrift.a<y, a> {
    public static final Map<a, b> d;
    private static final j e = new j("Wifi");
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("macAddress", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 1);
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("signalStrength", 8, 2);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("ssid", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    public String a;
    public int b;
    public String c;
    private BitSet i = new BitSet(1);

    public enum a {
        MAC_ADDRESS(1, "macAddress"),
        SIGNAL_STRENGTH(2, "signalStrength"),
        SSID(3, "ssid");
        
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
        enumMap.put(a.MAC_ADDRESS, new b("macAddress", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.SIGNAL_STRENGTH, new b("signalStrength", 1, new c(8)));
        enumMap.put(a.SSID, new b("ssid", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        d = Collections.unmodifiableMap(enumMap);
        b.a(y.class, d);
    }

    public y a(int i2) {
        this.b = i2;
        a(true);
        return this;
    }

    public y a(String str) {
        this.a = str;
        return this;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r4) {
        /*
            r3 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r4.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x003c
            short r1 = r0.c
            r2 = 11
            switch(r1) {
                case 1: goto L_0x0031;
                case 2: goto L_0x0020;
                case 3: goto L_0x0015;
                default: goto L_0x000f;
            }
        L_0x000f:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r4, r0)
            goto L_0x0000
        L_0x0015:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.c = r0
            goto L_0x0000
        L_0x0020:
            byte r1 = r0.b
            r2 = 8
            if (r1 != r2) goto L_0x000f
            int r0 = r4.i()
            r3.b = r0
            r0 = 1
            r3.a(r0)
            goto L_0x0000
        L_0x0031:
            byte r1 = r0.b
            if (r1 != r2) goto L_0x000f
            java.lang.String r0 = r4.l()
            r3.a = r0
            goto L_0x0000
        L_0x003c:
            boolean r4 = r3.b()
            if (r4 != 0) goto L_0x005a
            org.apache.thrift.protocol.f r4 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'signalStrength' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r3.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r4.<init>(r0)
            throw r4
        L_0x005a:
            r3.d()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.y.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.i.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(y yVar) {
        if (yVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = yVar.a();
        if (((a2 || a3) && (!a2 || !a3 || !this.a.equals(yVar.a))) || this.b != yVar.b) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = yVar.c();
        return (!c2 && !c3) || (c2 && c3 && this.c.equals(yVar.c));
    }

    /* renamed from: b */
    public int compareTo(y yVar) {
        if (!getClass().equals(yVar.getClass())) {
            return getClass().getName().compareTo(yVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(yVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, yVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(yVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a(this.b, yVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(yVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, yVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        return 0;
    }

    public y b(String str) {
        this.c = str;
        return this;
    }

    public void b(e eVar) {
        d();
        if (this.a != null) {
            eVar.a(f);
            eVar.a(this.a);
        }
        eVar.a(g);
        eVar.a(this.b);
        if (this.c != null && c()) {
            eVar.a(h);
            eVar.a(this.c);
        }
        eVar.a();
    }

    public boolean b() {
        return this.i.get(0);
    }

    public boolean c() {
        return this.c != null;
    }

    public void d() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder("Required field 'macAddress' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof y)) {
            return a((y) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Wifi(");
        sb.append("macAddress:");
        sb.append(this.a == null ? "null" : this.a);
        sb.append(", ");
        sb.append("signalStrength:");
        sb.append(this.b);
        if (c()) {
            sb.append(", ");
            sb.append("ssid:");
            sb.append(this.c == null ? "null" : this.c);
        }
        sb.append(")");
        return sb.toString();
    }
}
