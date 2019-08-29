package com.alipay.mobile.beehive.rpc.lifecycle;

import com.alipay.mobile.beehive.rpc.RpcRunnerContext;

public interface RpcRunnerLifeCycleCallback {
    void onAfterRpc(RpcRunnerContext rpcRunnerContext);

    void onBeforeRpc(RpcRunnerContext rpcRunnerContext);

    void onRpcException(RpcRunnerContext rpcRunnerContext, Exception exc);

    void onRpcResult(RpcRunnerContext rpcRunnerContext, Object obj);
}
