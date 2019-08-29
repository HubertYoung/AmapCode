package com.alibaba.sdk.trade.component.coupon;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import com.alibaba.sdk.trade.container.network.AlibcContainerNetworkRequest;
import com.alibaba.sdk.trade.container.utils.AlibcComponentLog;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;
import java.util.HashMap;

public class AlibcGetCoupon implements AlibcProccessCoupon {
    private static final String TAG = "AlibcGetCoupon";
    private static AlibcGetCoupon instance;
    private AlibcGetCouponParams mGetCouponParams;

    public String getMoniterPoint() {
        return AlibcComponentTrack.MONITOR_POINT_WANT_COUPON_GET;
    }

    public Object getSuccessMsg(NetworkResponse networkResponse) {
        return "success";
    }

    public AlibcGetCoupon(AlibcGetCouponParams alibcGetCouponParams) {
        this.mGetCouponParams = alibcGetCouponParams;
    }

    public void initRequestParam(AlibcContainerNetworkRequest alibcContainerNetworkRequest) {
        initGetCouponMtopParams(alibcContainerNetworkRequest);
        HashMap hashMap = new HashMap();
        hashMap.put("asac", this.mGetCouponParams.mASAC);
        hashMap.put("supplierId", this.mGetCouponParams.mSupplierId);
        hashMap.put("uuid", this.mGetCouponParams.mUUID);
        hashMap.put("couponInstanceSource", Integer.valueOf(this.mGetCouponParams.mCouponInstanceSource));
        HashMap hashMap2 = new HashMap();
        hashMap2.put("ybhpss", AlibcComponentTrack.createYbhpss(this.mGetCouponParams.mYbhpssParams, null));
        hashMap.put("extInfo", JSONUtils.toJsonObject(hashMap2).toString());
        alibcContainerNetworkRequest.paramMap = hashMap;
    }

    private void initGetCouponMtopParams(AlibcContainerNetworkRequest alibcContainerNetworkRequest) {
        AlibcComponentTrack.preprocessYbhpss(this.mGetCouponParams.mYbhpssParams, AlibcComponentTrack.LABEL_VALUE_FOR_YBHPSS_COUPON_API, false);
        alibcContainerNetworkRequest.apiName = "mtop.taobao.coupon.opencoupon.apply";
        alibcContainerNetworkRequest.apiVersion = "1.0";
        alibcContainerNetworkRequest.needLogin = true;
        alibcContainerNetworkRequest.needAuth = false;
        alibcContainerNetworkRequest.isPost = true;
        alibcContainerNetworkRequest.needWua = true;
        alibcContainerNetworkRequest.timeOut = 90000;
        alibcContainerNetworkRequest.requestType = alibcContainerNetworkRequest.hashCode();
    }

    public boolean checkCouponParams() {
        String str;
        boolean z = false;
        if (this.mGetCouponParams.mSupplierId == null) {
            str = "SupplierId错误";
        } else if (TextUtils.isEmpty(this.mGetCouponParams.mUUID)) {
            str = "uuid为null";
        } else if (this.mGetCouponParams.mCouponInstanceSource <= 0) {
            str = "CouponInstance为null";
        } else if (TextUtils.isEmpty(this.mGetCouponParams.mASAC)) {
            str = "安全码为null";
        } else {
            z = true;
            str = null;
        }
        if (!z) {
            AlibcComponentLog.d(TAG, str);
            AlibcComponentTrack.sendUseabilityFailure("BCPCSDK", AlibcComponentTrack.MONITOR_POINT_WANT_COUPON_GET, AlibcComponentTrack.ERRNO_COMPONENT_COUPON_PARM, str);
        }
        return z;
    }
}
