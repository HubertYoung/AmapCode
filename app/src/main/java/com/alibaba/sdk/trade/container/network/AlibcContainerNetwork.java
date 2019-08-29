package com.alibaba.sdk.trade.container.network;

import com.alibaba.baichuan.android.trade.adapter.mtop.AlibcMtop;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkClient.NetworkRequestListener;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;

public class AlibcContainerNetwork {

    static class NetworkHolder {
        public static AlibcContainerNetwork instance = new AlibcContainerNetwork();

        private NetworkHolder() {
        }
    }

    public static AlibcContainerNetwork getInstance() {
        return NetworkHolder.instance;
    }

    private AlibcContainerNetwork() {
    }

    public boolean sendRequest(AlibcContainerNetworkRequest alibcContainerNetworkRequest, final AlibcContainerNetworkRequestListener alibcContainerNetworkRequestListener) {
        AlibcMtop instance = AlibcMtop.getInstance();
        alibcContainerNetworkRequest.authParams = alibcContainerNetworkRequest.bizID;
        return instance.sendRequest(new NetworkRequestListener() {
            public void onSuccess(int i, NetworkResponse networkResponse) {
                alibcContainerNetworkRequestListener.onSuccess(i, networkResponse);
            }

            public void onError(int i, NetworkResponse networkResponse) {
                alibcContainerNetworkRequestListener.onError(i, networkResponse);
            }
        }, alibcContainerNetworkRequest);
    }

    public NetworkResponse sendRequest(AlibcContainerNetworkRequest alibcContainerNetworkRequest) {
        AlibcMtop instance = AlibcMtop.getInstance();
        alibcContainerNetworkRequest.authParams = alibcContainerNetworkRequest.bizID;
        return instance.sendRequest(alibcContainerNetworkRequest);
    }
}
