package com.autonavi.jni.route.health;

import java.util.HashMap;
import java.util.Map;

public enum HealthPointStatus {
    HPS_VALID(0),
    HPS_IN_VALID(1),
    HPS_AUTO_PAUSE(2),
    HPS_PAUSE(3),
    HPS_BEFORE_START(4);
    
    private static Map<Integer, HealthPointStatus> map;
    private int value;

    static {
        int i;
        HealthPointStatus[] values;
        map = new HashMap();
        for (HealthPointStatus healthPointStatus : values()) {
            map.put(Integer.valueOf(healthPointStatus.value), healthPointStatus);
        }
    }

    private HealthPointStatus(int i) {
        this.value = i;
    }

    public static HealthPointStatus setValue(int i) {
        return map.get(Integer.valueOf(i));
    }

    public final int getValue() {
        return this.value;
    }
}
