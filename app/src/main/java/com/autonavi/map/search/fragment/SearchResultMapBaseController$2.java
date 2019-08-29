package com.autonavi.map.search.fragment;

import com.autonavi.common.Callback;

public class SearchResultMapBaseController$2 implements Callback<ctm> {
    final /* synthetic */ bxo a;

    public void error(Throwable th, boolean z) {
    }

    public SearchResultMapBaseController$2(bxo bxo) {
        this.a = bxo;
    }

    public void callback(final ctm ctm) {
        aho.a(new Runnable() {
            public final void run() {
                if (ctm != null && SearchResultMapBaseController$2.this.a.f != null && SearchResultMapBaseController$2.this.a.f.isStarted()) {
                    SearchResultMapBaseController$2.this.a.ab.a(SearchResultMapBaseController$2.this.a.f, "17", ctm.c);
                }
            }
        }, 200);
    }
}
