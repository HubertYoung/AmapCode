package com.alipay.mobile.quinox.security;

import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.BitSet;

public class HexUtil {
    private HexUtil() {
    }

    public static final String bytesToHex(byte[] bArr, int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer(i2 * 2);
        bytesToHexAppend(bArr, i, i2, stringBuffer);
        return stringBuffer.toString();
    }

    public static final void bytesToHexAppend(byte[] bArr, int i, int i2, StringBuffer stringBuffer) {
        stringBuffer.ensureCapacity(stringBuffer.length() + (i2 * 2));
        int i3 = i;
        while (i3 < i + i2 && i3 < bArr.length) {
            stringBuffer.append(Character.forDigit((bArr[i3] >>> 4) & 15, 16));
            stringBuffer.append(Character.forDigit(bArr[i3] & 15, 16));
            i3++;
        }
    }

    public static final String bytesToHex(byte[] bArr) {
        return bytesToHex(bArr, 0, bArr.length);
    }

    public static final byte[] hexToBytes(String str) {
        return hexToBytes(str, 0);
    }

    public static final byte[] hexToBytes(String str, int i) {
        byte[] bArr = new byte[(((str.length() + 1) / 2) + i)];
        hexToBytes(str, bArr, i);
        return bArr;
    }

    public static final void hexToBytes(String str, byte[] bArr, int i) throws NumberFormatException, IndexOutOfBoundsException {
        int length = str.length();
        if (length % 2 != 0) {
            str = "0".concat(String.valueOf(str));
        }
        int i2 = length / 2;
        if (bArr.length < i + i2) {
            StringBuilder sb = new StringBuilder("Output buffer too small for input (");
            sb.append(bArr.length);
            sb.append(SimpleComparison.LESS_THAN_OPERATION);
            sb.append(i);
            sb.append(i2);
            sb.append(")");
            throw new IndexOutOfBoundsException(sb.toString());
        }
        for (int i3 = 0; i3 < length; i3 += 2) {
            byte digit = (byte) Character.digit(str.charAt(i3), 16);
            byte digit2 = (byte) Character.digit(str.charAt(i3 + 1), 16);
            if (digit < 0 || digit2 < 0) {
                throw new NumberFormatException();
            }
            bArr[(i3 / 2) + i] = (byte) ((digit << 4) | digit2);
        }
    }

    public static final byte[] bitsToBytes(BitSet bitSet, int i) {
        boolean z;
        int countBytesForBits = countBytesForBits(i);
        byte[] bArr = new byte[countBytesForBits];
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < countBytesForBits; i2++) {
            int i3 = 0;
            short s = 0;
            while (i3 < 8) {
                int i4 = (i2 * 8) + i3;
                if (i4 > i) {
                    z = false;
                } else {
                    z = bitSet.get(i4);
                }
                s = (short) (s | (z ? 1 << i3 : 0));
                stringBuffer.append(z ? '1' : '0');
                i3++;
            }
            if (s > 255) {
                throw new IllegalStateException("WTF? s = ".concat(String.valueOf(s)));
            }
            bArr[i2] = (byte) s;
        }
        return bArr;
    }

    public static final String bitsToHexString(BitSet bitSet, int i) {
        return bytesToHex(bitsToBytes(bitSet, i));
    }

    public static int countBytesForBits(int i) {
        return (i / 8) + (i % 8 == 0 ? 0 : 1);
    }

    public static void bytesToBits(byte[] bArr, BitSet bitSet, int i) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length) {
            int i4 = i3;
            for (int i5 = 0; i5 < 8 && i4 <= i; i5++) {
                boolean z = true;
                if (((1 << i5) & bArr[i2]) == 0) {
                    z = false;
                }
                bitSet.set(i4, z);
                i4++;
            }
            i2++;
            i3 = i4;
        }
    }

    public static void hexToBits(String str, BitSet bitSet, int i) {
        bytesToBits(hexToBytes(str), bitSet, i);
    }
}
