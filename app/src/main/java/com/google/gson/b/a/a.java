package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.b.b;
import com.google.gson.d.c;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/* compiled from: ArrayTypeAdapter */
public final class a<E> extends t<Object> {
    public static final u a = new u() {
        public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
            Type b = aVar.b();
            if (!(b instanceof GenericArrayType) && (!(b instanceof Class) || !((Class) b).isArray())) {
                return null;
            }
            Type g = b.g(b);
            return new a(gson, gson.getAdapter(com.google.gson.c.a.a(g)), b.e(g));
        }
    };
    private final Class<E> b;
    private final t<E> c;

    public a(Gson gson, t<E> tVar, Class<E> cls) {
        this.c = new m(gson, tVar, cls);
        this.b = cls;
    }

    public final Object b(com.google.gson.d.a aVar) throws IOException {
        if (aVar.f() == com.google.gson.d.b.NULL) {
            aVar.j();
            return null;
        }
        ArrayList arrayList = new ArrayList();
        aVar.a();
        while (aVar.e()) {
            arrayList.add(this.c.b(aVar));
        }
        aVar.b();
        int size = arrayList.size();
        Object newInstance = Array.newInstance(this.b, size);
        for (int i = 0; i < size; i++) {
            Array.set(newInstance, i, arrayList.get(i));
        }
        return newInstance;
    }

    public final void a(c cVar, Object obj) throws IOException {
        if (obj == null) {
            cVar.f();
            return;
        }
        cVar.b();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.c.a(cVar, Array.get(obj, i));
        }
        cVar.c();
    }
}
