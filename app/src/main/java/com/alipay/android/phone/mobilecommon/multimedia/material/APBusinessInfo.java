package com.alipay.android.phone.mobilecommon.multimedia.material;

import com.alibaba.fastjson.annotation.JSONField;

public class APBusinessInfo {
    @JSONField(name = "businessId")
    public String businessId;
    @JSONField(name = "shortCut")
    public String shortCut;
    @JSONField(name = "version")
    public String version;

    public String toString() {
        return "APBusinessInfo{businessId='" + this.businessId + '\'' + ", shortCut='" + this.shortCut + '\'' + ", version='" + this.version + '\'' + '}';
    }
}
