package com.alipay.mobile.beehive.rpc;

import com.alipay.mobile.common.rpc.RpcInvokeContext;

public class RpcRunnerContext {
    private RpcRunner rpcRunner;

    public void setRpcRunner(RpcRunner r) {
        this.rpcRunner = r;
    }

    public RpcInvokeContext getRpcInvokeContext() {
        return this.rpcRunner.getRpcInvokeContext();
    }

    public RpcTask getRpcTask() {
        return this.rpcRunner.getRpcTask();
    }

    public Object getUiHost() {
        if (this.rpcRunner.getRpcSubscriber() == null || this.rpcRunner.getRpcSubscriber().getRpcUiProcessor() == null) {
            return null;
        }
        return this.rpcRunner.getRpcSubscriber().getRpcUiProcessor().getUiHost();
    }
}
