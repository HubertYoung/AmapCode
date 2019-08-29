package com.google.protobuf.micro;

public final class a {
    public static final a a = new a(new byte[0]);
    private final byte[] b;
    private volatile int c = 0;

    private a(byte[] bArr) {
        this.b = bArr;
    }

    public static a a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static a a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return new a(bArr2);
    }

    public final int a() {
        return this.b.length;
    }

    public final byte[] b() {
        int length = this.b.length;
        byte[] bArr = new byte[length];
        System.arraycopy(this.b, 0, bArr, 0, length);
        return bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        int length = this.b.length;
        if (length != aVar.b.length) {
            return false;
        }
        byte[] bArr = this.b;
        byte[] bArr2 = aVar.b;
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = this.c;
        if (i == 0) {
            byte[] bArr = this.b;
            int length = this.b.length;
            int i2 = length;
            for (int i3 = 0; i3 < length; i3++) {
                i2 = (i2 * 31) + bArr[i3];
            }
            i = i2 == 0 ? 1 : i2;
            this.c = i;
        }
        return i;
    }
}
