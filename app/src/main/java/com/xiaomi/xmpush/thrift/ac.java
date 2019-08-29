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

public class ac implements Serializable, Cloneable, org.apache.thrift.a<ac, a> {
    public static final Map<a, b> b;
    private static final j c = new j("XmPushActionCollectData");
    private static final org.apache.thrift.protocol.b d = new org.apache.thrift.protocol.b("dataCollectionItems", 15, 1);
    public List<k> a;

    public enum a {
        DATA_COLLECTION_ITEMS(1, "dataCollectionItems");
        
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
        enumMap.put(a.DATA_COLLECTION_ITEMS, new b("dataCollectionItems", 1, new d(15, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, k.class))));
        b = Collections.unmodifiableMap(enumMap);
        b.a(ac.class, b);
    }

    public ac a(List<k> list) {
        this.a = list;
        return this;
    }

    public void a(e eVar) {
        while (true) {
            org.apache.thrift.protocol.b b2 = eVar.b();
            if (b2.b == 0) {
                b();
                return;
            } else if (b2.c == 1 && b2.b == 15) {
                c d2 = eVar.d();
                this.a = new ArrayList(d2.b);
                for (int i = 0; i < d2.b; i++) {
                    k kVar = new k();
                    kVar.a(eVar);
                    this.a.add(kVar);
                }
            } else {
                h.a(eVar, b2.b);
            }
        }
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ac acVar) {
        if (acVar == null) {
            return false;
        }
        boolean a2 = a();
        boolean a3 = acVar.a();
        return (!a2 && !a3) || (a2 && a3 && this.a.equals(acVar.a));
    }

    /* renamed from: b */
    public int compareTo(ac acVar) {
        if (!getClass().equals(acVar.getClass())) {
            return getClass().getName().compareTo(acVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(acVar.a()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (a()) {
            int a2 = org.apache.thrift.b.a((List) this.a, (List) acVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        return 0;
    }

    public void b() {
        if (this.a == null) {
            StringBuilder sb = new StringBuilder("Required field 'dataCollectionItems' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public void b(e eVar) {
        b();
        if (this.a != null) {
            eVar.a(d);
            eVar.a(new c(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.a.size()));
            for (k b2 : this.a) {
                b2.b(eVar);
            }
        }
        eVar.a();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ac)) {
            return a((ac) obj);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCollectData(");
        sb.append("dataCollectionItems:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(")");
        return sb.toString();
    }
}
