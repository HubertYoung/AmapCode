package com.autonavi.minimap.nativesupport.platform;

import com.autonavi.minimap.ackor.ackorplatform.IHttpRequest;
import com.autonavi.minimap.ackor.ackorplatform.INetworkMonitor;
import com.autonavi.minimap.ackor.ackorplatform.INetworkService;

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
