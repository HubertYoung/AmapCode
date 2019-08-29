package defpackage;

import java.util.Arrays;

/* renamed from: euy reason: default package */
/* compiled from: BaseNCodec */
public abstract class euy {
    private final int a;
    @Deprecated
    protected final byte b;
    protected final byte c;
    protected final int d;
    private final int e;
    private final int f;

    /* renamed from: euy$a */
    /* compiled from: BaseNCodec */
    static class a {
        int a;
        long b;
        byte[] c;
        int d;
        int e;
        boolean f;
        int g;
        int h;

        a() {
        }

        public final String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", new Object[]{getClass().getSimpleName(), Arrays.toString(this.c), Integer.valueOf(this.g), Boolean.valueOf(this.f), Integer.valueOf(this.a), Long.valueOf(this.b), Integer.valueOf(this.h), Integer.valueOf(this.d), Integer.valueOf(this.e)});
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void a(byte[] bArr, int i, int i2, a aVar);

    /* access modifiers changed from: protected */
    public abstract boolean a(byte b2);

    protected euy(int i) {
        this(0, i);
    }

    private euy(int i, int i2) {
        this.b = 61;
        this.a = 3;
        this.e = 4;
        this.d = 0;
        this.f = i2;
        this.c = 61;
    }

    private static byte[] a(a aVar) {
        if (aVar.c == null) {
            aVar.c = new byte[8192];
            aVar.d = 0;
            aVar.e = 0;
        } else {
            byte[] bArr = new byte[(aVar.c.length * 2)];
            System.arraycopy(aVar.c, 0, bArr, 0, aVar.c.length);
            aVar.c = bArr;
        }
        return aVar.c;
    }

    protected static byte[] a(int i, a aVar) {
        if (aVar.c == null || aVar.c.length < aVar.d + i) {
            return a(aVar);
        }
        return aVar.c;
    }

    /* access modifiers changed from: protected */
    public final boolean b(byte[] bArr) {
        if (bArr == null) {
            return false;
        }
        for (byte b2 : bArr) {
            if (this.c == b2 || a(b2)) {
                return true;
            }
        }
        return false;
    }

    public final long c(byte[] bArr) {
        long length = ((long) (((bArr.length + this.a) - 1) / this.a)) * ((long) this.e);
        return this.d > 0 ? length + ((((((long) this.d) + length) - 1) / ((long) this.d)) * ((long) this.f)) : length;
    }
}
