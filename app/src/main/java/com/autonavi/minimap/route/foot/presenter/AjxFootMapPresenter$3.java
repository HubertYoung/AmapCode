package com.autonavi.minimap.route.foot.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.ajx3.Ajx3Page;

public class AjxFootMapPresenter$3 implements Callback<ctm> {
    final /* synthetic */ ecq a;

    public void error(Throwable th, boolean z) {
    }

    public AjxFootMapPresenter$3(ecq ecq) {
        this.a = ecq;
    }

    public void callback(ctm ctm) {
        if (ctm != null && this.a.mPage != null && ((Ajx3Page) this.a.mPage).isStarted()) {
            this.a.d.a((AbstractBasePage) this.a.mPage, "11", ctm.c);
            eba.a().a = false;
        }
    }
}
