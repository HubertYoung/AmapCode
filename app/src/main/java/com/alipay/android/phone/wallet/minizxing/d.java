package com.alipay.android.phone.wallet.minizxing;

import java.lang.reflect.Array;

public final class d {
    private final byte[][] a;
    private final int b;
    private final int c;

    public d(int width, int height) {
        this.a = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{height, width});
        this.b = width;
        this.c = height;
    }

    public final int a() {
        return this.c;
    }

    public final int b() {
        return this.b;
    }

    public final byte a(int x, int y) {
        return this.a[y][x];
    }

    public final byte[][] c() {
        return this.a;
    }

    public final void a(int x, int y, int value) {
        this.a[y][x] = (byte) value;
    }

    public final void a(int x, int y, boolean value) {
        this.a[y][x] = (byte) (value ? 1 : 0);
    }

    public final void d() {
        for (int y = 0; y < this.c; y++) {
            for (int x = 0; x < this.b; x++) {
                this.a[y][x] = -1;
            }
        }
    }

    public final String toString() {
        StringBuilder result = new StringBuilder((this.b * 2 * this.c) + 2);
        for (int y = 0; y < this.c; y++) {
            for (int x = 0; x < this.b; x++) {
                switch (this.a[y][x]) {
                    case 0:
                        result.append(" 0");
                        break;
                    case 1:
                        result.append(" 1");
                        break;
                    default:
                        result.append("  ");
                        break;
                }
            }
            result.append(10);
        }
        return result.toString();
    }
}
