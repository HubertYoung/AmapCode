package com.alipay.mobile.liteprocess.rpc;

import com.alipay.mobile.common.rpc.Config;
import com.alipay.mobile.common.rpc.RpcFactory;

public class LiteRpcFactory extends RpcFactory {
    public LiteRpcFactory(Config config) {
        super(config);
        this.mRpcInvoker = new RpcCallClientInvoker(this);
    }
}
