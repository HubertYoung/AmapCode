package org.eclipse.mat.collect;

public class ArrayIntCompressed {
    private static final int BIT_LENGTH = 32;
    private byte[] data;
    private byte trailingClearBits;
    private byte varyingBits;

    public ArrayIntCompressed(byte[] bArr) {
        this.data = bArr;
        this.varyingBits = this.data[0];
        this.trailingClearBits = this.data[1];
    }

    public ArrayIntCompressed(int i, int i2, int i3) {
        init(i, (32 - i2) - i3, i3);
    }

    public ArrayIntCompressed(int[] iArr) {
        this(iArr, 0, iArr.length);
    }

    public ArrayIntCompressed(int[] iArr, int i, int i2) {
        int i3;
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5++) {
            i4 |= iArr[i + i5];
        }
        int i6 = 0;
        while (true) {
            i3 = 32 - i6;
            if (((1 << (i3 - 1)) & i4) != 0 || i6 >= 32) {
                int i7 = 0;
            } else {
                i6++;
            }
        }
        int i72 = 0;
        while (((1 << i72) & i4) == 0 && i72 < i3) {
            i72++;
        }
        init(i2, i3 - i72, i72);
        for (int i8 = 0; i8 < i2; i8++) {
            set(i8, iArr[i + i8]);
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

    public void set(int i, int i2) {
        int i3 = i2 >>> this.trailingClearBits;
        long j = ((long) i) * ((long) this.varyingBits);
        int i4 = ((int) (j >>> 3)) + 2;
        int i5 = (((int) j) & 7) + this.varyingBits;
        if (i5 > 8) {
            i5 -= 8;
            byte[] bArr = this.data;
            int i6 = i4 + 1;
            bArr[i4] = (byte) (bArr[i4] | ((byte) (i3 >>> i5)));
            while (true) {
                i4 = i6;
                if (i5 <= 8) {
                    break;
                }
                i5 -= 8;
                i6 = i4 + 1;
                this.data[i4] = (byte) (i3 >>> i5);
            }
        }
        byte[] bArr2 = this.data;
        bArr2[i4] = (byte) (((byte) (i3 << (8 - i5))) | bArr2[i4]);
    }

    public int get(int i) {
        int i2;
        long j = ((long) i) * ((long) this.varyingBits);
        int i3 = ((int) (j >>> 3)) + 2;
        byte b = ((int) j) & 7;
        if (this.varyingBits + b > 8) {
            int i4 = i3 + 1;
            int i5 = ((this.data[i3] << b) & 255) >>> b;
            int i6 = b + (this.varyingBits - 8);
            while (i6 > 8) {
                i5 = (i5 << 8) | (this.data[i4] & 255);
                i6 -= 8;
                i4++;
            }
            i2 = (i5 << i6) | ((this.data[i4] & 255) >>> (8 - i6));
        } else {
            i2 = ((this.data[i3] << b) & 255) >>> (8 - this.varyingBits);
        }
        return i2 << this.trailingClearBits;
    }

    public byte[] toByteArray() {
        return this.data;
    }
}
