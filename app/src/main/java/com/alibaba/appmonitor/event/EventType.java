package com.alibaba.appmonitor.event;

import com.alibaba.analytics.utils.Logger;
import com.alibaba.appmonitor.sample.AlarmConfig;
import com.alibaba.appmonitor.sample.CounterConfig;
import com.alibaba.appmonitor.sample.StatConfig;

public enum EventType {
    ALARM(65501, 30, "alarmData", 1000, "ap_alarm", AlarmConfig.class),
    COUNTER(65502, 30, "counterData", 1000, "ap_counter", CounterConfig.class),
    STAT(65503, 30, "statData", 1000, "ap_stat", StatConfig.class);
    
    static String TAG = "EventType";
    private String aggregateEventArgsKey;
    private int backgroundStatisticsInterval;
    private Class cls;
    private int defaultSampling;
    private int eventId;
    private int foregroundStatisticsInterval;
    private String namespce;
    private boolean open;
    private int triggerCount;

    private EventType(int i, int i2, String str, int i3, String str2, Class cls2) {
        this.foregroundStatisticsInterval = 25;
        this.backgroundStatisticsInterval = 300;
        this.eventId = i;
        this.triggerCount = i2;
        this.open = true;
        this.aggregateEventArgsKey = str;
        this.defaultSampling = i3;
        this.namespce = str2;
        this.cls = cls2;
    }

    public final int getEventId() {
        return this.eventId;
    }

    public final boolean isOpen() {
        return this.open;
    }

    public final void setOpen(boolean z) {
        this.open = z;
    }

    public final int getTriggerCount() {
        return this.triggerCount;
    }

    @Deprecated
    public final void setTriggerCount(int i) {
        Logger.d(TAG, "[setTriggerCount]", this.aggregateEventArgsKey, String.valueOf(i));
        this.triggerCount = i;
    }

    public static EventType getEventType(int i) {
        EventType[] values = values();
        for (EventType eventType : values) {
            if (eventType != null && eventType.getEventId() == i) {
                return eventType;
            }
        }
        return null;
    }

    public final String getAggregateEventArgsKey() {
        return this.aggregateEventArgsKey;
    }

    public final int getForegroundStatisticsInterval() {
        return this.foregroundStatisticsInterval;
    }

    public final int getBackgroundStatisticsInterval() {
        return this.backgroundStatisticsInterval;
    }

    public final void setStatisticsInterval(int i) {
        this.foregroundStatisticsInterval = i;
    }

    public final int getDefaultSampling() {
        return this.defaultSampling;
    }

    public final void setDefaultSampling(int i) {
        this.defaultSampling = i;
    }

    public static EventType getEventTypeByNameSpace(String str) {
        if (str == null) {
            return null;
        }
        EventType[] values = values();
        for (EventType eventType : values) {
            if (eventType != null && str.equalsIgnoreCase(eventType.getNameSpace())) {
                return eventType;
            }
        }
        return null;
    }

    private String getNameSpace() {
        return this.namespce;
    }

    public final Class getCls() {
        return this.cls;
    }
}
