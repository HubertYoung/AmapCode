package com.autonavi.minimap.route.sharebike.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;

public class ShareRidingFinishPresenter$6 implements Callback<ctm> {
    final /* synthetic */ ehf a;

    public void error(Throwable th, boolean z) {
    }

    public ShareRidingFinishPresenter$6(ehf ehf) {
        this.a = ehf;
    }

    public void callback(final ctm ctm) {
        aho.a(new Runnable() {
            public final void run() {
                if (ctm != null && ShareRidingFinishPresenter$6.this.a.mPage != null && ((ShareRidingFinishPage) ShareRidingFinishPresenter$6.this.a.mPage).isStarted()) {
                    ShareRidingFinishPresenter$6.this.a.u = true;
                    ShareRidingFinishPresenter$6.this.a.r.a((AbstractBasePage) ShareRidingFinishPresenter$6.this.a.mPage, "18", ctm.c);
                }
            }
        }, 1000);
    }
}
