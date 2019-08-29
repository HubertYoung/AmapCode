package com.alipay.multimedia.adjuster.config;

import com.alibaba.fastjson.annotation.JSONField;

public class HostItem {
    @JSONField(name = "gp")
    public int grayPercent = 0;
    @JSONField(name = "hs")
    public String host = "";

    public HostItem() {
    }

    public HostItem(String host2, int percent) {
        this.host = host2;
        this.grayPercent = percent;
    }

    public boolean checkPercent() {
        return this.grayPercent > 0 && this.grayPercent <= 100;
    }

    public boolean checkGraySwitch(int random) {
        return random > 0 && random <= this.grayPercent;
    }
}
