package com.autonavi.minimap.route.sharebike.presenter;

import android.text.TextUtils;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.sharebike.page.ShareBikePage;

public class ShareBikePresenter$19 implements Callback<ctm> {
    final /* synthetic */ ehd a;

    public void error(Throwable th, boolean z) {
    }

    public ShareBikePresenter$19(ehd ehd) {
        this.a = ehd;
    }

    public void callback(final ctm ctm) {
        aho.a(new Runnable() {
            public final void run() {
                if (ShareBikePresenter$19.this.a.mPage != null && ((ShareBikePage) ShareBikePresenter$19.this.a.mPage).isStarted() && ctm != null && !TextUtils.isEmpty(ctm.c)) {
                    ShareBikePresenter$19.this.a.D = true;
                    ShareBikePresenter$19.this.a.H.a((AbstractBasePage) ShareBikePresenter$19.this.a.mPage, "19", ctm.c);
                }
            }
        }, 1000);
    }
}
