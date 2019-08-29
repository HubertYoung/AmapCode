package defpackage;

import com.google.zxing.LuminanceSource;

/* renamed from: duc reason: default package */
/* compiled from: RGBLuminanceSource */
public final class duc extends LuminanceSource {
    private final byte[] a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    public final boolean isCropSupported() {
        return true;
    }

    public duc(int i, int i2, int[] iArr) {
        super(i, i2);
        this.b = i;
        this.c = i2;
        this.d = 0;
        this.e = 0;
        this.a = new byte[(i * i2)];
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = i3 * i;
            for (int i5 = 0; i5 < i; i5++) {
                int i6 = i4 + i5;
                int i7 = iArr[i6];
                int i8 = (i7 >> 16) & 255;
                int i9 = (i7 >> 8) & 255;
                int i10 = i7 & 255;
                if (i8 == i9 && i9 == i10) {
                    this.a[i6] = (byte) i8;
                } else {
                    this.a[i6] = (byte) ((((i8 + i9) + i9) + i10) >> 2);
                }
            }
        }
    }

    private duc(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6) {
        super(i5, i6);
        if (i5 + i3 > i || i6 + i4 > i2) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
    }

    public final byte[] getRow(int i, byte[] bArr) {
        if (i < 0 || i >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: ".concat(String.valueOf(i)));
        }
        int width = getWidth();
        if (bArr == null || bArr.length < width) {
            bArr = new byte[width];
        }
        System.arraycopy(this.a, ((i + this.e) * this.b) + this.d, bArr, 0, width);
        return bArr;
    }

    public final byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        if (width == this.b && height == this.c) {
            return this.a;
        }
        int i = width * height;
        byte[] bArr = new byte[i];
        int i2 = (this.e * this.b) + this.d;
        if (width == this.b) {
            System.arraycopy(this.a, i2, bArr, 0, i);
            return bArr;
        }
        byte[] bArr2 = this.a;
        for (int i3 = 0; i3 < height; i3++) {
            System.arraycopy(bArr2, i2, bArr, i3 * width, width);
            i2 += this.b;
        }
        return bArr;
    }

    public final LuminanceSource crop(int i, int i2, int i3, int i4) {
        duc duc = new duc(this.a, this.b, this.c, this.d + i, this.e + i2, i3, i4);
        return duc;
    }
}
