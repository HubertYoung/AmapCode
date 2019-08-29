package com.autonavi.map.search.template;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback;
import com.autonavi.minimap.search.model.ETAResponse;
import java.lang.ref.WeakReference;

public class SearchResultCommand$2 implements AosResponseCallbackOnUi<AosStringResponse> {
    final /* synthetic */ WeakReference a;

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosStringResponse aosStringResponse = (AosStringResponse) aosResponse;
        if (aosStringResponse != null) {
            String str = (String) aosStringResponse.getResult();
            AMapLog.d("ETA-Request", str);
            if (this.a != null) {
                Callback callback = (Callback) this.a.get();
                if (!TextUtils.isEmpty(str) && callback != null) {
                    callback.callback((ETAResponse) JSON.parseObject(str, ETAResponse.class));
                }
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        StringBuilder sb = new StringBuilder("error:");
        sb.append(aosResponseException.getMessage());
        AMapLog.d("ETA-Request", sb.toString());
        if (this.a != null && this.a.get() != null) {
            ((Callback) this.a.get()).error(aosResponseException, true);
        }
    }
}
