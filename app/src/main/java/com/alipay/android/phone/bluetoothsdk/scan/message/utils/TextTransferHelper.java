package com.alipay.android.phone.bluetoothsdk.scan.message.utils;

public class TextTransferHelper {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static final String hexToText(byte[] data) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte curByte : data) {
            stringBuffer.append(HEX_DIGITS[(curByte & 240) >> 4]);
            stringBuffer.append(HEX_DIGITS[curByte & 15]);
        }
        return stringBuffer.toString();
    }
}
