package com.autonavi.map.search.manager;

import com.autonavi.bundle.searchresult.ajx.ModuleSearch;
import com.autonavi.common.Callback;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

public class SearchResultAjxViewManager$3 implements Callback<AmapAjxView> {
    static final /* synthetic */ boolean a = true;
    final /* synthetic */ bzf b;

    public void error(Throwable th, boolean z) {
    }

    static {
        Class<bzf> cls = bzf.class;
    }

    public SearchResultAjxViewManager$3(bzf bzf) {
        this.b = bzf;
    }

    public void callback(AmapAjxView amapAjxView) {
        if (amapAjxView == this.b.l) {
            ModuleSearch c_ = this.b.c_();
            if (a || c_ != null) {
                c_.setAjxProxy(this.b.o);
                c_.setOnAnimateEventListener(this.b.y);
                c_.setOnPageActionListener(this.b.x);
                return;
            }
            throw new AssertionError();
        }
    }
}
