package com.autonavi.map.search.callback;

import com.amap.bundle.searchservice.callback.AbsSearchCallBack;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;

public class SearchCallBackEx$2 extends AbsSearchCallBack {
    final /* synthetic */ bvt a;

    public void callback(InfoliteResult infoliteResult) {
        this.a.callback(infoliteResult);
    }

    public void error(Throwable th, boolean z) {
        this.a.error(-1);
    }

    public void error(int i, String str) {
        this.a.error(i);
    }
}
