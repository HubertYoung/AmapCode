package com.alibaba.appmonitor.model;

import com.alibaba.appmonitor.event.Event;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.pool.Reusable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricValueSet implements Reusable {
    private Map<Metric, Event> events = Collections.synchronizedMap(new HashMap());

    public List<Event> getEvents() {
        return new ArrayList(this.events.values());
    }

    public Event getEvent(Integer num, String str, String str2, String str3, Class<? extends Event> cls) {
        boolean z;
        Metric metric;
        Event event;
        if (num.intValue() == EventType.STAT.getEventId()) {
            metric = MetricRepo.getRepo().getMetric(str, str2);
            z = false;
        } else {
            metric = (Metric) BalancedPool.getInstance().poll(Metric.class, str, str2, str3);
            z = true;
        }
        Event event2 = null;
        if (metric != null) {
            if (this.events.containsKey(metric)) {
                event2 = this.events.get(metric);
            } else {
                synchronized (MetricValueSet.class) {
                    try {
                        event = (Event) BalancedPool.getInstance().poll(cls, num, str, str2, str3);
                        this.events.put(metric, event);
                    }
                }
                event2 = event;
                z = false;
            }
            if (z) {
                BalancedPool.getInstance().offer(metric);
            }
        }
        return event2;
    }

    public void clean() {
        for (Event offer : this.events.values()) {
            BalancedPool.getInstance().offer(offer);
        }
        this.events.clear();
    }

    public void fill(Object... objArr) {
        if (this.events == null) {
            this.events = Collections.synchronizedMap(new HashMap());
        }
    }
}
