package com.alipay.security.mobile.module.crypto;

public class ByteUtil {
    private ByteUtil() {
    }

    public static boolean initWithByte(byte[] bArr, byte b, int i, int i2) {
        if (bArr == null || bArr.length < i + i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i + i3] = b;
        }
        return true;
    }

    public static boolean appendByteArray(byte[] bArr, byte[] bArr2, int i) {
        if (bArr == null || bArr2 == null) {
            return false;
        }
        if (i >= bArr.length) {
            return true;
        }
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            int i3 = i2 + i;
            if (i3 + 1 > bArr.length) {
                break;
            }
            bArr[i3] = bArr2[i2];
        }
        return true;
    }

    public static boolean isTheSame(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (bArr == null || bArr2 == null || i3 <= 0 || bArr.length < i + i3 || bArr2.length < i2 + i3) {
            return false;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            if (bArr[i + i4] != bArr2[i2 + i4]) {
                return false;
            }
        }
        return true;
    }
}
