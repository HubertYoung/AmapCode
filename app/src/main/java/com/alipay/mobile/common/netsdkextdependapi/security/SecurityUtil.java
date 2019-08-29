package com.alipay.mobile.common.netsdkextdependapi.security;

public class SecurityUtil {
    public static final SignResult signature(SignRequest signRequest) {
        return a().signature(signRequest);
    }

    public static final byte[] encrypt(byte[] source) {
        return a().encrypt(source);
    }

    public static final byte[] encrypt(byte[] source, String type) {
        return a().encrypt(source, type);
    }

    public static final byte[] decrypt(byte[] encrypted) {
        return a().decrypt(encrypted);
    }

    public static final byte[] decrypt(byte[] encrypted, String type) {
        return a().decrypt(encrypted, type);
    }

    private static final SecurityManager a() {
        return (SecurityManager) SecurityManagerFactory.getInstance().getDefaultBean();
    }
}
