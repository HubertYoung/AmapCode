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
import com.autonavi.minimap.search.model.CallTaxiResponse;
import java.lang.ref.WeakReference;

public class SearchResultCommand$1 implements AosResponseCallbackOnUi<AosStringResponse> {
    final /* synthetic */ WeakReference a;

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosStringResponse aosStringResponse = (AosStringResponse) aosResponse;
        if (aosStringResponse != null) {
            String str = (String) aosStringResponse.getResult();
            AMapLog.d("TAXI_COMPARATOR", str);
            if (!(TextUtils.isEmpty(str) || this.a == null || this.a.get() == null)) {
                try {
                    ((Callback) this.a.get()).callback((CallTaxiResponse) JSON.parseObject(str, CallTaxiResponse.class));
                } catch (Exception unused) {
                }
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        StringBuilder sb = new StringBuilder("error:");
        sb.append(aosResponseException.getMessage());
        AMapLog.d("ETA-TAXI_COMPARATOR", sb.toString());
        if (this.a != null && this.a.get() != null) {
            ((Callback) this.a.get()).error(aosResponseException, true);
        }
    }
}
