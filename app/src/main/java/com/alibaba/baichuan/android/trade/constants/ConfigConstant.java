package com.alibaba.baichuan.android.trade.constants;

import com.alibaba.baichuan.android.trade.AlibcContext;

public class ConfigConstant {
    public static final String CHECK_GROUP_NAME = "group0";
    public static final String MD5_SALT = "ALITRADE20160628";
    public static final String SIGN_KEY = "sign";
    public static final String SP_CONFIG_NAME = "aliTradeConfigSP";

    public static String getConfigUrl() {
        String str;
        switch (AlibcContext.environment) {
            case PRE:
                str = "http://nbsdk-baichuan.taobao.com/%s/%s/%s/meta.htm?plat=android";
                break;
            case TEST:
                str = "http://100.69.205.47/%s/%s/%s/meta.htm?plat=android";
                break;
            default:
                str = "https://nbsdk-baichuan.alicdn.com/%s/%s/%s/meta.htm?plat=android";
                break;
        }
        return String.format(str, new Object[]{AlibcContext.sdkVersion, AlibcContext.getAppKey(), "1.0.0"});
    }
}
