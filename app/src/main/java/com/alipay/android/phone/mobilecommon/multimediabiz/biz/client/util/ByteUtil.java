package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util;

import android.annotation.SuppressLint;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import java.nio.ByteBuffer;
import java.util.UUID;

public class ByteUtil {
    public static byte[] shortToByteArray(short value) {
        return ByteBuffer.allocate(2).putShort(value).array();
    }

    public static byte[] intToByteArray(int value) {
        return ByteBuffer.allocate(4).putInt(value).array();
    }

    public static byte[] longToByteArray(long value) {
        return ByteBuffer.allocate(8).putLong(value).array();
    }

    public static byte[] UUIDToByteArray(UUID value) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(value.getMostSignificantBits());
        bb.putLong(value.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID byteArrayToUUID(byte[] value) {
        ByteBuffer bb = ByteBuffer.wrap(value);
        return new UUID(bb.getLong(), bb.getLong());
    }

    public static long byteArrayToLong(byte[] value) {
        long res = 0;
        int i = 0;
        while (i < 8 && i < value.length) {
            res = (res << 8) | ((long) (value[i] & 255));
            i++;
        }
        return res;
    }

    public static long byteArrayToLong(byte[] value, int offset, int length) {
        long res = 0;
        int i = 0;
        while (i < 8 && i < value.length) {
            res = (res << 8) | ((long) (value[i + offset] & 255));
            i++;
        }
        return res;
    }

    public static int byteArrayToInt(byte[] value) {
        int res = 0;
        int i = 0;
        while (i < 4 && i < value.length) {
            res = (res << 8) | (value[i] & 255);
            i++;
        }
        return res;
    }

    public static int byteArrayToInt(byte[] value, int offset, int length) {
        int res = 0;
        int i = 0;
        while (i < 4 && i < value.length) {
            res = (res << 8) | (value[i + offset] & 255);
            i++;
        }
        return res;
    }

    public static short byteArrayToShort(byte[] value) {
        short res = 0;
        int i = 0;
        while (i < 2 && i < value.length) {
            res = (short) (((short) (res << 8)) | (value[i] & 255));
            i++;
        }
        return res;
    }

    public static short byteArrayToShort(byte[] value, int offset, int length) {
        short res = 0;
        int i = 0;
        while (i < 2 && i < value.length) {
            res = (short) (((short) (res << 8)) | (value[i + offset] & 255));
            i++;
        }
        return res;
    }

    @SuppressLint({"DefaultLocale"})
    public static byte[] hexStringToByteArray(String hexStr) {
        String hexStr2 = hexStr.toUpperCase();
        int length = hexStr2.length() / 2;
        char[] hexChars = hexStr2.toCharArray();
        byte[] data = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            data[i] = (byte) ((a(hexChars[pos]) << 4) | a(hexChars[pos + 1]));
        }
        return data;
    }

    public static String byteArrayToHexString(byte[] src, int offset, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        int i = offset;
        while (i < src.length && i < length) {
            String hv = Integer.toHexString(src[i] & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
            i++;
        }
        return stringBuilder.toString();
    }

    private static byte a(char c) {
        return (byte) HexUtils.HEX_CHARS.indexOf(c);
    }
}
