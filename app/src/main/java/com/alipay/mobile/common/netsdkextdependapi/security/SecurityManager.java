package com.alipay.mobile.common.netsdkextdependapi.security;

public interface SecurityManager {
    byte[] decrypt(byte[] bArr);

    byte[] decrypt(byte[] bArr, String str);

    byte[] encrypt(byte[] bArr);

    byte[] encrypt(byte[] bArr, String str);

    SignResult signature(SignRequest signRequest);
}
