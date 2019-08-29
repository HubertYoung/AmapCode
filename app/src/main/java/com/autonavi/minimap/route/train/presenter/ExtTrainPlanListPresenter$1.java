package com.autonavi.minimap.route.train.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public class ExtTrainPlanListPresenter$1 implements Callback<ctm> {
    final /* synthetic */ ejj a;

    public void error(Throwable th, boolean z) {
    }

    public ExtTrainPlanListPresenter$1(ejj ejj) {
        this.a = ejj;
    }

    public void callback(ctm ctm) {
        if (ctm != null) {
            this.a.a.a((AbstractBasePage) this.a.mPage, "15", ctm.c);
        }
    }
}
