package com.alipay.multimedia.common.config.item;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.multimedia.common.config.IConfig;

public class PlayerConf implements IConfig {
    @JSONField(name = "progressUpdateInterval")
    public int progressUpdateInterval = 1000;

    public Object update(Object t) {
        if (t instanceof PlayerConf) {
            return null;
        }
        throw new IllegalArgumentException("Source is not instance of PlayerConf");
    }

    public PlayerConf clone() {
        return null;
    }

    public String toString() {
        return super.toString();
    }
}
