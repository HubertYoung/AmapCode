package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;
import com.autonavi.common.SuperId;

public class GrayscaleConfig {
    @JSONField(name = "q80")
    public String qualityGray = "";
    @JSONField(name = "wp")
    public String webpGray = SuperId.BIT_2_REALTIMEBUS_BUSSTATION;

    public String toString() {
        return "GrayscaleConfig{qualityGray='" + this.qualityGray + ";webpGray='" + this.webpGray + '}';
    }
}
