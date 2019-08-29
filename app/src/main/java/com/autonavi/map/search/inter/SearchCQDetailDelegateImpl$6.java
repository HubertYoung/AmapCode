package com.autonavi.map.search.inter;

import com.autonavi.common.Callback;

public class SearchCQDetailDelegateImpl$6 implements Callback<Integer> {
    final /* synthetic */ a a;
    final /* synthetic */ byk b;

    public void error(Throwable th, boolean z) {
    }

    public SearchCQDetailDelegateImpl$6(byk byk, a aVar) {
        this.b = byk;
        this.a = aVar;
    }

    public void callback(Integer num) {
        this.b.c.reset();
        this.b.c.setFromPageID(10001);
    }
}
