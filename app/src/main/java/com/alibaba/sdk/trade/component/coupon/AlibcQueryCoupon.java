package com.alibaba.sdk.trade.component.coupon;

import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.sdk.trade.container.network.AlibcContainerNetworkRequest;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;

public class AlibcQueryCoupon implements AlibcProccessCoupon {
    public boolean checkCouponParams() {
        return true;
    }

    public String getMoniterPoint() {
        return AlibcComponentTrack.MONITOR_POINT_WANT_SELLER_COUPON_QUERY;
    }

    public void initRequestParam(AlibcContainerNetworkRequest alibcContainerNetworkRequest) {
        alibcContainerNetworkRequest.apiName = "mtop.taobao.coupon.opencoupon.seller.query";
        alibcContainerNetworkRequest.apiVersion = "1.0";
        alibcContainerNetworkRequest.needLogin = true;
        alibcContainerNetworkRequest.needAuth = false;
        alibcContainerNetworkRequest.isPost = true;
        alibcContainerNetworkRequest.needWua = false;
        alibcContainerNetworkRequest.timeOut = 90000;
        alibcContainerNetworkRequest.requestType = alibcContainerNetworkRequest.hashCode();
    }

    public Object getSuccessMsg(NetworkResponse networkResponse) {
        return networkResponse.data;
    }
}
