package com.alipay.mobile.monitor.api;

public class APMSmoothnessConstants {
    public static final long ACTIVITY_CREATE_MINUS = 900;
    public static final long SMOOTH_HI_LAG_L_LIMIT = 1000;
    public static final double SMOOTH_HI_WEIGHT = 2.25d;
    public static final long SMOOTH_LOW_LAG_H_LIMIT = 499;
    public static final long SMOOTH_LOW_LAG_L_LIMIT = 200;
    public static final double SMOOTH_LOW_WEIGHT = 1.0d;
    public static final long SMOOTH_MID_LAG_H_LIMIT = 999;
    public static final long SMOOTH_MID_LAG_L_LIMIT = 500;
    public static final double SMOOTH_MID_WEIGHT = 1.5d;
    public static final String SMOOTH_MONITOR_UNIT_ID = "SMOOTH_MONITOR_UNIT_ID";
    public static final String SMOOTH_MONITOR_UNIT_TYPE = "SMOOTH_MONITOR_UNIT_TYPE";
    public static final String TYPE_ACTIVITY = "ACTIVITY";
    public static final String TYPE_APP = "APP";
    public static final String UNIT_MONITOR_CONTROL = "UNIT_MONITOR_CONTROL";
    public static final String UNIT_MONITOR_END = "UNIT_MONITOR_END";
    public static final String UNIT_MONITOR_START = "UNIT_MONITOR_START";
}
