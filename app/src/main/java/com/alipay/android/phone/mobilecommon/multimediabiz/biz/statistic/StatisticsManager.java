package com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsManager {
    private static StatisticsManager a = new StatisticsManager();
    private final Map<String, Statistics> b = new ConcurrentHashMap();

    public static StatisticsManager get() {
        return a;
    }

    public <T extends Statistics> T getStatistics(String key) {
        return (Statistics) this.b.get(key);
    }

    public void putStatistics(String key, Statistics statistics) {
        this.b.put(key, statistics);
    }

    public void removeStatistics(String key) {
        this.b.remove(key);
    }

    public void submit(Statistics st) {
        if (st != null) {
            st.submitRemote();
        }
    }
}
