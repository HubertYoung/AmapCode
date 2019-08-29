package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.d.a;
import com.google.gson.d.c;
import com.google.gson.t;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* compiled from: TypeAdapterRuntimeTypeWrapper */
final class m<T> extends t<T> {
    private final Gson a;
    private final t<T> b;
    private final Type c;

    m(Gson gson, t<T> tVar, Type type) {
        this.a = gson;
        this.b = tVar;
        this.c = type;
    }

    public final T b(a aVar) throws IOException {
        return this.b.b(aVar);
    }

    public final void a(c cVar, T t) throws IOException {
        t<T> tVar = this.b;
        Type a2 = a(this.c, (Object) t);
        if (a2 != this.c) {
            tVar = this.a.getAdapter(com.google.gson.c.a.a(a2));
            if ((tVar instanceof i.a) && !(this.b instanceof i.a)) {
                tVar = this.b;
            }
        }
        tVar.a(cVar, t);
    }

    private Type a(Type type, Object obj) {
        if (obj != null) {
            return (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type;
        }
        return type;
    }
}
