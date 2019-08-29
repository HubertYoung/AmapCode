package com.autonavi.minimap.route.sharebike.model;

public class CheckOrder extends BaseNetResult {
    public static final int NOT_RIDING = 0;
    public static final int RIDING = 1;
    public String bikeId;
    public String cpSource;
    public String loadingtime;
    public String orderId;
    public String repairurl;
    public int ridingStatus;
    public int status;
    public String unlockpwd;
}
