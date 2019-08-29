package com.alipay.mobile.common.logging.api.monitor;

import java.util.HashMap;
import java.util.Map;

public enum BatteryID {
    STATISTIC("STATISTIC"),
    SAMPLE_STATS("STATS"),
    MEDIA_RECORD("RECORD"),
    LOCATION("LOC"),
    MAP("MAP"),
    WIFI_SCAN("SCAN"),
    NORMAL_ALARM("N_ALARM"),
    AMNET_ALARM("A_ALARM"),
    SENSOR("SENSOR"),
    WAKE_LOCK("WAKE"),
    WEB_VIEW("H5"),
    BLUETOOTH("BT"),
    SEND_BROADCAST("BROADCAST"),
    UNKNOWN("NA");
    
    @Deprecated
    public static final String DEFAULT_BUNDLE = "bundle";
    @Deprecated
    public static final String DEFAULT_DIAGNOSE = "diagnose";
    @Deprecated
    public static final String DEFAULT_FALSE = "0";
    @Deprecated
    public static final String DEFAULT_TRUE = "1";
    @Deprecated
    public static final String STATS_LONG_TIME_CONSUME = "longTimeCost";
    @Deprecated
    public static final String STATS_WARNNING_NAME = "warnName";
    @Deprecated
    public static final String STATS_WARNNING_VALUE = "warnVal";
    private static final Map<String, BatteryID> sStringToEnum = null;
    private String desc;

    static {
        int i;
        BatteryID[] values;
        sStringToEnum = new HashMap();
        for (BatteryID value : values()) {
            sStringToEnum.put(value.desc, value);
        }
    }

    private BatteryID(String desc2) {
        this.desc = desc2;
    }

    public final String getDes() {
        return this.desc;
    }

    public static BatteryID fromString(String symbol) {
        return sStringToEnum.get(symbol);
    }
}
