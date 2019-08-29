package com.autonavi.map.fragmentcontainer.page.mappage.TipsView;

import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;

public class GeoCodeChecker implements IGeoCodeChecker {
    private IGeoCodeChecker mTarget = ((IGeoCodeChecker) ank.a(IGeoCodeChecker.class));

    public void request(GeoPoint geoPoint, Callback<PageBundle> callback) {
        this.mTarget.request(geoPoint, callback);
    }
}
