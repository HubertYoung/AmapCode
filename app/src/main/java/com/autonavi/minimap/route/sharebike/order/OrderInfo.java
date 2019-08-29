package com.autonavi.minimap.route.sharebike.order;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class OrderInfo implements Serializable {
    public static final int SOURCE_CHECKORDER = 3;
    public static final int SOURCE_DEFAULT = 0;
    public static final int SOURCE_ENDBILL = 4;
    public static final int SOURCE_QRSCAN = 1;
    public static final int SOURCE_RIDESTATE = 2;
    public String accountAppkey = "";
    public String accountToken = "";
    public String accountUserid = "";
    public String bikeId = "";
    public float cost;
    public String cpSource = "";
    public int dataSource;
    public Object extraData;
    public boolean hasNetFailed;
    public boolean hasUnfinishOrder;
    public String orderId = "";
    public int status = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DataSource {
    }

    public void setSource(int i) {
        this.dataSource = i;
    }

    public int getSource() {
        return this.dataSource;
    }
}
