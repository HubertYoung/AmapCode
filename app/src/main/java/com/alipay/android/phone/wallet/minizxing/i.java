package com.alipay.android.phone.wallet.minizxing;

public final class i {
    public static final i a = new i(4201, 4096, 1);
    public static final i b = new i(1033, 1024, 1);
    public static final i c = new i(67, 64, 1);
    public static final i d = new i(19, 16, 1);
    public static final i e = new i(285, 256, 0);
    public static final i f;
    public static final i g;
    public static final i h = c;
    private final int[] i;
    private final int[] j;
    private final j k;
    private final j l;
    private final int m;
    private final int n;
    private final int o;

    static {
        i iVar = new i(301, 256, 1);
        f = iVar;
        g = iVar;
    }

    private i(int primitive, int size, int b2) {
        this.n = primitive;
        this.m = size;
        this.o = b2;
        this.i = new int[size];
        this.j = new int[size];
        int x = 1;
        for (int i2 = 0; i2 < size; i2++) {
            this.i[i2] = x;
            x *= 2;
            if (x >= size) {
                x = (x ^ primitive) & (size - 1);
            }
        }
        for (int i3 = 0; i3 < size - 1; i3++) {
            this.j[this.i[i3]] = i3;
        }
        this.k = new j(this, new int[]{0});
        this.l = new j(this, new int[]{1});
    }

    /* access modifiers changed from: 0000 */
    public final j a() {
        return this.k;
    }

    /* access modifiers changed from: 0000 */
    public final j a(int degree, int coefficient) {
        if (degree < 0) {
            throw new IllegalArgumentException();
        } else if (coefficient == 0) {
            return this.k;
        } else {
            int[] coefficients = new int[(degree + 1)];
            coefficients[0] = coefficient;
            return new j(this, coefficients);
        }
    }

    static int b(int a2, int b2) {
        return a2 ^ b2;
    }

    /* access modifiers changed from: 0000 */
    public final int a(int a2) {
        return this.i[a2];
    }

    /* access modifiers changed from: 0000 */
    public final int b(int a2) {
        if (a2 != 0) {
            return this.j[a2];
        }
        throw new IllegalArgumentException();
    }

    /* access modifiers changed from: 0000 */
    public final int c(int a2) {
        if (a2 != 0) {
            return this.i[(this.m - this.j[a2]) - 1];
        }
        throw new ArithmeticException();
    }

    /* access modifiers changed from: 0000 */
    public final int c(int a2, int b2) {
        if (a2 == 0 || b2 == 0) {
            return 0;
        }
        return this.i[(this.j[a2] + this.j[b2]) % (this.m - 1)];
    }

    public final int b() {
        return this.o;
    }

    public final String toString() {
        return "GF(0x" + Integer.toHexString(this.n) + ',' + this.m + ')';
    }
}
