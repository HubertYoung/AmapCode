package com.alibaba.sdk.trade.component.coupon;

import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.sdk.trade.container.network.AlibcContainerNetworkRequest;

public interface AlibcProccessCoupon {
    boolean checkCouponParams();

    String getMoniterPoint();

    Object getSuccessMsg(NetworkResponse networkResponse);

    void initRequestParam(AlibcContainerNetworkRequest alibcContainerNetworkRequest);
}
