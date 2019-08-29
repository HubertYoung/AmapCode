package com.autonavi.indoor.util;

import java.math.BigInteger;

public class Rsa {
    public static String decrypt(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        try {
            BigInteger modPow = bigInteger.modPow(bigInteger2, bigInteger3);
            StringBuilder sb = new StringBuilder();
            BigInteger bigInteger4 = new BigInteger("256");
            while (modPow.bitCount() > 0) {
                BigInteger[] divideAndRemainder = modPow.divideAndRemainder(bigInteger4);
                BigInteger bigInteger5 = divideAndRemainder[0];
                sb.append((char) divideAndRemainder[1].intValue());
                modPow = bigInteger5;
            }
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public static String decrypt(String str, BigInteger bigInteger, BigInteger bigInteger2) {
        try {
            return decrypt(new BigInteger(str, 16), bigInteger, bigInteger2);
        } catch (Exception unused) {
            return "";
        }
    }

    public static String encrypt(String str, BigInteger bigInteger, BigInteger bigInteger2) {
        try {
            byte[] bytes = str.getBytes();
            BigInteger bigInteger3 = new BigInteger("0");
            BigInteger bigInteger4 = new BigInteger("256");
            BigInteger bigInteger5 = new BigInteger("1");
            for (byte valueOf : bytes) {
                bigInteger3 = bigInteger3.add(new BigInteger(Integer.valueOf(valueOf).toString()).multiply(bigInteger5));
                bigInteger5 = bigInteger5.multiply(bigInteger4);
            }
            return bigInteger3.modPow(bigInteger, bigInteger2).toString(16);
        } catch (Exception unused) {
            return "";
        }
    }

    public static byte[] decrypt(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) {
        try {
            BigInteger modPow = new BigInteger(bArr).modPow(bigInteger, bigInteger2);
            byte[] bArr2 = new byte[16];
            BigInteger bigInteger3 = new BigInteger("256");
            int i = 0;
            while (modPow.bitCount() > 0 && i < 16) {
                BigInteger[] divideAndRemainder = modPow.divideAndRemainder(bigInteger3);
                BigInteger bigInteger4 = divideAndRemainder[0];
                bArr2[i] = (byte) divideAndRemainder[1].intValue();
                i++;
                modPow = bigInteger4;
            }
            return bArr2;
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] byteReverse(byte[] bArr) {
        byte[] bArr2 = new byte[(bArr.length + 1)];
        bArr2[0] = 0;
        for (int i = 1; i <= bArr.length; i++) {
            bArr2[i] = bArr[bArr.length - i];
        }
        return bArr2;
    }
}
