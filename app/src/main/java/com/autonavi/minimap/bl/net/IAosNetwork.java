package com.autonavi.minimap.bl.net;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IAosNetwork {
    void cancel(int i);

    void cancelGroup(int i);

    int send(AosRequest aosRequest, IHttpResponseCallback iHttpResponseCallback);

    int send(AosRequest aosRequest, IHttpResponseCallback iHttpResponseCallback, int i);
}
