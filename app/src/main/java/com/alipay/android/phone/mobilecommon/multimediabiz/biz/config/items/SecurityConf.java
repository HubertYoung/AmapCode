package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class SecurityConf {
    @JSONField(name = "ehh")
    public int enableHttpHandle = 0;
    @JSONField(name = "enbh")
    public int enableNBnetHandle = 0;
    @JSONField(name = "esh")
    public int enableSyncHandle = 0;
    @JSONField(name = "inbsc")
    public int illegalNBnetStatusCode = 901;
    @JSONField(name = "isc")
    public int illegalStatusCode = 901;

    public boolean isEnableSyncHandle() {
        return this.enableSyncHandle == 1;
    }

    public boolean isEnableHttpHandle() {
        return this.enableHttpHandle == 1;
    }

    public boolean isEnableNBnetHandle() {
        return this.enableNBnetHandle == 1;
    }

    public String toString() {
        return "SecurityConf{enableSyncHandle=" + this.enableSyncHandle + ", enableHttpHandle=" + this.enableHttpHandle + ", enableNBnetHandle=" + this.enableNBnetHandle + ", illegalStatusCode=" + this.illegalStatusCode + ", illegalNBnetStatusCode=" + this.illegalNBnetStatusCode + '}';
    }
}
