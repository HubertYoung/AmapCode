package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class BusinessCleanStrategy {
    @JSONField(name = "bid")
    public String businessId;
    @JSONField(name = "cet")
    public long cacheExpiredTime = -1;
    @JSONField(name = "ct")
    public int cleanTypes = 65535;
    @JSONField(name = "endTime")
    public long endTime;
    @JSONField(name = "pbid")
    public String prefixBusinessId;
    @JSONField(name = "skipLock")
    public boolean skipLock = true;

    public String toString() {
        return "BusinessCleanStrategy{businessId='" + this.businessId + '\'' + "prefixBusinessId='" + this.prefixBusinessId + '\'' + ", endTime=" + this.endTime + ", cacheExpiredTime=" + this.cacheExpiredTime + ", cleanTypes=" + this.cleanTypes + ", skipLock=" + this.skipLock + '}';
    }
}
