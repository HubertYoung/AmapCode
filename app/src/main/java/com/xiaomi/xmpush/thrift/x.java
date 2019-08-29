package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.logging.util.LoggingSPCache;
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

public class x implements Serializable, Cloneable, org.apache.thrift.a<x, a> {
    public static final Map<a, b> f;
    private static final j g = new j("Target");
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b(LoggingSPCache.STORAGE_CHANNELID, 10, 1);
    private static final org.apache.thrift.protocol.b i = new org.apache.thrift.protocol.b("userId", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 2);
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b("server", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 3);
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("resource", ClientRpcPack.SYMMETRIC_ENCRYPT_AES, 4);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("isPreview", 2, 5);
    public long a = 5;
    public String b;
    public String c = "xiaomi.com";
    public String d = "";
    public boolean e = false;
    private BitSet m = new BitSet(2);

    public enum a {
        CHANNEL_ID(1, LoggingSPCache.STORAGE_CHANNELID),
        USER_ID(2, "userId"),
        SERVER(3, "server"),
        RESOURCE(4, "resource"),
        IS_PREVIEW(5, "isPreview");
        
        private static final Map<String, a> f = null;
        private final short g;
        private final String h;

        static {
            f = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                f.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.g = s;
            this.h = str;
        }

        public final String a() {
            return this.h;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.CHANNEL_ID, new b(LoggingSPCache.STORAGE_CHANNELID, 1, new c(10)));
        enumMap.put(a.USER_ID, new b("userId", 1, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.SERVER, new b("server", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.RESOURCE, new b("resource", 2, new c(ClientRpcPack.SYMMETRIC_ENCRYPT_AES)));
        enumMap.put(a.IS_PREVIEW, new b("isPreview", 2, new c(2)));
        f = Collections.unmodifiableMap(enumMap);
        b.a(x.class, f);
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.thrift.protocol.e r5) {
        /*
            r4 = this;
        L_0x0000:
            org.apache.thrift.protocol.b r0 = r5.b()
            byte r1 = r0.b
            if (r1 == 0) goto L_0x0056
            short r1 = r0.c
            r2 = 1
            r3 = 11
            switch(r1) {
                case 1: goto L_0x0046;
                case 2: goto L_0x003b;
                case 3: goto L_0x0030;
                case 4: goto L_0x0025;
                case 5: goto L_0x0016;
                default: goto L_0x0010;
            }
        L_0x0010:
            byte r0 = r0.b
            org.apache.thrift.protocol.h.a(r5, r0)
            goto L_0x0000
        L_0x0016:
            byte r1 = r0.b
            r3 = 2
            if (r1 != r3) goto L_0x0010
            boolean r0 = r5.f()
            r4.e = r0
            r4.b(r2)
            goto L_0x0000
        L_0x0025:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0010
            java.lang.String r0 = r5.l()
            r4.d = r0
            goto L_0x0000
        L_0x0030:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0010
            java.lang.String r0 = r5.l()
            r4.c = r0
            goto L_0x0000
        L_0x003b:
            byte r1 = r0.b
            if (r1 != r3) goto L_0x0010
            java.lang.String r0 = r5.l()
            r4.b = r0
            goto L_0x0000
        L_0x0046:
            byte r1 = r0.b
            r3 = 10
            if (r1 != r3) goto L_0x0010
            long r0 = r5.j()
            r4.a = r0
            r4.a(r2)
            goto L_0x0000
        L_0x0056:
            boolean r5 = r4.a()
            if (r5 != 0) goto L_0x0074
            org.apache.thrift.protocol.f r5 = new org.apache.thrift.protocol.f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Required field 'channelId' was not found in serialized data! Struct: "
            r0.<init>(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x0074:
            r4.f()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.xmpush.thrift.x.a(org.apache.thrift.protocol.e):void");
    }

    public void a(boolean z) {
        this.m.set(0, z);
    }

    public boolean a() {
        return this.m.get(0);
    }

    public boolean a(x xVar) {
        if (xVar == null || this.a != xVar.a) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = xVar.b();
        if ((b2 || b3) && (!b2 || !b3 || !this.b.equals(xVar.b))) {
            return false;
        }
        boolean c2 = c();
        boolean c3 = xVar.c();
        if ((c2 || c3) && (!c2 || !c3 || !this.c.equals(xVar.c))) {
            return false;
        }
        boolean d2 = d();
        boolean d3 = xVar.d();
        if ((d2 || d3) && (!d2 || !d3 || !this.d.equals(xVar.d))) {
            return false;
        }
        boolean e2 = e();
        boolean e3 = xVar.e();
        return (!e2 && !e3) || (e2 && e3 && this.e == xVar.e);
    }

    /* renamed from: b */
    public int compareTo(x xVar) {
        if (!getClass().equals(xVar.getClass())) {
            return getClass().getName().compareTo(xVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(xVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a(this.a, xVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        int compareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(xVar.b()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        if (b()) {
            int a3 = org.apache.thrift.b.a(this.b, xVar.b);
            if (a3 != 0) {
                return a3;
            }
        }
        int compareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(xVar.c()));
        if (compareTo3 != 0) {
            return compareTo3;
        }
        if (c()) {
            int a4 = org.apache.thrift.b.a(this.c, xVar.c);
            if (a4 != 0) {
                return a4;
            }
        }
        int compareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(xVar.d()));
        if (compareTo4 != 0) {
            return compareTo4;
        }
        if (d()) {
            int a5 = org.apache.thrift.b.a(this.d, xVar.d);
            if (a5 != 0) {
                return a5;
            }
        }
        int compareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(xVar.e()));
        if (compareTo5 != 0) {
            return compareTo5;
        }
        if (e()) {
            int a6 = org.apache.thrift.b.a(this.e, xVar.e);
            if (a6 != 0) {
                return a6;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        f();
        eVar.a(h);
        eVar.a(this.a);
        if (this.b != null) {
            eVar.a(i);
            eVar.a(this.b);
        }
        if (this.c != null && c()) {
            eVar.a(j);
            eVar.a(this.c);
        }
        if (this.d != null && d()) {
            eVar.a(k);
            eVar.a(this.d);
        }
        if (e()) {
            eVar.a(l);
            eVar.a(this.e);
        }
        eVar.a();
    }

    public void b(boolean z) {
        this.m.set(1, z);
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
        return this.m.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof x)) {
            return a((x) obj);
        }
        return false;
    }

    public void f() {
        if (this.b == null) {
            StringBuilder sb = new StringBuilder("Required field 'userId' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Target(");
        sb.append("channelId:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("userId:");
        sb.append(this.b == null ? "null" : this.b);
        if (c()) {
            sb.append(", ");
            sb.append("server:");
            sb.append(this.c == null ? "null" : this.c);
        }
        if (d()) {
            sb.append(", ");
            sb.append("resource:");
            sb.append(this.d == null ? "null" : this.d);
        }
        if (e()) {
            sb.append(", ");
            sb.append("isPreview:");
            sb.append(this.e);
        }
        sb.append(")");
        return sb.toString();
    }
}
