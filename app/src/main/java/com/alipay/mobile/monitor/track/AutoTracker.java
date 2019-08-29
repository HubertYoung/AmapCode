package com.alipay.mobile.monitor.track;

public class AutoTracker {
    private AutoTrackerAdapter mAutoTrackerAdapter;
    private AutoTrackerHolder mAutoTrackerHolder;

    private AutoTracker() {
    }

    public static AutoTracker getImpl() {
        return b.a;
    }

    public void launch(AutoTrackerHolder holder) {
        this.mAutoTrackerHolder = holder;
    }

    public void setAutoTrackerAdapter(AutoTrackerAdapter adapter) {
        this.mAutoTrackerAdapter = adapter;
    }

    public AutoTrackerAdapter getAutoTrackerAdapter() {
        return this.mAutoTrackerAdapter;
    }

    public AutoTrackerHolder getAutoTrackerHolder() {
        return this.mAutoTrackerHolder;
    }
}
