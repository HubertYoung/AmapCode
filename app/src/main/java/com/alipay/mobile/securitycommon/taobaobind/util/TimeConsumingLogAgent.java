package com.alipay.mobile.securitycommon.taobaobind.util;

import com.alipay.inside.android.phone.mrpc.core.RpcException;

public class TimeConsumingLogAgent {
    private long a;
    private long b;

    public static void logRpcException(TimeConsumingLogAgent timeConsumingLogAgent, RpcException rpcException) {
    }

    public TimeConsumingLogAgent addExtParam(String str, String str2) {
        return this;
    }

    public TimeConsumingLogAgent addPara2(String str) {
        return this;
    }

    public TimeConsumingLogAgent addParam1(String str) {
        return this;
    }

    public TimeConsumingLogAgent addParam3(String str) {
        return this;
    }

    public void commit() {
    }

    public TimeConsumingLogAgent logFacade(String str) {
        return this;
    }

    public TimeConsumingLogAgent logSeedID(String str) {
        return this;
    }

    public TimeConsumingLogAgent(String str) {
    }

    public TimeConsumingLogAgent(String str, String str2) {
    }

    public TimeConsumingLogAgent logStart() {
        this.a = System.currentTimeMillis();
        return this;
    }

    public TimeConsumingLogAgent logEnd() {
        this.b = System.currentTimeMillis() - this.a;
        return this;
    }
}
