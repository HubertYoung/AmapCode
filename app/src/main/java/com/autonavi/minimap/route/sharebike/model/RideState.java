package com.autonavi.minimap.route.sharebike.model;

import android.text.TextUtils;

public class RideState extends BaseNetResult {
    public static final int BIKE_LOCK_DEFAULT_TYPE = -1;
    public static final int BIKE_LOCK_ELECTRONIC_TYPE = 1;
    public static final int BIKE_LOCK_MACHINE_TYPE = 0;
    public static final int RIDE_STATUS_EXCEPTION = -1;
    public static final int RIDE_STATUS_HAS_REPORT_ERROR = 3;
    public static final int RIDE_STATUS_NOT_RIDE = 0;
    public static final int RIDE_STATUS_RIDING = 1;
    public static final int RIDE_STATUS_UNLOCKING = 2;
    public String accountAppkey = "";
    public String accountToken = "";
    public String accountUserid = "";
    public String bikeId = "";
    public float cost;
    public long createTime;
    public long currentTimestamp;
    public int duration = 0;
    public long durationSec = -1;
    public String faqDesc = "";
    public String faqUrl = "";
    public String fees;
    public int lockType = -1;
    public String orderId = "";
    public int status = -1;
    public String tag_desc;

    public boolean isVip() {
        return !TextUtils.isEmpty(this.tag_desc) && !TextUtils.isEmpty(this.fees);
    }

    public boolean isOfo() {
        return this.lockType >= 0;
    }
}
