package com.alipay.mobile.nebula.provider;

import android.os.Bundle;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;

public interface H5PreRpcProvider {
    void clearPreAll();

    void clearPreState(String str);

    boolean enableUsePreRpc(Bundle bundle);

    boolean getPreFlag(String str);

    JSONObject getResult(String str, H5BridgeContext h5BridgeContext);

    void handleResultPool(String str, int i);

    void handleResultPool(String str, JSONObject jSONObject);

    void preRpc();

    void setStartParams(Bundle bundle);
}
