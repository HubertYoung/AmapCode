package com.alipay.mobile.beehive.photo.data;

import com.alipay.mobile.beehive.rpc.RpcRunnable;

public abstract class PhotoRpcRunnable implements RpcRunnable<PhotoResult> {
    public abstract PhotoResult execute(Object... objArr);
}
