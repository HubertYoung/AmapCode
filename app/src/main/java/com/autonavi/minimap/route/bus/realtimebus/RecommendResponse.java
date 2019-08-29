package com.autonavi.minimap.route.bus.realtimebus;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.amap.bundle.aosservice.response.AosResponse;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommendStation;

public class RecommendResponse extends AosResponse<RecommendStation> {
    private RecommendStation a = null;

    public /* synthetic */ Object parseResult() {
        String responseBodyString = getResponseBodyString();
        if (!TextUtils.isEmpty(responseBodyString)) {
            this.a = (RecommendStation) JSON.parseObject(responseBodyString, RecommendStation.class);
        }
        return this.a;
    }
}
