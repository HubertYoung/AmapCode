package com.alipay.mobile.common.logging.api.encrypt;

public interface LogEncryptClient {
    String decrypt(String str);

    byte[] decrypt(byte[] bArr);

    String encrypt(String str);

    byte[] encrypt(byte[] bArr);
}
