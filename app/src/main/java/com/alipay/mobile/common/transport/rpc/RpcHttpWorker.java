package com.alipay.mobile.common.transport.rpc;

import com.alipay.mobile.common.transport.http.HttpManager;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpWorker;
import com.alipay.mobile.common.transport.http.selfencrypt.ClientRpcPack;

public class RpcHttpWorker extends HttpWorker {
    public RpcHttpWorker(HttpManager httpManager, HttpUrlRequest request) {
        super(httpManager, request);
        if (isUseSelfEncrypt()) {
            this.clientRpcPack = new ClientRpcPack();
        }
    }
}
