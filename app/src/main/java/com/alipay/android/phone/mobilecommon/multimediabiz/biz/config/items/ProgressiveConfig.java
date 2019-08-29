package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class ProgressiveConfig {
    @JSONField(name = "lv")
    public int netLevel = 4;
    @JSONField(name = "net")
    public int netType = 2;
    @JSONField(name = "ps")
    public int progressive = 1;
    @JSONField(name = "piv")
    public int progressiveInterval = 20;
    @JSONField(name = "max")
    public int progressiveMax = 70;
    @JSONField(name = "mid")
    public int progressiveMid = 45;
    @JSONField(name = "min")
    public int progressiveMin = 25;
    @JSONField(name = "ms")
    public int progressiveMinSize = 5;
    @JSONField(name = "qs")
    public int qosSwitch = 1;
    @JSONField(name = "tiv")
    public int timeInterval = 2;

    public String toString() {
        return "ProgressiveConfig{progressive=" + this.progressive + ", progressiveMin=" + this.progressiveMin + ", progressiveMax=" + this.progressiveMax + ", progressiveMid=" + this.progressiveMid + ", progressiveInterval=" + this.progressiveInterval + ", progressiveMinSize=" + this.progressiveMinSize + ", timeInterval=" + this.timeInterval + ", netType=" + this.netType + ", qosSwitch=" + this.qosSwitch + ", netLevel=" + this.netLevel + '}';
    }
}
