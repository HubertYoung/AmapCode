package com.alipay.mobile.tinyappcommon.utils.codec;

public final class HexUtils {
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 255);
        if (hex.length() < 2) {
            return "0" + hex;
        }
        return hex;
    }

    public static String byteArrayToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexToByteArray(String hex) {
        byte[] result;
        if (hex == null) {
            return null;
        }
        int hexLen = hex.length();
        if (hexLen % 2 == 1) {
            hexLen++;
            result = new byte[(hexLen / 2)];
            hex = "0" + hex;
        } else {
            result = new byte[(hexLen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexLen; i += 2) {
            result[j] = hexToByte(hex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    public static byte hexToByte(String hex) {
        try {
            return (byte) Integer.parseInt(hex, 16);
        } catch (Exception e) {
            return 0;
        }
    }
}
