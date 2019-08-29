package com.autonavi.minimap.basemap.favorites;

import com.autonavi.common.Callback;

public class FavoriteServiceImpl$1 implements Callback<Boolean> {
    final /* synthetic */ cov a;

    public void error(Throwable th, boolean z) {
    }

    public FavoriteServiceImpl$1(cov cov) {
        this.a = cov;
    }

    public void callback(Boolean bool) {
        this.a.b();
    }
}
