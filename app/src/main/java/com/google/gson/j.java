package com.google.gson;

import com.google.gson.b.l;
import com.google.gson.d.c;
import java.io.IOException;
import java.io.StringWriter;

/* compiled from: JsonElement */
public abstract class j {
    public boolean g() {
        return this instanceof g;
    }

    public boolean h() {
        return this instanceof m;
    }

    public boolean i() {
        return this instanceof o;
    }

    public boolean j() {
        return this instanceof l;
    }

    public m k() {
        if (h()) {
            return (m) this;
        }
        throw new IllegalStateException("Not a JSON Object: ".concat(String.valueOf(this)));
    }

    public g l() {
        if (g()) {
            return (g) this;
        }
        throw new IllegalStateException("Not a JSON Array: ".concat(String.valueOf(this)));
    }

    public o m() {
        if (i()) {
            return (o) this;
        }
        throw new IllegalStateException("Not a JSON Primitive: ".concat(String.valueOf(this)));
    }

    public boolean f() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    /* access modifiers changed from: 0000 */
    public Boolean n() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public Number a() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String b() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public double c() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public long d() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int e() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            c cVar = new c(stringWriter);
            cVar.b(true);
            l.a(this, cVar);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
