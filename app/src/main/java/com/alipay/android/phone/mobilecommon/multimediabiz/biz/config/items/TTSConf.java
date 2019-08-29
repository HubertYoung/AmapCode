package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class TTSConf {
    @JSONField(name = "av")
    public boolean adjustVolume = true;
    @JSONField(name = "pc")
    public int playConfig = 0;

    public String toString() {
        return "TTSConf{playConfig=" + this.playConfig + "adjustVolume=" + this.adjustVolume + '}';
    }
}
