package com.ali.auth.third.core.ut;

import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.RpcRequest;
import com.ali.auth.third.core.service.RpcService;
import org.json.JSONObject;

class a implements Runnable {
    final /* synthetic */ JSONObject a;
    final /* synthetic */ UserTracer b;

    a(UserTracer userTracer, JSONObject jSONObject) {
        this.b = userTracer;
        this.a = jSONObject;
    }

    public void run() {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.target = "mtop.taobao.havana.mlogin.userTracerLog";
        rpcRequest.version = "1.0";
        rpcRequest.addParam("userTracerInfo", this.a);
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        rpcRequest.addParam("ts", sb.toString());
        ((RpcService) KernelContext.getService(RpcService.class)).invoke(rpcRequest, String.class);
    }
}
