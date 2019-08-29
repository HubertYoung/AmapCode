package com.autonavi.jni.alc.inter;

public interface IALCCloudStrategy {
    public static final int NOT_REACHABLE = 1;
    public static final int REACHABLE_UNKNOWN = 6;
    public static final int REACHABLE_VIA_2G = 3;
    public static final int REACHABLE_VIA_3G = 4;
    public static final int REACHABLE_VIA_4G = 5;
    public static final int REACHABLE_VIA_5G = 7;
    public static final int REACHABLE_VIA_WIFI = 2;

    String cloudStrategy();

    int currentNetworkStatus();
}
