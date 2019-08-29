package com.autonavi.minimap.bl.net;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface IHttpResponseCallback {
    void onCanceled(HttpResponse httpResponse);

    void onFailed(HttpResponse httpResponse);

    void onReceiveBody(HttpResponse httpResponse);

    void onReceiveHeader(HttpResponse httpResponse);

    void onSuccess(HttpResponse httpResponse);
}
