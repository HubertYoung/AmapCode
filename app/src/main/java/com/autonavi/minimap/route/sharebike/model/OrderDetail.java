package com.autonavi.minimap.route.sharebike.model;

import android.text.TextUtils;
import java.util.List;

public class OrderDetail extends BaseNetResult {
    public static final int STATUS_FINISH = 4;
    public static final int STATUS_FINISH_AND_NEED_PAY = 6;
    public static final int STATUS_MOBIKE_RIDING_FINISH = 3;
    public static final int STATUS_REPORT = 5;
    public static final int STATUS_RIDING = 1;
    public static final int STATUS_UNLOCKING = 0;
    public static final int STATUS_UNLOCKING_FAILED = 2;
    public String alipay;
    public String bikeid;
    public List<egq> costSection;
    public String fees;
    public String isPay;
    public String orderid;
    public String pasString;
    public String ridingTime;
    public String status;
    public String tagDesc;
    public String totalFee;

    public boolean isShowMonthCardInfo() {
        return !TextUtils.isEmpty(this.tagDesc) && !TextUtils.isEmpty(this.fees);
    }
}
