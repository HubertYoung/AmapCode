package com.autonavi.minimap.route.run.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.run.page.RunFinishMapPage;

public class RunFinishMapPresenter$1 implements Callback<ctm> {
    final /* synthetic */ efn a;

    public void error(Throwable th, boolean z) {
    }

    public RunFinishMapPresenter$1(efn efn) {
        this.a = efn;
    }

    public void callback(final ctm ctm) {
        aho.a(new Runnable() {
            public final void run() {
                if (ctm != null && RunFinishMapPresenter$1.this.a.mPage != null && ((RunFinishMapPage) RunFinishMapPresenter$1.this.a.mPage).isStarted()) {
                    RunFinishMapPresenter$1.this.a.m.a((AbstractBasePage) RunFinishMapPresenter$1.this.a.mPage, "10", ctm.c);
                }
            }
        }, 1000);
    }
}
