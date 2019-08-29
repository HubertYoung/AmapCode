package com.autonavi.minimap.route.sharebike.presenter;

import com.autonavi.common.Callback;

public class ShareBikePresenter$22 implements Callback<Boolean> {
    final /* synthetic */ ehd a;

    public void error(Throwable th, boolean z) {
    }

    public ShareBikePresenter$22(ehd ehd) {
        this.a = ehd;
    }

    public void callback(Boolean bool) {
        if (bool.booleanValue()) {
            ehd.au(this.a);
        }
    }
}
