package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class ProgConfig {
    @JSONField(name = "imgUpMax")
    public int imgUpProgMax = 12;
    @JSONField(name = "imgUpMin")
    public int imgUpProgMin = 10;
    @JSONField(name = "imgUpProg")
    public int imgUpProgSwitch = 1;
    @JSONField(name = "interval")
    public int timeInterval = 500;
    @JSONField(name = "vdUpMax")
    public int vdUpProgMax = 4;
    @JSONField(name = "vdUpMin")
    public int vdUpProgMin = 2;
    @JSONField(name = "vdUpProg")
    public int vdUpProgSwitch = 1;

    public boolean isImageProgOn() {
        return this.imgUpProgSwitch == 1;
    }

    public boolean isVideoProgOn() {
        return this.vdUpProgSwitch == 1;
    }

    public String toString() {
        return "ProgConfig{imgUpProgSwitch=" + this.imgUpProgSwitch + ", imgUpProgMin=" + this.imgUpProgMin + ", imgUpProgMax=" + this.imgUpProgMax + ", vdUpProgSwitch=" + this.vdUpProgSwitch + ", vdUpProgMin=" + this.vdUpProgMin + ", vdUpProgMax=" + this.vdUpProgMax + ", timeInterval=" + this.timeInterval + '}';
    }
}
