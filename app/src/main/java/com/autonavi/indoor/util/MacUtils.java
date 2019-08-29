package com.autonavi.indoor.util;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.indoor.entity.Beacon;
import java.nio.ByteBuffer;

public class MacUtils {
    public static String encodeBleID(Beacon beacon) {
        StringBuilder sb = new StringBuilder();
        sb.append(beacon.getUUID().replace("-", ""));
        sb.append("_");
        sb.append(DigitalTrans.algorismToHEXString(beacon.getMajor(), 4));
        sb.append("_");
        sb.append(DigitalTrans.algorismToHEXString(beacon.getMinor(), 4));
        return sb.toString();
    }

    public static String encodeWifiMac(long j) {
        return macString(j);
    }

    public static String encodeWifiMac(String str) {
        return str.replace(':', '_');
    }

    public static void writeMac2Buffer(String str, ByteBuffer byteBuffer) {
        byte[] bytes = str.getBytes();
        byteBuffer.put((byte) bytes.length);
        byteBuffer.put(bytes);
    }

    public static void writeMac2SixByteBuffer(String str, ByteBuffer byteBuffer) {
        if (str == null || str.length() == 0) {
            MapUtils.putDummyData(byteBuffer, 6);
        } else {
            byteBuffer.put(encodeMac2SixByte(encodeMacLong(str)));
        }
    }

    static long encodeMacLong(String str) {
        String replace = str.replace(":", "").replace("_", "").replace(Token.SEPARATOR, "").replace("", "");
        if (replace != null && replace.length() == 12) {
            return Long.parseLong(replace, 16);
        }
        if (L.isLogging) {
            L.d("mac=".concat(String.valueOf(replace)));
        }
        return 0;
    }

    static long encodeMacLong(byte[] bArr) {
        long j = 0;
        if (bArr == null || bArr.length < 6) {
            return 0;
        }
        for (int i = 0; i < 6; i++) {
            j = (j << 8) + ((long) (bArr[i] & 255));
        }
        return j;
    }

    static byte[] encodeMac(String str) {
        return encodeMac2SixByte(encodeMacLong(str));
    }

    static byte[] encodeMac2SixByte(long j) {
        byte[] bArr = new byte[6];
        for (int i = 5; i >= 0; i--) {
            bArr[i] = (byte) ((int) j);
            j >>= 8;
        }
        return bArr;
    }

    public static String macString(long j) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(String.format("%02x:", new Object[]{Long.valueOf((j >> (40 - (i * 8))) & 255)}));
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    static String macString(byte[] bArr) {
        if (bArr == null || bArr.length < 6) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(String.format("%02x:", new Object[]{Byte.valueOf(bArr[i])}));
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
