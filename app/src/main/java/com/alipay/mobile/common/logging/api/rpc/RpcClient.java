package com.alipay.mobile.common.logging.api.rpc;

import android.os.Bundle;

public interface RpcClient {
    LogRpcResult uploadLog(RpcLogRequestParam rpcLogRequestParam, String str, Bundle bundle);
}
