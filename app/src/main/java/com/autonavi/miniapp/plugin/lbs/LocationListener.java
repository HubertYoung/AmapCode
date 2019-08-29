package com.autonavi.miniapp.plugin.lbs;

import com.alibaba.fastjson.JSONObject;

public interface LocationListener {
    void onLocationResult(JSONObject jSONObject, int i);
}
