package com.autonavi.minimap.route.sharebike.model;

public class ScanQrcode extends BaseNetResult {
    public static final int AUTHORIZE_RESPONSE_CODE = 160;
    public static final int NOT_CP_RESPONSE_CODE = 161;
    public static final int SAME_BIKE_UNPAY_RESPONSE_CODE = 155;
    public static final int SAME_BIKE_USING_RESPONSE_CODE = 154;
    public String appkey;
    public String bikeId;
    public String cpSource;
    public String cpUserId;
    public String h5Url;
    public boolean isGsmLock;
    public int loadingTime;
    public String orderId;
    public String repairurl;
    public String toast;
    public String token;
    public long unlockTime;
    public String unlockpwd;
}
