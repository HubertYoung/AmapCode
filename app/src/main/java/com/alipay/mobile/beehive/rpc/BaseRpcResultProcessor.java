package com.alipay.mobile.beehive.rpc;

public abstract class BaseRpcResultProcessor<ResultType> {
    public abstract boolean isSuccess(ResultType resulttype);

    public String convertResultText(ResultType result) {
        return "";
    }

    public boolean isEmpty(ResultType result) {
        return false;
    }
}
