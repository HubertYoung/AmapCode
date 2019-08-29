package com.autonavi.map.fragmentcontainer.page.mappage.TipsView;

import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;

public interface IGeoCodeChecker {
    void request(GeoPoint geoPoint, Callback<PageBundle> callback);
}
