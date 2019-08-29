package com.alibaba.baichuan.android.trade.a;

import com.alibaba.baichuan.android.trade.adapter.mtop.AlibcMtop;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkClient.NetworkRequestListener;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkRequest;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import java.util.Map;

public abstract class c {
    protected String a = "";
    protected String b = "1.0";
    protected boolean c = false;
    protected Boolean d = Boolean.FALSE;
    protected boolean e = false;
    protected boolean f = true;
    protected int g = -1;

    private NetworkRequest b(Map map) {
        NetworkRequest networkRequest = new NetworkRequest();
        networkRequest.apiName = this.a;
        networkRequest.apiVersion = this.b;
        networkRequest.needLogin = this.c;
        networkRequest.needWua = this.d.booleanValue();
        networkRequest.needAuth = this.e;
        networkRequest.isPost = this.f;
        networkRequest.timeOut = this.g;
        networkRequest.requestType = networkRequest.hashCode();
        networkRequest.paramMap = map;
        return networkRequest;
    }

    public NetworkResponse a(Map map) {
        return AlibcMtop.getInstance().sendRequest(b(map));
    }

    public boolean a(Map map, NetworkRequestListener networkRequestListener) {
        return AlibcMtop.getInstance().sendRequest(networkRequestListener, b(map));
    }
}
