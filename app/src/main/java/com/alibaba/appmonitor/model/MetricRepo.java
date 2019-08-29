package com.alibaba.appmonitor.model;

import com.alibaba.appmonitor.offline.TempEventMgr;
import java.util.ArrayList;
import java.util.List;

public class MetricRepo {
    private static final int INIT_SIZE = 3;
    private static MetricRepo instance;
    public List<Metric> metrics;

    public static MetricRepo getRepo() {
        if (instance == null) {
            instance = new MetricRepo(3);
        }
        return instance;
    }

    public static MetricRepo getRepo(int i) {
        return new MetricRepo(i);
    }

    private MetricRepo(int i) {
        this.metrics = new ArrayList(i);
    }

    public void add(Metric metric) {
        if (this.metrics.contains(metric)) {
            this.metrics.remove(metric);
        }
        this.metrics.add(metric);
    }

    public boolean remove(Metric metric) {
        if (this.metrics.contains(metric)) {
            return this.metrics.remove(metric);
        }
        return true;
    }

    public Metric getMetric(String str, String str2) {
        if (str == null || str2 == null || this.metrics == null) {
            return null;
        }
        int size = this.metrics.size();
        for (int i = 0; i < size; i++) {
            Metric metric = this.metrics.get(i);
            if (metric != null && metric.getModule().equals(str) && metric.getMonitorPoint().equals(str2)) {
                return metric;
            }
        }
        Metric metric2 = TempEventMgr.getInstance().getMetric(str, str2);
        if (metric2 != null) {
            this.metrics.add(metric2);
        }
        return metric2;
    }
}
