package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.b.c;
import com.google.gson.b.i;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/* compiled from: CollectionTypeAdapterFactory */
public final class b implements u {
    private final c a;

    /* compiled from: CollectionTypeAdapterFactory */
    static final class a<E> extends t<Collection<E>> {
        private final t<E> a;
        private final i<? extends Collection<E>> b;

        public a(Gson gson, Type type, t<E> tVar, i<? extends Collection<E>> iVar) {
            this.a = new m(gson, tVar, type);
            this.b = iVar;
        }

        /* renamed from: a */
        public final Collection<E> b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == com.google.gson.d.b.NULL) {
                aVar.j();
                return null;
            }
            Collection<E> collection = (Collection) this.b.a();
            aVar.a();
            while (aVar.e()) {
                collection.add(this.a.b(aVar));
            }
            aVar.b();
            return collection;
        }

        public final void a(com.google.gson.d.c cVar, Collection<E> collection) throws IOException {
            if (collection == null) {
                cVar.f();
                return;
            }
            cVar.b();
            for (E a2 : collection) {
                this.a.a(cVar, a2);
            }
            cVar.c();
        }
    }

    public b(c cVar) {
        this.a = cVar;
    }

    public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
        Type b = aVar.b();
        Class<? super T> a2 = aVar.a();
        if (!Collection.class.isAssignableFrom(a2)) {
            return null;
        }
        Type a3 = com.google.gson.b.b.a(b, a2);
        return new a(gson, a3, gson.getAdapter(com.google.gson.c.a.a(a3)), this.a.a(aVar));
    }
}
