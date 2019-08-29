package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class MemoryTrimStrategy {
    public static final int MEMORY_TYPE_COMMON = 1;
    public static final int MEMORY_TYPE_NATIVE = 2;
    public static final int MEMORY_TYPE_SOFTREFERENCE = 3;
    @JSONField(name = "ms")
    public long maxSize;
    @JSONField(name = "mt")
    public int memType;

    public MemoryTrimStrategy(int memType2, long maxSize2) {
        this.memType = memType2;
        this.maxSize = maxSize2;
    }

    public MemoryTrimStrategy() {
    }

    public String toString() {
        return "MemoryTrimStrategy{memType=" + this.memType + ", maxSize=" + this.maxSize + '}';
    }
}
