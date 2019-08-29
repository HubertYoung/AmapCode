package com.google.zxing.common;

public final class BitSource {
    private int bitOffset;
    private int byteOffset;
    private final byte[] bytes;

    public BitSource(byte[] bArr) {
        this.bytes = bArr;
    }

    public final int getBitOffset() {
        return this.bitOffset;
    }

    public final int getByteOffset() {
        return this.byteOffset;
    }

    public final int readBits(int i) {
        byte b;
        if (i <= 0 || i > 32 || i > available()) {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        if (this.bitOffset > 0) {
            int i2 = 8 - this.bitOffset;
            int i3 = i < i2 ? i : i2;
            int i4 = i2 - i3;
            b = (((255 >> (8 - i3)) << i4) & this.bytes[this.byteOffset]) >> i4;
            i -= i3;
            this.bitOffset += i3;
            if (this.bitOffset == 8) {
                this.bitOffset = 0;
                this.byteOffset++;
            }
        } else {
            b = 0;
        }
        if (i <= 0) {
            return b;
        }
        while (i >= 8) {
            b = (b << 8) | (this.bytes[this.byteOffset] & 255);
            this.byteOffset++;
            i -= 8;
        }
        if (i <= 0) {
            return b;
        }
        int i5 = 8 - i;
        int i6 = (b << i) | ((((255 >> i5) << i5) & this.bytes[this.byteOffset]) >> i5);
        this.bitOffset += i;
        return i6;
    }

    public final int available() {
        return ((this.bytes.length - this.byteOffset) * 8) - this.bitOffset;
    }
}
