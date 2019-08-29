package com.autonavi.minimap.ajx3.util;

public class ArrayUtils {
    public static Integer[] toObject(int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            return null;
        }
        Integer[] numArr = new Integer[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            numArr[i] = Integer.valueOf(iArr[i]);
        }
        return numArr;
    }

    public static Character[] toObject(char[] cArr) {
        if (cArr == null || cArr.length <= 0) {
            return null;
        }
        Character[] chArr = new Character[cArr.length];
        for (int i = 0; i < cArr.length; i++) {
            chArr[i] = Character.valueOf(cArr[i]);
        }
        return chArr;
    }

    public static Byte[] toObject(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        Byte[] bArr2 = new Byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = Byte.valueOf(bArr[i]);
        }
        return bArr2;
    }

    public static Boolean[] toObject(boolean[] zArr) {
        if (zArr == null || zArr.length <= 0) {
            return null;
        }
        Boolean[] boolArr = new Boolean[zArr.length];
        for (int i = 0; i < zArr.length; i++) {
            boolArr[i] = Boolean.valueOf(zArr[i]);
        }
        return boolArr;
    }

    public static Short[] toObject(short[] sArr) {
        if (sArr == null || sArr.length <= 0) {
            return null;
        }
        Short[] shArr = new Short[sArr.length];
        for (int i = 0; i < sArr.length; i++) {
            shArr[i] = Short.valueOf(sArr[i]);
        }
        return shArr;
    }

    public static Long[] toObject(long[] jArr) {
        if (jArr == null || jArr.length <= 0) {
            return null;
        }
        Long[] lArr = new Long[jArr.length];
        for (int i = 0; i < jArr.length; i++) {
            lArr[i] = Long.valueOf(jArr[i]);
        }
        return lArr;
    }

    public static Float[] toObject(float[] fArr) {
        if (fArr == null || fArr.length <= 0) {
            return null;
        }
        Float[] fArr2 = new Float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            fArr2[i] = Float.valueOf(fArr[i]);
        }
        return fArr2;
    }

    public static Double[] toObject(double[] dArr) {
        if (dArr == null || dArr.length <= 0) {
            return null;
        }
        Double[] dArr2 = new Double[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            dArr2[i] = Double.valueOf(dArr[i]);
        }
        return dArr2;
    }

    public static int[] toPrimary(Integer[] numArr) {
        if (numArr == null || numArr.length <= 0) {
            return null;
        }
        int[] iArr = new int[numArr.length];
        for (int i = 0; i < numArr.length; i++) {
            iArr[i] = numArr[i].intValue();
        }
        return iArr;
    }

    public static char[] toPrimary(Character[] chArr) {
        if (chArr == null || chArr.length <= 0) {
            return null;
        }
        char[] cArr = new char[chArr.length];
        for (int i = 0; i < chArr.length; i++) {
            cArr[i] = chArr[i].charValue();
        }
        return cArr;
    }

    public static byte[] toPrimary(Byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[i].byteValue();
        }
        return bArr2;
    }

    public static boolean[] toPrimary(Boolean[] boolArr) {
        if (boolArr == null || boolArr.length <= 0) {
            return null;
        }
        boolean[] zArr = new boolean[boolArr.length];
        for (int i = 0; i < boolArr.length; i++) {
            zArr[i] = boolArr[i].booleanValue();
        }
        return zArr;
    }

    public static short[] toPrimary(Short[] shArr) {
        if (shArr == null || shArr.length <= 0) {
            return null;
        }
        short[] sArr = new short[shArr.length];
        for (int i = 0; i < shArr.length; i++) {
            sArr[i] = shArr[i].shortValue();
        }
        return sArr;
    }

    public static long[] toPrimary(Long[] lArr) {
        if (lArr == null || lArr.length <= 0) {
            return null;
        }
        long[] jArr = new long[lArr.length];
        for (int i = 0; i < lArr.length; i++) {
            jArr[i] = lArr[i].longValue();
        }
        return jArr;
    }

    public static float[] toPrimary(Float[] fArr) {
        if (fArr == null || fArr.length <= 0) {
            return null;
        }
        float[] fArr2 = new float[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            fArr2[i] = fArr[i].floatValue();
        }
        return fArr2;
    }

    public static double[] toPrimary(Double[] dArr) {
        if (dArr == null || dArr.length <= 0) {
            return null;
        }
        double[] dArr2 = new double[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            dArr2[i] = dArr[i].doubleValue();
        }
        return dArr2;
    }

    public static int[] floatArray2IntArray(float[] fArr) {
        if (fArr == null || fArr.length <= 0) {
            return null;
        }
        int[] iArr = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            iArr[i] = (int) fArr[i];
        }
        return iArr;
    }

    private ArrayUtils() {
    }
}
