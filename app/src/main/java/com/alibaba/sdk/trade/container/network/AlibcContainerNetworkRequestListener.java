package com.alibaba.sdk.trade.container.network;

import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;

public interface AlibcContainerNetworkRequestListener {
    void onError(int i, NetworkResponse networkResponse);

    void onSuccess(int i, NetworkResponse networkResponse);
}
