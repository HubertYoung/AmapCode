package com.alipay.android.phone.inside.offlinecode.engine;

public interface JSEngineCallback {
    void onComplete(String str);

    void onError(Throwable th);
}
