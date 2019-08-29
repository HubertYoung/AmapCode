package com.alipay.mobile.common.logging.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RealTimeConfig {
    boolean enable = true;
    public int interval = 5;
    public Map<String, Boolean> realtimeCategory = new ConcurrentHashMap();

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int interval2) {
        if (interval2 <= 1) {
            interval2 = 1;
        }
        this.interval = interval2;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable2) {
        this.enable = enable2;
    }

    public Map<String, Boolean> getRealtimeCategory() {
        return this.realtimeCategory;
    }

    public void setRealtimeCategory(Map<String, Boolean> realtimeCategory2) {
        this.realtimeCategory = realtimeCategory2;
    }
}
