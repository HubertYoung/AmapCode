package com.autonavi.minimap.route.sharebike.model;

public class CheckLock extends BaseNetResult {
    public static final int FAILED = 0;
    public static final int SUCCESS = 1;
    public String bikeId;
    public String h5Url;
    public String orderId;
    public int unlockResult;
}
