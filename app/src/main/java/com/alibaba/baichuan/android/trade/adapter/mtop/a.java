package com.alibaba.baichuan.android.trade.adapter.mtop;

import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkClient.NetworkRequestListener;
import com.taobao.tao.remotebusiness.IRemoteBaseListener;
import mtopsdk.mtop.domain.BaseOutDo;
import mtopsdk.mtop.domain.MtopResponse;

class a implements IRemoteBaseListener {
    final /* synthetic */ NetworkRequestListener a;
    final /* synthetic */ NetworkRequest b;
    final /* synthetic */ AlibcMtop c;

    a(AlibcMtop alibcMtop, NetworkRequestListener networkRequestListener, NetworkRequest networkRequest) {
        this.c = alibcMtop;
        this.a = networkRequestListener;
        this.b = networkRequest;
    }

    public void onError(int i, MtopResponse mtopResponse, Object obj) {
        this.c.a(mtopResponse);
        this.a.onError(this.b.requestType, this.c.b(mtopResponse));
    }

    public void onSuccess(int i, MtopResponse mtopResponse, BaseOutDo baseOutDo, Object obj) {
        this.c.a(mtopResponse);
        this.a.onSuccess(this.b.requestType, this.c.b(mtopResponse));
    }

    public void onSystemError(int i, MtopResponse mtopResponse, Object obj) {
        this.c.a(mtopResponse);
        this.a.onError(this.b.requestType, this.c.b(mtopResponse));
    }
}
