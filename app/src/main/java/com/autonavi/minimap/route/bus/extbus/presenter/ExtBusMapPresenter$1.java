package com.autonavi.minimap.route.bus.extbus.presenter;

import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.bus.extbus.page.ExtBusMapPage;

public class ExtBusMapPresenter$1 implements Callback<ctm> {
    final /* synthetic */ dvd a;

    public void error(Throwable th, boolean z) {
    }

    public ExtBusMapPresenter$1(dvd dvd) {
        this.a = dvd;
    }

    public void callback(final ctm ctm) {
        aho.a(new Runnable() {
            public final void run() {
                if (ctm != null && ExtBusMapPresenter$1.this.a.mPage != null && ((ExtBusMapPage) ExtBusMapPresenter$1.this.a.mPage).isStarted()) {
                    ExtBusMapPresenter$1.this.a.h.a((AbstractBasePage) ExtBusMapPresenter$1.this.a.mPage, "14", ctm.c);
                }
            }
        }, 1000);
    }
}
