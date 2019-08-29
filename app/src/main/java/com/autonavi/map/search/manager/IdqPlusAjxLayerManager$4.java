package com.autonavi.map.search.manager;

import com.autonavi.common.Callback;
import com.autonavi.minimap.ajx3.views.AmapAjxView;

public class IdqPlusAjxLayerManager$4 implements Callback<AmapAjxView> {
    final /* synthetic */ byx a;

    public void error(Throwable th, boolean z) {
    }

    public IdqPlusAjxLayerManager$4(byx byx) {
        this.a = byx;
    }

    public void callback(AmapAjxView amapAjxView) {
        this.a.h.removeView(this.a.g);
    }
}
