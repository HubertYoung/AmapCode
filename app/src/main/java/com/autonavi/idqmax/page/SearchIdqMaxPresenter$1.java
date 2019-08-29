package com.autonavi.idqmax.page;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public class SearchIdqMaxPresenter$1 implements Callback<ctm> {
    final /* synthetic */ bqm a;

    public void error(Throwable th, boolean z) {
    }

    public SearchIdqMaxPresenter$1(bqm bqm) {
        this.a = bqm;
    }

    public void callback(final ctm ctm) {
        aho.a(new Runnable() {
            public final void run() {
                if (ctm != null && SearchIdqMaxPresenter$1.this.a.mPage != null && ((SearchIdqMaxPage) SearchIdqMaxPresenter$1.this.a.mPage).isStarted()) {
                    SearchIdqMaxPresenter$1.this.a.d.a((AbstractBasePage) SearchIdqMaxPresenter$1.this.a.mPage, "17", ctm.c);
                }
            }
        }, 200);
    }
}
