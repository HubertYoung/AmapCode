package com.autonavi.minimap.ajx3.platform.impl;

import com.autonavi.minimap.ajx3.platform.ackor.IHttpRequest;
import com.autonavi.minimap.ajx3.platform.ackor.INetworkMonitor;
import com.autonavi.minimap.ajx3.platform.ackor.INetworkService;

public class NetworkServiceImpl implements INetworkService {
    private INetworkMonitor mNetworkMonitor = new NetworkMonitor();

    public INetworkMonitor getNetworkMonitorInstance() {
        return this.mNetworkMonitor;
    }

    public IHttpRequest createHttpRequest() {
        return new HttpRequestImpl();
    }

    public void destroyHttpRequest(IHttpRequest iHttpRequest) {
        if (iHttpRequest != null) {
            iHttpRequest.cancel();
        }
    }
}
