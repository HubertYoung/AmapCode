package com.alipay.android.phone.wallet.minizxing;

import java.util.Arrays;

public final class BitMatrix implements Cloneable {
    private final int a;
    private final int b;
    private final int c;
    private final int[] d;

    public BitMatrix(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.a = width;
        this.b = height;
        this.c = (width + 31) / 32;
        this.d = new int[(this.c * height)];
    }

    private BitMatrix(int width, int height, int rowSize, int[] bits) {
        this.a = width;
        this.b = height;
        this.c = rowSize;
        this.d = bits;
    }

    public final boolean get(int x, int y) {
        return ((this.d[(this.c * y) + (x / 32)] >>> (x & 31)) & 1) != 0;
    }

    public final void set(int x, int y) {
        int offset = (this.c * y) + (x / 32);
        int[] iArr = this.d;
        iArr[offset] = iArr[offset] | (1 << (x & 31));
    }

    public final void clear() {
        int max = this.d.length;
        for (int i = 0; i < max; i++) {
            this.d[i] = 0;
        }
    }

    public final void a(int left, int top, int width, int height) {
        if (top < 0 || left < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        } else if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        } else {
            int right = left + width;
            int bottom = top + height;
            if (bottom > this.b || right > this.a) {
                throw new IllegalArgumentException("The region must fit inside the matrix");
            }
            for (int y = top; y < bottom; y++) {
                int offset = y * this.c;
                for (int x = left; x < right; x++) {
                    int[] iArr = this.d;
                    int i = (x / 32) + offset;
                    iArr[i] = iArr[i] | (1 << (x & 31));
                }
            }
        }
    }

    public final int[] getEnclosingRectangle() {
        int left = this.a;
        int top = this.b;
        int right = -1;
        int bottom = -1;
        for (int y = 0; y < this.b; y++) {
            for (int x32 = 0; x32 < this.c; x32++) {
                int theBits = this.d[(this.c * y) + x32];
                if (theBits != 0) {
                    if (y < top) {
                        top = y;
                    }
                    if (y > bottom) {
                        bottom = y;
                    }
                    if (x32 * 32 < left) {
                        int bit = 0;
                        while ((theBits << (31 - bit)) == 0) {
                            bit++;
                        }
                        if ((x32 * 32) + bit < left) {
                            left = (x32 * 32) + bit;
                        }
                    }
                    if ((x32 * 32) + 31 > right) {
                        int bit2 = 31;
                        while ((theBits >>> bit2) == 0) {
                            bit2--;
                        }
                        if ((x32 * 32) + bit2 > right) {
                            right = (x32 * 32) + bit2;
                        }
                    }
                }
            }
        }
        int width = right - left;
        int height = bottom - top;
        if (width < 0 || height < 0) {
            return null;
        }
        return new int[]{left, top, width, height};
    }

    public final int getWidth() {
        return this.a;
    }

    public final int getHeight() {
        return this.b;
    }

    public final boolean equals(Object o) {
        if (!(o instanceof BitMatrix)) {
            return false;
        }
        BitMatrix other = (BitMatrix) o;
        if (this.a == other.a && this.b == other.b && this.c == other.c && Arrays.equals(this.d, other.d)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return (((((((this.a * 31) + this.a) * 31) + this.b) * 31) + this.c) * 31) + Arrays.hashCode(this.d);
    }

    public final String toString() {
        StringBuilder result = new StringBuilder(this.b * (this.a + 1));
        for (int y = 0; y < this.b; y++) {
            for (int x = 0; x < this.a; x++) {
                result.append(get(x, y) ? "X " : "  ");
            }
            result.append(10);
        }
        return result.toString();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public BitMatrix clone() {
        return new BitMatrix(this.a, this.b, this.c, (int[]) this.d.clone());
    }
}
