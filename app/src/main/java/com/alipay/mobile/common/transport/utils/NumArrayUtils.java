package com.alipay.mobile.common.transport.utils;

public class NumArrayUtils {
    public static int getUnsignedByte(byte[] b) {
        return b[0] & 255;
    }

    public static int getUnsignedInt(byte[] b) {
        return (b[3] & 255) | ((b[2] & 255) << 8) | ((b[1] & 255) << 16) | ((b[0] & 255) << 24);
    }

    public static int getUnsignedMedium(byte[] b) {
        return (b[2] & 255) | ((b[1] & 255) << 8) | ((b[0] & 255) << 16);
    }

    public static int getUnsignedShort(byte[] b) {
        return (b[1] & 255) | ((b[0] & 255) << 8);
    }

    public static byte[] shortToByteArray(int value) {
        return new byte[]{(byte) ((value >> 8) & 255), (byte) (value & 255)};
    }

    public static byte[] mediumToByteArray(int value) {
        return new byte[]{(byte) ((value >> 16) & 255), (byte) ((value >> 8) & 255), (byte) (value & 255)};
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{(byte) ((a >> 24) & 255), (byte) ((a >> 16) & 255), (byte) ((a >> 8) & 255), (byte) (a & 255)};
    }
}
