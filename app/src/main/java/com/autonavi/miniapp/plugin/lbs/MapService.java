package com.autonavi.miniapp.plugin.lbs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class MapService extends ExternalService {
    public abstract float calculateDistance(LatLonPoint latLonPoint, LatLonPoint latLonPoint2);

    public abstract View getMapView(Context context);

    public abstract View getMapView(Context context, AttributeSet attributeSet);

    public abstract View getSendMapView(Context context);

    public abstract View getShareMapView(Context context);
}
