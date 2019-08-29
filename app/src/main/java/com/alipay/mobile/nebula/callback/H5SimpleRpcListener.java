package com.alipay.mobile.nebula.callback;

public interface H5SimpleRpcListener {
    void onFailed(int i, String str);

    void onSuccess(String str);
}
