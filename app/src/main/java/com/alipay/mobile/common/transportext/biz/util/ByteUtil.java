package com.alipay.mobile.common.transportext.biz.util;

import android.text.TextUtils;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;

public class ByteUtil {
    public static String bytes2String(byte[] args) {
        if (args == null) {
            return "[]";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        int length = args.length;
        for (int i = 0; i < length; i++) {
            sb.append(args[i] + ",");
        }
        sb.append("]");
        return sb.toString();
    }

    public static byte[] seq8turn(byte[] seq) {
        byte[] temp = new byte[seq.length];
        long2Byte(temp, getLong(seq) + 1);
        for (int j = 0; j < seq.length; j++) {
            seq[j] = temp[j];
        }
        return seq;
    }

    public static long getLong(byte[] bb) {
        return ((((long) bb[0]) & 255) << 56) | ((((long) bb[1]) & 255) << 48) | ((((long) bb[2]) & 255) << 40) | ((((long) bb[3]) & 255) << 32) | ((((long) bb[4]) & 255) << 24) | ((((long) bb[5]) & 255) << 16) | ((((long) bb[6]) & 255) << 8) | ((((long) bb[7]) & 255) << 0);
    }

    public static void long2Byte(byte[] bb, long x) {
        bb[0] = (byte) ((int) (x >> 56));
        bb[1] = (byte) ((int) (x >> 48));
        bb[2] = (byte) ((int) (x >> 40));
        bb[3] = (byte) ((int) (x >> 32));
        bb[4] = (byte) ((int) (x >> 24));
        bb[5] = (byte) ((int) (x >> 16));
        bb[6] = (byte) ((int) (x >> 8));
        bb[7] = (byte) ((int) (x >> 0));
    }

    public static byte[] shortToBytes(int num) {
        return toHH((short) num);
    }

    public static byte[] int2Bytes(int num) {
        byte[] b = new byte[2];
        for (int i = 0; i < 2; i++) {
            b[i] = (byte) (num >>> (i * 8));
        }
        return b;
    }

    public static int toInt(byte[] bRefArr, int st, int ed) {
        int iOutcome = 0;
        for (int i = st; i < ed; i++) {
            iOutcome += (bRefArr[i] & 255) << (i * 8);
        }
        return iOutcome;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || TextUtils.equals(hexString, "")) {
            return null;
        }
        String hexString2 = hexString.toUpperCase();
        int length = hexString2.length() / 2;
        char[] hexChars = hexString2.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) ((charToByte(hexChars[pos]) << 4) | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) HexUtils.HEX_CHARS.indexOf(c);
    }

    public static byte[] toLH(int n) {
        return new byte[]{(byte) (n & 255), (byte) ((n >> 8) & 255), (byte) ((n >> 16) & 255), (byte) ((n >> 24) & 255)};
    }

    public static byte[] toHH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 255);
        b[2] = (byte) ((n >> 8) & 255);
        b[1] = (byte) ((n >> 16) & 255);
        b[0] = (byte) ((n >> 24) & 255);
        return b;
    }

    public static int hBytesToShort(byte[] b) {
        int s;
        int s2;
        if (b[0] >= 0) {
            s = b[0] + 0;
        } else {
            s = b[0] + 256;
        }
        int s3 = s * 256;
        if (b[1] >= 0) {
            s2 = s3 + b[1];
        } else {
            s2 = s3 + 256 + b[1];
        }
        return (short) s2;
    }

    public static byte[] toLH(short n) {
        return new byte[]{(byte) (n & 255), (byte) ((n >> 8) & 255)};
    }

    public static byte[] toHH(short n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 255);
        b[0] = (byte) ((n >> 8) & 255);
        return b;
    }

    public static byte[] intToByteArray(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }
}
