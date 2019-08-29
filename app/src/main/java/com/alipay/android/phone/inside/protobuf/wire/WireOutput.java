package com.alipay.android.phone.inside.protobuf.wire;

import java.io.IOException;

public final class WireOutput {
    private final byte[] a;
    private final int b;
    private int c;

    static int a(long j) {
        if ((-128 & j) == 0) {
            return 1;
        }
        if ((-16384 & j) == 0) {
            return 2;
        }
        if ((-2097152 & j) == 0) {
            return 3;
        }
        if ((-268435456 & j) == 0) {
            return 4;
        }
        if ((-34359738368L & j) == 0) {
            return 5;
        }
        if ((-4398046511104L & j) == 0) {
            return 6;
        }
        if ((-562949953421312L & j) == 0) {
            return 7;
        }
        if ((-72057594037927936L & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    static int c(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    static long d(long j) {
        return (j >> 63) ^ (j << 1);
    }

    static int g(int i) {
        return (i >> 31) ^ (i << 1);
    }

    public static int a(int i) {
        if (i >= 0) {
            return c(i);
        }
        return 10;
    }

    public static int a(int i, WireType wireType) {
        return (i << 3) | wireType.value();
    }

    private WireOutput(byte[] bArr, int i, int i2) {
        this.a = bArr;
        this.c = i;
        this.b = i + i2;
    }

    static WireOutput a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    static WireOutput a(byte[] bArr, int i, int i2) {
        return new WireOutput(bArr, i, i2);
    }

    static int b(int i) {
        return c(a(i, WireType.VARINT));
    }

    /* access modifiers changed from: 0000 */
    public final void b(byte[] bArr) throws IOException {
        int length = bArr.length;
        if (this.b - this.c >= length) {
            System.arraycopy(bArr, 0, this.a, this.c, length);
            this.c += length;
            return;
        }
        StringBuilder sb = new StringBuilder("Out of space: position=");
        sb.append(this.c);
        sb.append(", limit=");
        sb.append(this.b);
        throw new IOException(sb.toString());
    }

    /* access modifiers changed from: 0000 */
    public final void b(int i, WireType wireType) throws IOException {
        e(a(i, wireType));
    }

    /* access modifiers changed from: 0000 */
    public final void e(int i) throws IOException {
        while ((i & -128) != 0) {
            d((i & 127) | 128);
            i >>>= 7;
        }
        d(i);
    }

    /* access modifiers changed from: 0000 */
    public final void b(long j) throws IOException {
        while ((-128 & j) != 0) {
            d((((int) j) & 127) | 128);
            j >>>= 7;
        }
        d((int) j);
    }

    /* access modifiers changed from: 0000 */
    public final void f(int i) throws IOException {
        d(i & 255);
        d((i >> 8) & 255);
        d((i >> 16) & 255);
        d((i >> 24) & 255);
    }

    /* access modifiers changed from: 0000 */
    public final void c(long j) throws IOException {
        d(((int) j) & 255);
        d(((int) (j >> 8)) & 255);
        d(((int) (j >> 16)) & 255);
        d(((int) (j >> 24)) & 255);
        d(((int) (j >> 32)) & 255);
        d(((int) (j >> 40)) & 255);
        d(((int) (j >> 48)) & 255);
        d(((int) (j >> 56)) & 255);
    }

    /* access modifiers changed from: 0000 */
    public final void d(int i) throws IOException {
        byte b2 = (byte) i;
        if (this.c == this.b) {
            StringBuilder sb = new StringBuilder("Out of space: position=");
            sb.append(this.c);
            sb.append(", limit=");
            sb.append(this.b);
            throw new IOException(sb.toString());
        }
        byte[] bArr = this.a;
        int i2 = this.c;
        this.c = i2 + 1;
        bArr[i2] = b2;
    }
}
