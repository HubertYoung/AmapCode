package com.google.gson.b;

import com.google.gson.Gson;
import com.google.gson.a;
import com.google.gson.a.e;
import com.google.gson.b;
import com.google.gson.d.c;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/* compiled from: Excluder */
public final class d implements u, Cloneable {
    public static final d a = new d();
    private double b = -1.0d;
    private int c = 136;
    private boolean d = true;
    private boolean e;
    private List<a> f = Collections.emptyList();
    private List<a> g = Collections.emptyList();

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final d clone() {
        try {
            return (d) super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
        Class<? super T> a2 = aVar.a();
        boolean a3 = a(a2);
        final boolean z = a3 || b(a2, true);
        final boolean z2 = a3 || b(a2, false);
        if (!z && !z2) {
            return null;
        }
        final Gson gson2 = gson;
        final com.google.gson.c.a<T> aVar2 = aVar;
        AnonymousClass1 r5 = new t<T>() {
            private t<T> f;

            public T b(com.google.gson.d.a aVar) throws IOException {
                if (!z2) {
                    return b().b(aVar);
                }
                aVar.n();
                return null;
            }

            public void a(c cVar, T t) throws IOException {
                if (z) {
                    cVar.f();
                } else {
                    b().a(cVar, t);
                }
            }

            private t<T> b() {
                t<T> tVar = this.f;
                if (tVar != null) {
                    return tVar;
                }
                t<T> delegateAdapter = gson2.getDelegateAdapter(d.this, aVar2);
                this.f = delegateAdapter;
                return delegateAdapter;
            }
        };
        return r5;
    }

    public final boolean a(Field field, boolean z) {
        if ((this.c & field.getModifiers()) != 0) {
            return true;
        }
        if ((this.b != -1.0d && !a((com.google.gson.a.d) field.getAnnotation(com.google.gson.a.d.class), (e) field.getAnnotation(e.class))) || field.isSynthetic()) {
            return true;
        }
        if (this.e) {
            com.google.gson.a.a aVar = (com.google.gson.a.a) field.getAnnotation(com.google.gson.a.a.class);
            if (aVar == null || (!z ? !aVar.b() : !aVar.a())) {
                return true;
            }
        }
        if ((!this.d && c(field.getType())) || b(field.getType())) {
            return true;
        }
        List<a> list = z ? this.f : this.g;
        if (!list.isEmpty()) {
            b bVar = new b(field);
            for (a a2 : list) {
                if (a2.a(bVar)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(Class<?> cls) {
        if (this.b != -1.0d && !a((com.google.gson.a.d) cls.getAnnotation(com.google.gson.a.d.class), (e) cls.getAnnotation(e.class))) {
            return true;
        }
        if ((this.d || !c(cls)) && !b(cls)) {
            return false;
        }
        return true;
    }

    public final boolean a(Class<?> cls, boolean z) {
        return a(cls) || b(cls, z);
    }

    private boolean b(Class<?> cls, boolean z) {
        for (a a2 : z ? this.f : this.g) {
            if (a2.a(cls)) {
                return true;
            }
        }
        return false;
    }

    private boolean b(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    private boolean c(Class<?> cls) {
        return cls.isMemberClass() && !d(cls);
    }

    private boolean d(Class<?> cls) {
        return (cls.getModifiers() & 8) != 0;
    }

    private boolean a(com.google.gson.a.d dVar, e eVar) {
        return a(dVar) && a(eVar);
    }

    private boolean a(com.google.gson.a.d dVar) {
        return dVar == null || dVar.a() <= this.b;
    }

    private boolean a(e eVar) {
        return eVar == null || eVar.a() > this.b;
    }
}
