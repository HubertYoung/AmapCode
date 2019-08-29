package com.sina.weibo.sdk.network.target;

import com.sina.weibo.sdk.network.base.WbResponse;

public abstract class SimpleTarget extends BaseTarget {
    public abstract void onSuccess(String str);

    public String transResponse(WbResponse wbResponse) throws Exception {
        return wbResponse.body().string();
    }

    public final void onRequestSuccess(Object obj) {
        onSuccess(String.valueOf(obj));
    }
}
