package com.alipay.mobile.common.transportext.biz.spdy.longlink;

import android.content.Context;
import android.content.Intent;

public interface ISpyLongLink {
    void asynConnect();

    boolean connect();

    void disconnect();

    boolean isConnected();

    void notifyNetworkConnectivity(Context context, Intent intent);

    void notifyPingResponse();

    boolean register(ISpdyCallBack iSpdyCallBack);

    boolean unregister();

    int writeData(byte[] bArr);
}
