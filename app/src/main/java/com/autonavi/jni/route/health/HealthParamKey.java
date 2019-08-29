package com.autonavi.jni.route.health;

import java.util.HashMap;
import java.util.Map;

public enum HealthParamKey {
    HPK_WORKSPACE(0),
    HPK_TRACK_PATH(1),
    HPK_TRACK_NAME(2),
    HPK_VOICE_TYPE(3),
    HPK_ENABLE_LOG(4),
    HPK_DP_DISTANCE(5),
    HPK_RES_PATH(6),
    HPK_STAR_CODE(7);
    
    private static Map<Integer, HealthParamKey> map;
    private int value;

    static {
        int i;
        HealthParamKey[] values;
        map = new HashMap();
        for (HealthParamKey healthParamKey : values()) {
            map.put(Integer.valueOf(healthParamKey.value), healthParamKey);
        }
    }

    private HealthParamKey(int i) {
        this.value = i;
    }

    public static HealthParamKey setValue(int i) {
        return map.get(Integer.valueOf(i));
    }

    public final int getValue() {
        return this.value;
    }
}
