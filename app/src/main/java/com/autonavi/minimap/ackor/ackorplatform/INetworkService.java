package com.autonavi.minimap.ackor.ackorplatform;

public interface INetworkService {
    IHttpRequest createHttpRequest();

    void destroyHttpRequest(IHttpRequest iHttpRequest);

    INetworkMonitor getNetworkMonitorInstance();
}
