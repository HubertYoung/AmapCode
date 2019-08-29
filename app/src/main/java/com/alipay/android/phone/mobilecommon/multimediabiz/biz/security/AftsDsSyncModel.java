package com.alipay.android.phone.mobilecommon.multimediabiz.biz.security;

import com.alibaba.fastjson.annotation.JSONField;

public class AftsDsSyncModel {
    public static final String BIZ_SECURE = "secure";
    @JSONField(name = "biz")
    public String biz;
    @JSONField(name = "data")
    public String data;
}
