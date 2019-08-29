package com.autonavi.minimap.bl.net;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IHttpNetwork {
    void cancel(int i);

    void cancelGroup(int i);

    int send(HttpRequest httpRequest, IHttpResponseCallback iHttpResponseCallback);

    int send(HttpRequest httpRequest, IHttpResponseCallback iHttpResponseCallback, int i);
}
