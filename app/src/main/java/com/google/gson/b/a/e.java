package com.google.gson.b.a;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.google.gson.d.a;
import com.google.gson.d.b;
import com.google.gson.g;
import com.google.gson.j;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.o;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map.Entry;

/* compiled from: JsonTreeReader */
public final class e extends a {
    private static final Reader b = new Reader() {
        public final int read(char[] cArr, int i, int i2) throws IOException {
            throw new AssertionError();
        }

        public final void close() throws IOException {
            throw new AssertionError();
        }
    };
    private static final Object c = new Object();
    private Object[] d = new Object[32];
    private int e = 0;
    private String[] f = new String[32];
    private int[] g = new int[32];

    public e(j jVar) {
        super(b);
        a((Object) jVar);
    }

    public final void a() throws IOException {
        a(b.BEGIN_ARRAY);
        a((Object) ((g) t()).iterator());
        this.g[this.e - 1] = 0;
    }

    public final void b() throws IOException {
        a(b.END_ARRAY);
        u();
        u();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public final void c() throws IOException {
        a(b.BEGIN_OBJECT);
        a((Object) ((m) t()).o().iterator());
    }

    public final void d() throws IOException {
        a(b.END_OBJECT);
        u();
        u();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public final boolean e() throws IOException {
        b f2 = f();
        return (f2 == b.END_OBJECT || f2 == b.END_ARRAY) ? false : true;
    }

    public final b f() throws IOException {
        while (this.e != 0) {
            Object t = t();
            if (t instanceof Iterator) {
                boolean z = this.d[this.e - 2] instanceof m;
                Iterator it = (Iterator) t;
                if (!it.hasNext()) {
                    return z ? b.END_OBJECT : b.END_ARRAY;
                }
                if (z) {
                    return b.NAME;
                }
                a(it.next());
            } else if (t instanceof m) {
                return b.BEGIN_OBJECT;
            } else {
                if (t instanceof g) {
                    return b.BEGIN_ARRAY;
                }
                if (t instanceof o) {
                    o oVar = (o) t;
                    if (oVar.q()) {
                        return b.STRING;
                    }
                    if (oVar.o()) {
                        return b.BOOLEAN;
                    }
                    if (oVar.p()) {
                        return b.NUMBER;
                    }
                    throw new AssertionError();
                } else if (t instanceof l) {
                    return b.NULL;
                } else {
                    if (t == c) {
                        throw new IllegalStateException("JsonReader is closed");
                    }
                    throw new AssertionError();
                }
            }
        }
        return b.END_DOCUMENT;
    }

    private Object t() {
        return this.d[this.e - 1];
    }

    private Object u() {
        Object[] objArr = this.d;
        int i = this.e - 1;
        this.e = i;
        Object obj = objArr[i];
        this.d[this.e] = null;
        return obj;
    }

    private void a(b bVar) throws IOException {
        if (f() != bVar) {
            StringBuilder sb = new StringBuilder("Expected ");
            sb.append(bVar);
            sb.append(" but was ");
            sb.append(f());
            sb.append(v());
            throw new IllegalStateException(sb.toString());
        }
    }

    public final String g() throws IOException {
        a(b.NAME);
        Entry entry = (Entry) ((Iterator) t()).next();
        String str = (String) entry.getKey();
        this.f[this.e - 1] = str;
        a(entry.getValue());
        return str;
    }

    public final String h() throws IOException {
        b f2 = f();
        if (f2 == b.STRING || f2 == b.NUMBER) {
            String b2 = ((o) u()).b();
            if (this.e > 0) {
                int[] iArr = this.g;
                int i = this.e - 1;
                iArr[i] = iArr[i] + 1;
            }
            return b2;
        }
        StringBuilder sb = new StringBuilder("Expected ");
        sb.append(b.STRING);
        sb.append(" but was ");
        sb.append(f2);
        sb.append(v());
        throw new IllegalStateException(sb.toString());
    }

    public final boolean i() throws IOException {
        a(b.BOOLEAN);
        boolean f2 = ((o) u()).f();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
        return f2;
    }

    public final void j() throws IOException {
        a(b.NULL);
        u();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public final double k() throws IOException {
        b f2 = f();
        if (f2 == b.NUMBER || f2 == b.STRING) {
            double c2 = ((o) t()).c();
            if (q() || (!Double.isNaN(c2) && !Double.isInfinite(c2))) {
                u();
                if (this.e > 0) {
                    int[] iArr = this.g;
                    int i = this.e - 1;
                    iArr[i] = iArr[i] + 1;
                }
                return c2;
            }
            throw new NumberFormatException("JSON forbids NaN and infinities: ".concat(String.valueOf(c2)));
        }
        StringBuilder sb = new StringBuilder("Expected ");
        sb.append(b.NUMBER);
        sb.append(" but was ");
        sb.append(f2);
        sb.append(v());
        throw new IllegalStateException(sb.toString());
    }

    public final long l() throws IOException {
        b f2 = f();
        if (f2 == b.NUMBER || f2 == b.STRING) {
            long d2 = ((o) t()).d();
            u();
            if (this.e > 0) {
                int[] iArr = this.g;
                int i = this.e - 1;
                iArr[i] = iArr[i] + 1;
            }
            return d2;
        }
        StringBuilder sb = new StringBuilder("Expected ");
        sb.append(b.NUMBER);
        sb.append(" but was ");
        sb.append(f2);
        sb.append(v());
        throw new IllegalStateException(sb.toString());
    }

    public final int m() throws IOException {
        b f2 = f();
        if (f2 == b.NUMBER || f2 == b.STRING) {
            int e2 = ((o) t()).e();
            u();
            if (this.e > 0) {
                int[] iArr = this.g;
                int i = this.e - 1;
                iArr[i] = iArr[i] + 1;
            }
            return e2;
        }
        StringBuilder sb = new StringBuilder("Expected ");
        sb.append(b.NUMBER);
        sb.append(" but was ");
        sb.append(f2);
        sb.append(v());
        throw new IllegalStateException(sb.toString());
    }

    public final void close() throws IOException {
        this.d = new Object[]{c};
        this.e = 1;
    }

    public final void n() throws IOException {
        if (f() == b.NAME) {
            g();
            this.f[this.e - 2] = "null";
        } else {
            u();
            if (this.e > 0) {
                this.f[this.e - 1] = "null";
            }
        }
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public final String toString() {
        return getClass().getSimpleName();
    }

    public final void o() throws IOException {
        a(b.NAME);
        Entry entry = (Entry) ((Iterator) t()).next();
        a(entry.getValue());
        a((Object) new o((String) entry.getKey()));
    }

    private void a(Object obj) {
        if (this.e == this.d.length) {
            Object[] objArr = new Object[(this.e * 2)];
            int[] iArr = new int[(this.e * 2)];
            String[] strArr = new String[(this.e * 2)];
            System.arraycopy(this.d, 0, objArr, 0, this.e);
            System.arraycopy(this.g, 0, iArr, 0, this.e);
            System.arraycopy(this.f, 0, strArr, 0, this.e);
            this.d = objArr;
            this.g = iArr;
            this.f = strArr;
        }
        Object[] objArr2 = this.d;
        int i = this.e;
        this.e = i + 1;
        objArr2[i] = obj;
    }

    public final String p() {
        StringBuilder sb = new StringBuilder("$");
        int i = 0;
        while (i < this.e) {
            if (this.d[i] instanceof g) {
                i++;
                if (this.d[i] instanceof Iterator) {
                    sb.append('[');
                    sb.append(this.g[i]);
                    sb.append(']');
                }
            } else if (this.d[i] instanceof m) {
                i++;
                if (this.d[i] instanceof Iterator) {
                    sb.append(DjangoUtils.EXTENSION_SEPARATOR);
                    if (this.f[i] != null) {
                        sb.append(this.f[i]);
                    }
                }
            }
            i++;
        }
        return sb.toString();
    }

    private String v() {
        StringBuilder sb = new StringBuilder(" at path ");
        sb.append(p());
        return sb.toString();
    }
}
