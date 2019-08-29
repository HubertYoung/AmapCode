package com.google.zxing;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;

public abstract class LuminanceSource {
    private final int height;
    private final int width;

    public abstract byte[] getMatrix();

    public abstract byte[] getRow(int i, byte[] bArr);

    public boolean isCropSupported() {
        return false;
    }

    public boolean isRotateSupported() {
        return false;
    }

    protected LuminanceSource(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public LuminanceSource crop(int i, int i2, int i3, int i4) {
        throw new UnsupportedOperationException("This luminance source does not support cropping.");
    }

    public LuminanceSource invert() {
        return new InvertedLuminanceSource(this);
    }

    public LuminanceSource rotateCounterClockwise() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 90 degrees.");
    }

    public LuminanceSource rotateCounterClockwise45() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 45 degrees.");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(this.height * (this.width + 1));
        byte[] bArr = new byte[this.width];
        for (int i = 0; i < this.height; i++) {
            bArr = getRow(i, bArr);
            for (int i2 = 0; i2 < this.width; i2++) {
                byte b = bArr[i2] & 255;
                char c = b < 64 ? '#' : b < 128 ? '+' : b < 192 ? DjangoUtils.EXTENSION_SEPARATOR : ' ';
                sb.append(c);
            }
            sb.append(10);
        }
        return sb.toString();
    }
}
