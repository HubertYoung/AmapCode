package com.autonavi.minimap.route.sharebike.model;

import android.text.TextUtils;

public class BicycleDetailItem extends AbsBicycleItem {
    public String cpCode;
    public String favourableDesc;
    public String fees;
    public String feesInterval;
    public String iconCode;
    public String logoCode;
    public String partnerName;
    public String status;
    public double x;
    public double y;

    public boolean isPartnerOfo() {
        return !TextUtils.isEmpty(this.partnerName) && this.partnerName.contains("ofo");
    }

    public boolean isPartnerMobike() {
        return !TextUtils.isEmpty(this.partnerName) && this.partnerName.contains("摩拜");
    }
}
