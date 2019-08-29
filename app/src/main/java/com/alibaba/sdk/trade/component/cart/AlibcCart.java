package com.alibaba.sdk.trade.component.cart;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.baichuan.android.trade.callback.AlibcFailureCallback;
import com.alibaba.baichuan.android.trade.component.AlibcTaokeComponent;
import com.alibaba.baichuan.android.trade.utils.json.JSONUtils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.sdk.trade.container.AlibcBaseComponent;
import com.alibaba.sdk.trade.container.AlibcComponentCallback;
import com.alibaba.sdk.trade.container.network.AlibcContainerNetwork;
import com.alibaba.sdk.trade.container.network.AlibcContainerNetworkRequest;
import com.alibaba.sdk.trade.container.network.AlibcContainerNetworkRequestListener;
import com.alibaba.sdk.trade.container.utils.AlibcComponentLog;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;
import com.alibaba.sdk.want.AlibcWantConstant;
import java.io.Serializable;
import java.util.HashMap;

public class AlibcCart extends AlibcBaseComponent {
    private static final String TAG = "AlibcCart";
    public static AlibcCart instance = new AlibcCart();

    public int getType() {
        return 1;
    }

    public String getWantName() {
        return "BCWantCart";
    }

    private AlibcCart() {
        this.mBizId = "BC_WantAddCart";
        this.mHintList.add(Integer.valueOf(1));
    }

    public void execute(Object obj, AlibcComponentCallback alibcComponentCallback) {
        if (obj == null || !(obj instanceof AlibcCartParams)) {
            alibcComponentCallback.onError("PARAM_ERROR", "参数错误");
        } else {
            sendRequest((AlibcCartParams) obj, alibcComponentCallback);
        }
    }

    private boolean sendRequest(final AlibcCartParams alibcCartParams, final AlibcComponentCallback alibcComponentCallback) {
        if (!checkParams(alibcCartParams)) {
            if (alibcComponentCallback != null) {
                alibcComponentCallback.onError(AlibcWantConstant.WANT_ADDCART_FAIL, "params error");
            }
            return false;
        }
        asyncTaokeTrace(alibcCartParams);
        return AlibcContainerNetwork.getInstance().sendRequest(getRequest(alibcCartParams), new AlibcContainerNetworkRequestListener() {
            public void onSuccess(int i, NetworkResponse networkResponse) {
                alibcComponentCallback.onSuccess(null);
                AlibcComponentTrack.sentUserTrack(2101, AlibcComponentTrack.UT_CONTROL_NAME_WANTCART_PRESS, alibcCartParams.mYbhpssParams, alibcCartParams.mItemID);
                AlibcComponentTrack.sendUseabilitySuccess("BCPCSDK", "addCart");
            }

            public void onError(int i, NetworkResponse networkResponse) {
                alibcComponentCallback.onError(networkResponse.errorCode, networkResponse.errorMsg);
                AlibcCart.this.sendUseabilityFailure(networkResponse);
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendUseabilityFailure(NetworkResponse networkResponse) {
        String str = TextUtils.equals(networkResponse.errorCode, AlibcComponentTrack.MTOP_ERRNO_LOGIN_CANCEL) ? AlibcComponentTrack.ERRNO_COMPONENT_CART_CANCEL_LOGIN : TextUtils.equals(networkResponse.errorCode, AlibcComponentTrack.MTOP_ERRNO_AUTH_CANCEL) ? AlibcComponentTrack.ERRNO_COMPONENT_CART_CANCEL_AUTH : AlibcComponentTrack.ERRNO_COMPONENT_CART_MTOP_FAIL;
        AlibcComponentTrack.sendUseabilityFailure("BCPCSDK", "addCart", str, networkResponse.errorMsg);
    }

    private AlibcContainerNetworkRequest getRequest(AlibcCartParams alibcCartParams) {
        AlibcComponentTrack.preprocessYbhpss(alibcCartParams.mYbhpssParams, AlibcComponentTrack.LABEL_VALUE_FOR_YBHPSS_CART_API, false);
        AlibcContainerNetworkRequest alibcContainerNetworkRequest = new AlibcContainerNetworkRequest();
        alibcContainerNetworkRequest.apiName = "mtop.taobao.trade.open.addbag";
        alibcContainerNetworkRequest.apiVersion = "3.1";
        alibcContainerNetworkRequest.needLogin = true;
        alibcContainerNetworkRequest.needAuth = true;
        alibcContainerNetworkRequest.isPost = true;
        alibcContainerNetworkRequest.needWua = false;
        alibcContainerNetworkRequest.bizID = this.mBizId;
        alibcContainerNetworkRequest.timeOut = 90000;
        alibcContainerNetworkRequest.requestType = alibcContainerNetworkRequest.hashCode();
        alibcContainerNetworkRequest.paramMap = initParam4Mtop(alibcCartParams);
        return alibcContainerNetworkRequest;
    }

    public boolean checkParams(AlibcCartParams alibcCartParams) {
        String str;
        boolean z = false;
        if (alibcCartParams.mItemID == null) {
            str = "itemid为null";
        } else if (alibcCartParams.mYbhpssParams == null) {
            str = "ybhpss参数为null";
        } else if (alibcCartParams.mTaokeParams == null) {
            str = "淘客参数为null";
        } else {
            z = true;
            str = null;
        }
        if (!z) {
            AlibcLogger.e(TAG, str);
            AlibcComponentTrack.sendUseabilityFailure("BCPCSDK", "addCart", AlibcComponentTrack.ERRNO_COMPONENT_CART_PARM, str);
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public void asyncTaokeTrace(AlibcCartParams alibcCartParams) {
        HashMap hashMap = new HashMap();
        hashMap.put("itemId", alibcCartParams.mItemID);
        AlibcTaokeComponent.INSTANCE.asyncTaokeTrace(hashMap, alibcCartParams.mTaokeParams, new AlibcFailureCallback() {
            public void onFailure(int i, String str) {
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append(", ");
                sb.append(str);
                AlibcComponentLog.d(AlibcCart.TAG, sb.toString());
            }
        });
    }

    /* access modifiers changed from: protected */
    public HashMap<String, Serializable> initParam4Mtop(AlibcCartParams alibcCartParams) {
        HashMap<String, Serializable> hashMap = new HashMap<>();
        hashMap.put("itemId", alibcCartParams.mItemID);
        hashMap.put("quantity", Integer.valueOf(1));
        HashMap hashMap2 = new HashMap();
        hashMap2.put("item_id", alibcCartParams.mItemID);
        HashMap hashMap3 = new HashMap();
        hashMap3.put("ybhpss", AlibcComponentTrack.createYbhpss(alibcCartParams.mYbhpssParams, hashMap2));
        hashMap.put("exParams", JSONUtils.toJsonObject(hashMap3).toString());
        return hashMap;
    }
}
