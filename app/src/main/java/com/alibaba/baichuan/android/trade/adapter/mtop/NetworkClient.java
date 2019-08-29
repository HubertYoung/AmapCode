package com.alibaba.baichuan.android.trade.adapter.mtop;

public interface NetworkClient {

    public interface NetworkRequestListener {
        void onError(int i, NetworkResponse networkResponse);

        void onSuccess(int i, NetworkResponse networkResponse);
    }

    NetworkResponse sendRequest(NetworkRequest networkRequest);

    boolean sendRequest(NetworkRequestListener networkRequestListener, NetworkRequest networkRequest);
}
