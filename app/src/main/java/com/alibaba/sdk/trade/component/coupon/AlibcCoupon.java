package com.alibaba.sdk.trade.component.coupon;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.sdk.trade.container.AlibcBaseComponent;
import com.alibaba.sdk.trade.container.AlibcComponentCallback;
import com.alibaba.sdk.trade.container.network.AlibcContainerNetwork;
import com.alibaba.sdk.trade.container.network.AlibcContainerNetworkRequest;
import com.alibaba.sdk.trade.container.network.AlibcContainerNetworkRequestListener;
import com.alibaba.sdk.trade.container.utils.AlibcComponentLog;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;

public class AlibcCoupon extends AlibcBaseComponent {
    private static final String TAG = "AlibcCoupon";
    public static AlibcCoupon instance = new AlibcCoupon();

    public int getType() {
        return 2;
    }

    public String getWantName() {
        return "BCWantCoupon";
    }

    private AlibcCoupon() {
        this.mBizId = "BC_WantCoupon";
    }

    public void execute(Object obj, AlibcComponentCallback alibcComponentCallback) {
        if (obj == null || !(obj instanceof AlibcCouponParams)) {
            alibcComponentCallback.onError("PARAM_ERROR", "参数错误");
        } else {
            sendRequest((AlibcCouponParams) obj, alibcComponentCallback);
        }
    }

    private boolean sendRequest(final AlibcCouponParams alibcCouponParams, final AlibcComponentCallback alibcComponentCallback) {
        if (alibcCouponParams.getCoupon().checkCouponParams()) {
            return AlibcContainerNetwork.getInstance().sendRequest(getRequest(alibcCouponParams), new AlibcContainerNetworkRequestListener() {
                public void onSuccess(int i, NetworkResponse networkResponse) {
                    AlibcComponentLog.d(AlibcCoupon.TAG, "success.");
                    alibcComponentCallback.onSuccess(alibcCouponParams.getCoupon().getSuccessMsg(networkResponse));
                    AlibcComponentTrack.sendUseabilitySuccess("BCPCSDK", alibcCouponParams.getCoupon().getMoniterPoint());
                }

                public void onError(int i, NetworkResponse networkResponse) {
                    StringBuilder sb = new StringBuilder("fail, ");
                    sb.append(networkResponse.errorCode);
                    sb.append("; ");
                    sb.append(networkResponse.errorMsg);
                    AlibcComponentLog.d(AlibcCoupon.TAG, sb.toString());
                    alibcComponentCallback.onError(networkResponse.errorCode, networkResponse.errorMsg);
                    AlibcCoupon.this.sendUseabilityFailure(networkResponse, alibcCouponParams.getCoupon().getMoniterPoint());
                }
            });
        }
        alibcComponentCallback.onError("PARAM_ERROR", "参数错误");
        return false;
    }

    private AlibcContainerNetworkRequest getRequest(AlibcCouponParams alibcCouponParams) {
        AlibcContainerNetworkRequest alibcContainerNetworkRequest = new AlibcContainerNetworkRequest();
        alibcContainerNetworkRequest.bizID = this.mBizId;
        alibcCouponParams.getCoupon().initRequestParam(alibcContainerNetworkRequest);
        return alibcContainerNetworkRequest;
    }

    /* access modifiers changed from: private */
    public void sendUseabilityFailure(NetworkResponse networkResponse, String str) {
        AlibcComponentTrack.sendUseabilityFailure("BCPCSDK", str, TextUtils.equals(networkResponse.errorCode, AlibcComponentTrack.MTOP_ERRNO_LOGIN_CANCEL) ? AlibcComponentTrack.ERRNO_COMPONENT_COUPON_CANCEL_LOGIN : AlibcComponentTrack.ERRNO_COMPONENT_COUPON_MTOP_FAIL, networkResponse.errorMsg);
    }
}
