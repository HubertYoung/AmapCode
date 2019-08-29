package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.b.c;
import com.google.gson.b.f;
import com.google.gson.b.i;
import com.google.gson.b.l;
import com.google.gson.d.b;
import com.google.gson.j;
import com.google.gson.o;
import com.google.gson.r;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: MapTypeAdapterFactory */
public final class g implements u {
    final boolean a;
    private final c b;

    /* compiled from: MapTypeAdapterFactory */
    final class a<K, V> extends t<Map<K, V>> {
        private final t<K> b;
        private final t<V> c;
        private final i<? extends Map<K, V>> d;

        public a(Gson gson, Type type, t<K> tVar, Type type2, t<V> tVar2, i<? extends Map<K, V>> iVar) {
            this.b = new m(gson, tVar, type);
            this.c = new m(gson, tVar2, type2);
            this.d = iVar;
        }

        /* renamed from: a */
        public final Map<K, V> b(com.google.gson.d.a aVar) throws IOException {
            b f = aVar.f();
            if (f == b.NULL) {
                aVar.j();
                return null;
            }
            Map<K, V> map = (Map) this.d.a();
            if (f == b.BEGIN_ARRAY) {
                aVar.a();
                while (aVar.e()) {
                    aVar.a();
                    Object b2 = this.b.b(aVar);
                    if (map.put(b2, this.c.b(aVar)) != null) {
                        throw new r("duplicate key: ".concat(String.valueOf(b2)));
                    }
                    aVar.b();
                }
                aVar.b();
            } else {
                aVar.c();
                while (aVar.e()) {
                    f.a.a(aVar);
                    Object b3 = this.b.b(aVar);
                    if (map.put(b3, this.c.b(aVar)) != null) {
                        throw new r("duplicate key: ".concat(String.valueOf(b3)));
                    }
                }
                aVar.d();
            }
            return map;
        }

        public final void a(com.google.gson.d.c cVar, Map<K, V> map) throws IOException {
            if (map == null) {
                cVar.f();
            } else if (!g.this.a) {
                cVar.d();
                for (Entry next : map.entrySet()) {
                    cVar.a(String.valueOf(next.getKey()));
                    this.c.a(cVar, next.getValue());
                }
                cVar.e();
            } else {
                ArrayList arrayList = new ArrayList(map.size());
                ArrayList arrayList2 = new ArrayList(map.size());
                int i = 0;
                boolean z = false;
                for (Entry next2 : map.entrySet()) {
                    j a2 = this.b.a(next2.getKey());
                    arrayList.add(a2);
                    arrayList2.add(next2.getValue());
                    z |= a2.g() || a2.h();
                }
                if (z) {
                    cVar.b();
                    int size = arrayList.size();
                    while (i < size) {
                        cVar.b();
                        l.a((j) arrayList.get(i), cVar);
                        this.c.a(cVar, arrayList2.get(i));
                        cVar.c();
                        i++;
                    }
                    cVar.c();
                    return;
                }
                cVar.d();
                int size2 = arrayList.size();
                while (i < size2) {
                    cVar.a(a((j) arrayList.get(i)));
                    this.c.a(cVar, arrayList2.get(i));
                    i++;
                }
                cVar.e();
            }
        }

        private String a(j jVar) {
            if (jVar.i()) {
                o m = jVar.m();
                if (m.p()) {
                    return String.valueOf(m.a());
                }
                if (m.o()) {
                    return Boolean.toString(m.f());
                }
                if (m.q()) {
                    return m.b();
                }
                throw new AssertionError();
            } else if (jVar.j()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }
    }

    public g(c cVar, boolean z) {
        this.b = cVar;
        this.a = z;
    }

    public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
        Type b2 = aVar.b();
        if (!Map.class.isAssignableFrom(aVar.a())) {
            return null;
        }
        Type[] b3 = com.google.gson.b.b.b(b2, com.google.gson.b.b.e(b2));
        Gson gson2 = gson;
        a aVar2 = new a(gson2, b3[0], a(gson, b3[0]), b3[1], gson.getAdapter(com.google.gson.c.a.a(b3[1])), this.b.a(aVar));
        return aVar2;
    }

    private t<?> a(Gson gson, Type type) {
        if (type == Boolean.TYPE || type == Boolean.class) {
            return n.f;
        }
        return gson.getAdapter(com.google.gson.c.a.a(type));
    }
}
