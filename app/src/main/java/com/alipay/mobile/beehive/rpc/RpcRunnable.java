package com.alipay.mobile.beehive.rpc;

public interface RpcRunnable<ResultType> {
    ResultType execute(Object... objArr);
}
