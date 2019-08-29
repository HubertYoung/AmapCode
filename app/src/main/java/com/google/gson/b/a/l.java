package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.d.c;
import com.google.gson.h;
import com.google.gson.i;
import com.google.gson.j;
import com.google.gson.p;
import com.google.gson.q;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;

/* compiled from: TreeTypeAdapter */
public final class l<T> extends t<T> {
    final Gson a;
    private final q<T> b;
    private final i<T> c;
    private final com.google.gson.c.a<T> d;
    private final u e;
    private final a f = new a<>();
    private t<T> g;

    /* compiled from: TreeTypeAdapter */
    final class a implements h, p {
        private a() {
        }
    }

    public l(q<T> qVar, i<T> iVar, Gson gson, com.google.gson.c.a<T> aVar, u uVar) {
        this.b = qVar;
        this.c = iVar;
        this.a = gson;
        this.d = aVar;
        this.e = uVar;
    }

    public final T b(com.google.gson.d.a aVar) throws IOException {
        if (this.c == null) {
            return b().b(aVar);
        }
        j a2 = com.google.gson.b.l.a(aVar);
        if (a2.j()) {
            return null;
        }
        return this.c.a(a2, this.d.b(), this.f);
    }

    public final void a(c cVar, T t) throws IOException {
        if (this.b == null) {
            b().a(cVar, t);
        } else if (t == null) {
            cVar.f();
        } else {
            com.google.gson.b.l.a(this.b.a(t, this.d.b(), this.f), cVar);
        }
    }

    private t<T> b() {
        t<T> tVar = this.g;
        if (tVar != null) {
            return tVar;
        }
        t<T> delegateAdapter = this.a.getDelegateAdapter(this.e, this.d);
        this.g = delegateAdapter;
        return delegateAdapter;
    }
}
