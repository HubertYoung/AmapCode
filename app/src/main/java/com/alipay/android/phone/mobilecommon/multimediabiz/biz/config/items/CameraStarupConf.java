package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class CameraStarupConf {
    @JSONField(name = "ia")
    public int initAsync = 0;

    public String toString() {
        return "CameraStarupConf{initAsync=" + this.initAsync + '}';
    }
}
