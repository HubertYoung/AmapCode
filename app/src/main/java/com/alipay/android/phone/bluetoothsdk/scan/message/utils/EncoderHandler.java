package com.alipay.android.phone.bluetoothsdk.scan.message.utils;

import java.security.MessageDigest;

public class EncoderHandler {

    public enum EncodeType {
        MD5("MD5"),
        SHA1("SHA1");
        
        String name;

        private EncodeType(String name2) {
            this.name = name2;
        }

        public final String getName() {
            return this.name;
        }
    }

    public static byte[] encode(EncodeType encodeType, byte[] dataBytes) {
        if (dataBytes == null) {
            throw new NullPointerException("the byte array is null");
        }
        MessageDigest messageDigest = MessageDigest.getInstance(encodeType.getName());
        messageDigest.update(dataBytes);
        return messageDigest.digest();
    }
}
