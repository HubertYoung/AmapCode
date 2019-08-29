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
import org.apache.thrift.protocol.f;
import org.apache.thrift.protocol.h;
import org.apache.thrift.protocol.j;

public class e implements Serializable, Cloneable, org.apache.thrift.a<e, a> {
    public static final Map<a, b> b;
    private static final j c = new j("ClientUploadData");
    private static final org.apache.thrift.protocol.b d = new org.apache.thrift.protocol.b("uploadDataItems", 15, 1);
    public List<f> a;

    public enum a {
        UPLOAD_DATA_ITEMS(1, "uploadDataItems");
        
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
        enumMap.put(a.UPLOAD_DATA_ITEMS, new b("uploadDataItems", 1, new d(15, new g(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, f.class))));
        b = Collections.unmodifiableMap(enumMap);
        b.a(e.class, b);
    }

    public int a() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    public void a(f fVar) {
        if (this.a == null) {
            this.a = new ArrayList();
        }
        this.a.add(fVar);
    }

    public void a(org.apache.thrift.protocol.e eVar) {
        while (true) {
            org.apache.thrift.protocol.b b2 = eVar.b();
            if (b2.b == 0) {
                c();
                return;
            } else if (b2.c == 1 && b2.b == 15) {
                c d2 = eVar.d();
                this.a = new ArrayList(d2.b);
                for (int i = 0; i < d2.b; i++) {
                    f fVar = new f();
                    fVar.a(eVar);
                    this.a.add(fVar);
                }
            } else {
                h.a(eVar, b2.b);
            }
        }
    }

    public boolean a(e eVar) {
        if (eVar == null) {
            return false;
        }
        boolean b2 = b();
        boolean b3 = eVar.b();
        return (!b2 && !b3) || (b2 && b3 && this.a.equals(eVar.a));
    }

    /* renamed from: b */
    public int compareTo(e eVar) {
        if (!getClass().equals(eVar.getClass())) {
            return getClass().getName().compareTo(eVar.getClass().getName());
        }
        int compareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(eVar.b()));
        if (compareTo != 0) {
            return compareTo;
        }
        if (b()) {
            int a2 = org.apache.thrift.b.a((List) this.a, (List) eVar.a);
            if (a2 != 0) {
                return a2;
            }
        }
        return 0;
    }

    public void b(org.apache.thrift.protocol.e eVar) {
        c();
        if (this.a != null) {
            eVar.a(d);
            eVar.a(new c(ClientRpcPack.SYMMETRIC_ENCRYPT_3DES, this.a.size()));
            for (f b2 : this.a) {
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
            StringBuilder sb = new StringBuilder("Required field 'uploadDataItems' was not present! Struct: ");
            sb.append(toString());
            throw new f(sb.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof e)) {
            return a((e) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ClientUploadData(");
        sb.append("uploadDataItems:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(")");
        return sb.toString();
    }
}
