package com.alipay.mobile.mascanengine;

import com.alipay.mobile.bqcscanservice.BQCScanEngine.EngineCallback;

public interface MaScanCallback extends EngineCallback {
    void onResultMa(MultiMaScanResult multiMaScanResult);
}
