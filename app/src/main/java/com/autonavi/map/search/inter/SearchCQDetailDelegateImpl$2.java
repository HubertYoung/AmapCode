package com.autonavi.map.search.inter;

import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;

public class SearchCQDetailDelegateImpl$2 implements Callback<Integer> {
    final /* synthetic */ PageBundle a;
    final /* synthetic */ a b;
    final /* synthetic */ byk c;

    public void error(Throwable th, boolean z) {
    }

    public SearchCQDetailDelegateImpl$2(byk byk, PageBundle pageBundle, a aVar) {
        this.c = byk;
        this.a = pageBundle;
        this.b = aVar;
    }

    public void callback(Integer num) {
        if (this.c.b != null) {
            this.c.b.initData(null, (POI) this.a.getObject("POI"), 2);
        }
    }
}
