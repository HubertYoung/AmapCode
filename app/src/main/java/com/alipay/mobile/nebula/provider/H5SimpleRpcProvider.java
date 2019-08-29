package com.alipay.mobile.nebula.provider;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.callback.H5SimpleRpcListener;

public interface H5SimpleRpcProvider {
    void sendSimpleRpc(String str, String str2, String str3, boolean z, JSONObject jSONObject, String str4, boolean z2, H5Page h5Page, H5SimpleRpcListener h5SimpleRpcListener);
}
