package com.alipay.inside.android.phone.mrpc.core.gwprotocol;

import com.alipay.inside.android.phone.mrpc.core.RpcException;

public interface Serializer {
    byte[] packet() throws RpcException;

    void setExtParam(Object obj) throws RpcException;
}
