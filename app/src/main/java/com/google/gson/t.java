package com.google.gson;

import com.google.gson.b.a.f;
import com.google.gson.d.a;
import com.google.gson.d.b;
import com.google.gson.d.c;
import java.io.IOException;

/* compiled from: TypeAdapter */
public abstract class t<T> {
    public abstract void a(c cVar, T t) throws IOException;

    public abstract T b(a aVar) throws IOException;

    public final t<T> a() {
        return new t<T>() {
            public void a(c cVar, T t) throws IOException {
                if (t == null) {
                    cVar.f();
                } else {
                    t.this.a(cVar, t);
                }
            }

            public T b(a aVar) throws IOException {
                if (aVar.f() != b.NULL) {
                    return t.this.b(aVar);
                }
                aVar.j();
                return null;
            }
        };
    }

    public final j a(T t) {
        try {
            f fVar = new f();
            a(fVar, t);
            return fVar.a();
        } catch (IOException e) {
            throw new k((Throwable) e);
        }
    }
}
