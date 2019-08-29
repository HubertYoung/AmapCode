package org.eclipse.mat.collect;

public class ArrayLongCompressed {
    private static final int BIT_LENGTH = 64;
    private byte[] data;
    private byte trailingClearBits;
    private byte varyingBits;

    public ArrayLongCompressed(byte[] bArr) {
        this.data = bArr;
        this.varyingBits = this.data[0];
        this.trailingClearBits = this.data[1];
    }

    public ArrayLongCompressed(int i, int i2, int i3) {
        init(i, (64 - i2) - i3, i3);
    }

    public ArrayLongCompressed(long[] jArr) {
        this(jArr, 0, jArr.length);
    }

    public ArrayLongCompressed(long[] jArr, int i, int i2) {
        int i3;
        long j = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            j |= jArr[i + i4];
        }
        int i5 = 0;
        while (true) {
            i3 = 64 - i5;
            if ((((long) (1 << (i3 - 1))) & j) != 0 || i5 >= 64) {
                int i6 = 0;
            } else {
                i5++;
            }
        }
        int i62 = 0;
        while ((((long) (1 << i62)) & j) == 0 && i62 < i3) {
            i62++;
        }
        init(i2, i3 - i62, i62);
        for (int i7 = 0; i7 < i2; i7++) {
            set(i7, jArr[i + i7]);
        }
    }

    private void init(int i, int i2, int i3) {
        this.data = new byte[(((int) (((((long) i) * ((long) i2)) - 1) / 8)) + 2 + 1)];
        byte b = (byte) i2;
        this.data[0] = b;
        this.varyingBits = b;
        byte b2 = (byte) i3;
        this.data[1] = b2;
        this.trailingClearBits = b2;
    }

    public void set(int i, long j) {
        long j2 = j >>> this.trailingClearBits;
        long j3 = ((long) i) * ((long) this.varyingBits);
        int i2 = ((int) (j3 >>> 3)) + 2;
        int i3 = (((int) j3) & 7) + this.varyingBits;
        if (i3 > 8) {
            i3 -= 8;
            byte[] bArr = this.data;
            int i4 = i2 + 1;
            bArr[i2] = (byte) (bArr[i2] | ((byte) ((int) (j2 >>> i3))));
            while (true) {
                i2 = i4;
                if (i3 <= 8) {
                    break;
                }
                i3 -= 8;
                i4 = i2 + 1;
                this.data[i2] = (byte) ((int) (j2 >>> i3));
            }
        }
        byte[] bArr2 = this.data;
        bArr2[i2] = (byte) (((byte) ((int) (j2 << (8 - i3)))) | bArr2[i2]);
    }

    public long get(int i) {
        long j;
        long j2 = ((long) i) * ((long) this.varyingBits);
        int i2 = ((int) (j2 >>> 3)) + 2;
        byte b = ((int) j2) & 7;
        if (this.varyingBits + b > 8) {
            int i3 = i2 + 1;
            long j3 = (long) (((this.data[i2] << b) & 255) >>> b);
            int i4 = b + (this.varyingBits - 8);
            while (i4 > 8) {
                j3 = (j3 << 8) | ((long) (this.data[i3] & 255));
                i4 -= 8;
                i3++;
            }
            j = ((long) ((this.data[i3] & 255) >>> (8 - i4))) | (j3 << i4);
        } else {
            j = (long) (((this.data[i2] << b) & 255) >>> (8 - this.varyingBits));
        }
        return j << this.trailingClearBits;
    }

    public byte[] toByteArray() {
        return this.data;
    }
}
