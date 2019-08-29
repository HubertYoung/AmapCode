package com.autonavi.minimap.ajx3.platform.ackor;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface INetworkService {
    IHttpRequest createHttpRequest();

    void destroyHttpRequest(IHttpRequest iHttpRequest);

    INetworkMonitor getNetworkMonitorInstance();
}
