package com.alipay.mobile.common.netsdkextdependapi.security;

public class SignRequest {
    public static int SIGN_TYPE_ATLAS = 2;
    public static int SIGN_TYPE_HMAC_SHA1 = 1;
    public static int SIGN_TYPE_MD5 = 0;
    public String appkey;
    public String content;
    public int signType = SIGN_TYPE_MD5;

    public boolean isSignTypeMD5() {
        return this.signType == SIGN_TYPE_MD5;
    }

    public boolean isSignTypeAtlas() {
        return this.signType == SIGN_TYPE_ATLAS;
    }

    public boolean isSignTypeHmacSha1() {
        return this.signType == SIGN_TYPE_HMAC_SHA1;
    }
}
