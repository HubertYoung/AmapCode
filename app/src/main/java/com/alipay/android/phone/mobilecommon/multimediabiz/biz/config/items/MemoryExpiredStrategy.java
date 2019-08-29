package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class MemoryExpiredStrategy {
    public static final int MEMORY_TYPE_COMMON = 1;
    public static final int MEMORY_TYPE_GIF = 4;
    public static final int MEMORY_TYPE_NATIVE = 2;
    public static final int MEMORY_TYPE_SOFTREFERENCE = 3;
    @JSONField(name = "alt")
    public long aliveTime;
    @JSONField(name = "mt")
    public int memoryType;

    public MemoryExpiredStrategy(int memoryType2, long aliveTime2) {
        this.memoryType = memoryType2;
        this.aliveTime = aliveTime2;
    }

    public MemoryExpiredStrategy() {
    }

    public String toString() {
        return "MemoryExpiredStrategy{memoryType=" + this.memoryType + ", aliveTime=" + this.aliveTime + '}';
    }
}
