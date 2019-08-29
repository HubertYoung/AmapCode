package com.autonavi.map.search.page.refactor;

import com.amap.bundle.searchservice.callback.AbsSearchCallBack;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;

public class SearchPageRequestBuilder$1 extends AbsSearchCallBack {
    final /* synthetic */ bvt a;

    public void callback(InfoliteResult infoliteResult) {
        this.a.callback(infoliteResult);
    }

    public void error(Throwable th, boolean z) {
        error(-1, (String) "");
    }

    public void error(int i, String str) {
        this.a.error(i);
    }
}
