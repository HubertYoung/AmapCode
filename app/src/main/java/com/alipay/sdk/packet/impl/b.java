package com.alipay.sdk.packet.impl;

import com.alipay.sdk.packet.d;
import com.tencent.open.utils.SystemUtils;
import org.json.JSONException;
import org.json.JSONObject;

public final class b extends d {
    public final String b() {
        return SystemUtils.QQ_VERSION_NAME_5_0_0;
    }

    public final JSONObject a() throws JSONException {
        return d.a((String) "sdkConfig", (String) "obtain");
    }
}
