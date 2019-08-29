package com.alipay.mobile.beehive.rpc;

import com.alipay.mobile.beehive.util.MiscUtil;

public class RpcEvent<ResultType> {
    public Exception exception;
    public ResultType result;
    public RpcTask<ResultType> rpcTask;
    public String status;

    public RpcEvent(RpcTask<ResultType> rpcTask2, ResultType result2, Exception exception2) {
        this.rpcTask = rpcTask2;
        this.result = result2;
        this.exception = exception2;
    }

    public String getStatus() {
        return this.status;
    }

    public String toString() {
        return MiscUtil.safeToString(this, "status=" + this.status + ",runnable=" + RpcUtil.getRpcRunnableLogString(this.rpcTask) + ",result=[*hide*]");
    }

    public RpcTask<ResultType> getRpcTask() {
        return this.rpcTask;
    }

    public ResultType getResult() {
        return this.result;
    }
}
