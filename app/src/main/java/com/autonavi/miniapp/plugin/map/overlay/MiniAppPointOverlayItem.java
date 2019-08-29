package com.autonavi.miniapp.plugin.map.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor;
import com.autonavi.minimap.base.overlay.Marker;

public class MiniAppPointOverlayItem {
    public int mAngle;
    public int mAngleMode;
    public int mClickCalloutMarkerIndex = -1;
    public Marker mDefaultMarker;
    public GeoPoint mGeoPoint;
    public boolean mIsFixPoint;
    public long mItemId;
    public MarkerPropertyProcessor.Marker mMiniAppMarker;
    public boolean mVisible;

    public MiniAppPointOverlayItem(GeoPoint geoPoint) {
        this.mGeoPoint = geoPoint;
    }
}
