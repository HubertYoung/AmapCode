package com.alibaba.analytics.core.selfmonitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelfMonitorEventDispather {
    private static SelfMonitorEventListener testListener;
    private List<SelfMonitorEventListener> listeners = Collections.synchronizedList(new ArrayList());

    public void regiserListener(SelfMonitorEventListener selfMonitorEventListener) {
        this.listeners.add(selfMonitorEventListener);
    }

    public void unRegisterListener(SelfMonitorEventListener selfMonitorEventListener) {
        this.listeners.remove(selfMonitorEventListener);
    }

    public void onEvent(SelfMonitorEvent selfMonitorEvent) {
        if (testListener != null) {
            testListener.onEvent(selfMonitorEvent);
        }
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).onEvent(selfMonitorEvent);
        }
    }

    public static void setTestListener(SelfMonitorEventListener selfMonitorEventListener) {
        testListener = selfMonitorEventListener;
    }
}
