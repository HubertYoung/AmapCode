package com.autonavi.jni.ae.route.model;

public class LightBarItem {
    public static final int BLOCKSTATUS = 3;
    public static final int SLOWSTATUS = 2;
    public static final int SUPBLOCKSTATUS = 4;
    public static final int UNBLOCKSTATUS = 1;
    public static final int UNKNOWNSTATUS = 0;
    public TrafficItem end3dTrafficItem;
    public int endLinkIndex;
    public int endLinkStatus;
    public int endSegmentIdx;
    public TrafficItem endTrafficItem;
    public int length;
    public TrafficItem start3dTrafficItem;
    public int startLinkIdx;
    public int startLinkStatus;
    public int startSegmentIdx;
    public TrafficItem startTrafficItem;
    public int status;
}
