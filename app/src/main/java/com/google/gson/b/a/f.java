package com.google.gson.b.a;

import com.google.gson.d.c;
import com.google.gson.g;
import com.google.gson.j;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.o;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: JsonTreeWriter */
public final class f extends c {
    private static final Writer a = new Writer() {
        public final void write(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }

        public final void flush() throws IOException {
            throw new AssertionError();
        }

        public final void close() throws IOException {
            throw new AssertionError();
        }
    };
    private static final o b = new o((String) "closed");
    private final List<j> c = new ArrayList();
    private String d;
    private j e = l.a;

    public final void flush() throws IOException {
    }

    public f() {
        super(a);
    }

    public final j a() {
        if (this.c.isEmpty()) {
            return this.e;
        }
        StringBuilder sb = new StringBuilder("Expected one JSON element but was ");
        sb.append(this.c);
        throw new IllegalStateException(sb.toString());
    }

    private j j() {
        return this.c.get(this.c.size() - 1);
    }

    private void a(j jVar) {
        if (this.d != null) {
            if (!jVar.j() || i()) {
                ((m) j()).a(this.d, jVar);
            }
            this.d = null;
        } else if (this.c.isEmpty()) {
            this.e = jVar;
        } else {
            j j = j();
            if (j instanceof g) {
                ((g) j).a(jVar);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public final c b() throws IOException {
        g gVar = new g();
        a((j) gVar);
        this.c.add(gVar);
        return this;
    }

    public final c c() throws IOException {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof g) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public final c d() throws IOException {
        m mVar = new m();
        a((j) mVar);
        this.c.add(mVar);
        return this;
    }

    public final c e() throws IOException {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof m) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public final c a(String str) throws IOException {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof m) {
            this.d = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public final c b(String str) throws IOException {
        if (str == null) {
            return f();
        }
        a((j) new o(str));
        return this;
    }

    public final c f() throws IOException {
        a((j) l.a);
        return this;
    }

    public final c a(boolean z) throws IOException {
        a((j) new o(Boolean.valueOf(z)));
        return this;
    }

    public final c a(Boolean bool) throws IOException {
        if (bool == null) {
            return f();
        }
        a((j) new o(bool));
        return this;
    }

    public final c a(long j) throws IOException {
        a((j) new o((Number) Long.valueOf(j)));
        return this;
    }

    public final c a(Number number) throws IOException {
        if (number == null) {
            return f();
        }
        if (!g()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException("JSON forbids NaN and infinities: ".concat(String.valueOf(number)));
            }
        }
        a((j) new o(number));
        return this;
    }

    public final void close() throws IOException {
        if (!this.c.isEmpty()) {
            throw new IOException("Incomplete document");
        }
        this.c.add(b);
    }
}
