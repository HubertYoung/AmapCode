package com.xiaomi.xmpush.thrift;

import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;
import java.io.Serializable;
import java.util.ArrayList;
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
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.h;
import org.apache.thrift.protocol.j;

public class ag implements Serializable, Cloneable, org.apache.thrift.a<ag, a> {
    public static final Map<a, b> b;
    private static final j c = new j("XmPushActionCustomConfig");
    private static final org.apache.thrift.protocol.b d = new org.apache.thrift.protocol.b("customConfigs", 15, 1);
    public List<s> a;

    public enum a {
        CUSTOM_CONFIGS(1, "customConfigs");
        
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
        enumMap.put(a.CUSTOM_CONFIGS, new b("customConfigs", 1, new d(15, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, s.class))));
        b = Collections.unmodifiableMap(enumMap);
        b.a(ag.class, b);
    }

    public List<s> a() {
        return this.a;
    }

    public void a(e eVar) {
        while (true) {
            org.apache.thrift.protocol.b b2 = eVar.b();
            if (b2.b == 0) {
                c();
                return;
            } else if (b2.c == 1 && b2.b == 15) {
                c d2 = eVar.d();
                this.a = new ArrayList(d2.b);
                for (int i = 0; i < d2.b; i++) {
                    s sVar = new s();
                    sVar.a(eVar);
                    this.a.add(sVar);
                }
            } else {
                h.a(eVar, b2.b);
            }
        }
    }

    public boolean a(ag agVar) {
        if (agVar == null) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = agVar.b();
        return (!b2 && !b3) || (b2 && b3 && this.a.equals(agVar.a));
    }

    /* renamed from: b */
    public int compareTo(ag agVar) {
        if (!getClass().equals(agVar.getClass())) {
            return getClass().getName().compareTo(agVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(agVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a((List) this.a, (List) agVar.a);
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
            eVar.a(new c(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.a.size()));
            for (s b2 : this.a) {
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
            StringBuilder sb = new StringBuilder("Required field 'customConfigs' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ag)) {
            return a((ag) obj);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCustomConfig(");
        sb.append("customConfigs:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(")");
        return sb.toString();
    }
}
