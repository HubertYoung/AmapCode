package com.ali.user.mobile.accountbiz.extservice;

public interface DexInfoService {
    public static final String ALIPAY_SALT = "alipaysalt";

    public static class TaobaoBlackBoxInfo {
        public String clientDigest;
        public String secTS;
    }
}
