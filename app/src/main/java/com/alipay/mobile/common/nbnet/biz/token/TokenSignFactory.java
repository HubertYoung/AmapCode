package com.alipay.mobile.common.nbnet.biz.token;

public class TokenSignFactory {
    public static TokenSignManager a = new DefaultTokenSignManager();

    public static final TokenSignManager a() {
        return a;
    }
}
