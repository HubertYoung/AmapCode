package com.alipay.mobile.common.transportext.biz.spdy.longlink;

import android.content.Context;
import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.Ping;
import java.util.concurrent.Future;

public interface SpdyLongLinkConnManager {
    public static final int IO_TYPE_INPUT = 0;
    public static final int IO_TYPE_OUTPUT = 1;

    void asynConnect();

    void attch(Context context);

    void closeConnection();

    boolean connect();

    Connection getConnection();

    boolean isNetworkActive();

    Future<?> justPing();

    void notifyNetworkActive(int i);

    void notifyPingResponse(Ping ping);

    void resetPingStartTime();

    void startPing();
}
