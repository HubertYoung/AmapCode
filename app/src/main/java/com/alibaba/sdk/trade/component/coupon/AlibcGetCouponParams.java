package com.alibaba.sdk.trade.component.coupon;

import java.util.Map;

public class AlibcGetCouponParams implements AlibcCouponParams {
    public String mASAC;
    public int mCouponInstanceSource;
    public Long mSupplierId;
    public String mUUID;
    public Map<String, String> mYbhpssParams;

    public AlibcProccessCoupon getCoupon() {
        return new AlibcGetCoupon(this);
    }
}
