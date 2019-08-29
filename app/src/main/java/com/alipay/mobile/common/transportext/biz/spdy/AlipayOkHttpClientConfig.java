package com.alipay.mobile.common.transportext.biz.spdy;

public class AlipayOkHttpClientConfig {
    public static boolean isTrustAll = true;
    public static boolean isUseNpn = true;

    public static void initDevConfig() {
        isTrustAll = true;
        isUseNpn = true;
    }
}
