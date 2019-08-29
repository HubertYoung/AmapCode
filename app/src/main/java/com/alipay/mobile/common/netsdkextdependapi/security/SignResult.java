package com.alipay.mobile.common.netsdkextdependapi.security;

public class SignResult {
    private static SignResult a;
    private boolean b = false;
    public String sign = "";
    public int signType = SignRequest.SIGN_TYPE_MD5;

    public static final SignResult newEmptySignData() {
        if (a == null) {
            a = new SignResult();
        }
        return a;
    }

    public boolean isSuccess() {
        return this.b;
    }

    public void setSuccess(boolean success) {
        this.b = success;
    }
}
