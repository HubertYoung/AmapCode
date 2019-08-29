package com.alipay.android.phone.mobilecommon.rpc;

import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.security.bio.service.local.rpc.IRpcException;

/* compiled from: BioRpcException */
public final class b extends RpcException implements IRpcException {
    public b(RpcException rpcException) {
        super(Integer.valueOf(rpcException.getCode()), rpcException.getMsg());
    }
}
