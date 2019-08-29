package com.alipay.multimedia.adjuster.config;

public class BaseConfig {
    public static final int OFF = 0;
    public static final int ON = 1;
    private static final long UPDATE_INTERVAL = 1800000;
    private long lastUpdateTime = 0;

    public boolean needUpdate() {
        return Math.abs(System.currentTimeMillis() - this.lastUpdateTime) > 1800000;
    }

    /* access modifiers changed from: protected */
    public void updateTime() {
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public void setNeedUpdate() {
        this.lastUpdateTime = 0;
    }
}
