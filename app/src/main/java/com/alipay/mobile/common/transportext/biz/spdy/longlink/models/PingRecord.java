package com.alipay.mobile.common.transportext.biz.spdy.longlink.models;

import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.Ping;
import java.util.concurrent.Future;

public class PingRecord {
    public Ping ping;
    public long pingTime;
    public boolean responsed = false;
    public Future<?> sendPingThreadFuture;
}
