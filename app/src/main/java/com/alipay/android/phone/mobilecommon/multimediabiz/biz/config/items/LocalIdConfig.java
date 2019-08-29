package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class LocalIdConfig {
    @JSONField(name = "ldc")
    public long lruDeleteCount = 100;
    @JSONField(name = "mc")
    public long maxCount = 60000;

    public String toString() {
        return "LocalIdConfig{maxCount=" + this.maxCount + ", lruDeleteCount=" + this.lruDeleteCount + '}';
    }
}
