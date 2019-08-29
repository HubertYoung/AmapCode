package com.autonavi.minimap.route.train.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.train.page.TrainPlanListPage;

public class TrainPlanListPresenter$2 implements Callback<ctm> {
    final /* synthetic */ ejm a;

    public void error(Throwable th, boolean z) {
    }

    public TrainPlanListPresenter$2(ejm ejm) {
        this.a = ejm;
    }

    public void callback(ctm ctm) {
        if (ctm != null && this.a.mPage != null && ((TrainPlanListPage) this.a.mPage).isStarted()) {
            this.a.d.a((AbstractBasePage) this.a.mPage, "15", ctm.c);
        }
    }
}
