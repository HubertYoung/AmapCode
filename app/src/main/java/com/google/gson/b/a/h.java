package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.c.a;
import com.google.gson.d.c;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.util.ArrayList;

/* compiled from: ObjectTypeAdapter */
public final class h extends t<Object> {
    public static final u a = new u() {
        public final <T> t<T> a(Gson gson, a<T> aVar) {
            if (aVar.a() == Object.class) {
                return new h(gson);
            }
            return null;
        }
    };
    private final Gson b;

    h(Gson gson) {
        this.b = gson;
    }

    public final Object b(com.google.gson.d.a aVar) throws IOException {
        switch (aVar.f()) {
            case BEGIN_ARRAY:
                ArrayList arrayList = new ArrayList();
                aVar.a();
                while (aVar.e()) {
                    arrayList.add(b(aVar));
                }
                aVar.b();
                return arrayList;
            case BEGIN_OBJECT:
                com.google.gson.b.h hVar = new com.google.gson.b.h();
                aVar.c();
                while (aVar.e()) {
                    hVar.put(aVar.g(), b(aVar));
                }
                aVar.d();
                return hVar;
            case STRING:
                return aVar.h();
            case NUMBER:
                return Double.valueOf(aVar.k());
            case BOOLEAN:
                return Boolean.valueOf(aVar.i());
            case NULL:
                aVar.j();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    public final void a(c cVar, Object obj) throws IOException {
        if (obj == null) {
            cVar.f();
            return;
        }
        t<?> adapter = this.b.getAdapter(obj.getClass());
        if (adapter instanceof h) {
            cVar.d();
            cVar.e();
            return;
        }
        adapter.a(cVar, obj);
    }
}
