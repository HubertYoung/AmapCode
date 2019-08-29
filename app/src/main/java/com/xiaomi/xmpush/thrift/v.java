package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.thrift.meta_data.b;
import org.apache.thrift.meta_data.f;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.h;
import org.apache.thrift.protocol.i;
import org.apache.thrift.protocol.j;

public class v implements Serializable, Cloneable, org.apache.thrift.a<v, a> {
    public static final Map<a, b> b;
    private static final j c = new j("RegisteredGeoFencing");
    private static final org.apache.thrift.protocol.b d = new org.apache.thrift.protocol.b("geoFencings", 14, 1);
    public Set<m> a;

    public enum a {
        GEO_FENCINGS(1, "geoFencings");
        
        private static final Map<String, a> b = null;
        private final short c;
        private final String d;

        static {
            b = new HashMap();
            Iterator it = EnumSet.allOf(a.class).iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                b.put(aVar.a(), aVar);
            }
        }

        private a(short s, String str) {
            this.c = s;
            this.d = str;
        }

        public final String a() {
            return this.d;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.GEO_FENCINGS, new b("geoFencings", 1, new f(14, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, m.class))));
        b = Collections.unmodifiableMap(enumMap);
        b.a(v.class, b);
    }

    public v a(Set<m> set) {
        this.a = set;
        return this;
    }

    public Set<m> a() {
        return this.a;
    }

    public void a(e eVar) {
        while (true) {
            org.apache.thrift.protocol.b b2 = eVar.b();
            if (b2.b == 0) {
                c();
                return;
            } else if (b2.c == 1 && b2.b == 14) {
                i e = eVar.e();
                this.a = new HashSet(e.b * 2);
                for (int i = 0; i < e.b; i++) {
                    m mVar = new m();
                    mVar.a(eVar);
                    this.a.add(mVar);
                }
            } else {
                h.a(eVar, b2.b);
            }
        }
    }

    public boolean a(v vVar) {
        if (vVar == null) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = vVar.b();
        return (!b2 && !b3) || (b2 && b3 && this.a.equals(vVar.a));
    }

    /* renamed from: b */
    public int compareTo(v vVar) {
        if (!getClass().equals(vVar.getClass())) {
            return getClass().getName().compareTo(vVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(vVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a((Set) this.a, (Set) vVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        return 0;
    }

    public void b(e eVar) {
        c();
        if (this.a != null) {
            eVar.a(d);
            eVar.a(new i(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.a.size()));
            for (m b2 : this.a) {
                b2.b(eVar);
            }
        }
        eVar.a();
    }

    public boolean b() {
        return this.a != null;
    }

    public void c() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder("Required field 'geoFencings' was not present! Struct: ");
            sb.append(toString());
            throw new org.apache.thrift.protocol.f(sb.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof v)) {
            return a((v) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RegisteredGeoFencing(");
        sb.append("geoFencings:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(")");
        return sb.toString();
    }
}
