package com.autonavi.jni.route.health;

import java.util.HashMap;
import java.util.Map;

public enum TraceStatus {
    TS_AUTO_NORMAL(0),
    TS_AUTO_PAUSE(1),
    TS_AUTO_RESUME(2),
    TS_STOP(3);
    
    private static Map<Integer, TraceStatus> map;
    private int value;

    static {
        int i;
        TraceStatus[] values;
        map = new HashMap();
        for (TraceStatus traceStatus : values()) {
            map.put(Integer.valueOf(traceStatus.value), traceStatus);
        }
    }

    private TraceStatus(int i) {
        this.value = i;
    }

    public static TraceStatus setValue(int i) {
        return map.get(Integer.valueOf(i));
    }

    public final int getValue() {
        return this.value;
    }
}
