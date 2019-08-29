package com.alipay.inside.android.phone.mrpc.core;

public interface RpcCaller {
    Object call() throws RpcException;
}
