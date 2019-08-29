package com.alipay.inside.android.phone.mrpc.core.gwprotocol;

import com.alipay.inside.android.phone.mrpc.core.RpcException;

public interface Deserializer {
    Object parser() throws RpcException;
}
