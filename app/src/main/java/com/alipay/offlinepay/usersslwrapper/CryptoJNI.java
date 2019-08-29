package com.alipay.offlinepay.usersslwrapper;

public class CryptoJNI {
    public static final int SM2_MODE_ANS = 0;
    public static final int SM2_MODE_BIGNUM = 1;

    public static native String ecdsaSign(String str, String str2, String str3);

    public static native String ecdsaVerify(String str, String str2, String str3, String str4);

    public static native String sha1Digest(String str);

    public static native String sha1Verify(String str, String str2);

    public static native String sha256Digest(String str);

    public static native String sha256Verify(String str, String str2);

    public static native String sm2Sign(String str, String str2, int i);

    public static native String sm2SignWithUid(String str, String str2, int i, String str3);

    public static native String sm2Verify(String str, String str2, String str3, int i);

    public static native String sm2VerifyWithUid(String str, String str2, String str3, int i, String str4);

    public static native String sm3Digest(String str);

    public static native String sm3Verify(String str, String str2);

    static {
        System.loadLibrary("offlinecrypto");
    }
}
