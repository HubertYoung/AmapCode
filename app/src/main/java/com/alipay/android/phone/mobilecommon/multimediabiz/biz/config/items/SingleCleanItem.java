package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class SingleCleanItem {
    public static final int NO_END_TIME = -1;
    @JSONField(name = "et")
    public long endTime = -1;
    @JSONField(name = "id")
    public String id;
    @JSONField(name = "md5")
    public String md5;

    public String key() {
        return this.id + '#' + this.md5 + '#' + this.endTime;
    }

    public String toString() {
        return "SingleCleanItem{id='" + this.id + '\'' + ", md5='" + this.md5 + '\'' + ", endTime=" + this.endTime + '}';
    }
}
