package com.autonavi.minimap.base.overlay;

import android.graphics.Bitmap;
import com.autonavi.jni.ae.gmap.gloverlay.GLNaviOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.minimap.R;

public abstract class AbstractNaviOverlay extends BaseOverlayDelegate<GLNaviOverlay, Object> {
    protected static final int ID_CAR_DIRECTION_NIGHT_V2 = R.drawable.navi_direction_night_v2;
    protected static final int ID_CAR_DIRECTION_V2 = R.drawable.navi_direction_v2;
    protected static final int ID_CAR_EAST_DAY_V2 = R.drawable.east_day_xh_v2;
    protected static final int ID_CAR_EAST_NIGHT_V2 = R.drawable.east_night_xh_v2;
    protected static final int ID_CAR_NORTH_DAY_V2 = R.drawable.north_day_xh_v2;
    protected static final int ID_CAR_NORTH_NIGHT_V2 = R.drawable.north_night_xh_v2;
    protected static final int ID_CAR_SOUTH_DAY_V2 = R.drawable.south_day_xh_v2;
    protected static final int ID_CAR_SOUTH_NIGHT_V2 = R.drawable.south_night_xh_v2;
    protected static final int ID_CAR_WEST_DAY_V2 = R.drawable.west_day_xh_v2;
    protected static final int ID_CAR_WEST_NIGHT_V2 = R.drawable.west_night_xh_v2;

    public void onPause() {
    }

    public void onResume() {
    }

    public AbstractNaviOverlay(bty bty) {
        super(bty);
    }

    public AbstractNaviOverlay(int i, bty bty) {
        super(i, bty);
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLNaviOverlay(this.mEngineID, this.mMapView.c(), hashCode());
    }

    public Marker createMarker(int i, int i2) {
        return createMarker(i, i2, 0.0f, 0.0f);
    }

    public Marker createMarker(int i, int i2, boolean z) {
        return createMarker(i, i2, 0.0f, 0.0f, z);
    }

    public Marker createMarker(int i, int i2, float f, float f2) {
        return SimpleMarkerFactory.createMarker(i, i2, f, f2, this.mMapView);
    }

    public Marker createMarker(int i, int i2, float f, float f2, boolean z) {
        return SimpleMarkerFactory.createMarker(i, i2, f, f2, z, this.mMapView);
    }

    public void createLineTexure(int i, int i2) {
        SimpleMarkerFactory.createLineTexure(this.mMapView, i, i2);
    }

    public Marker createMarkerWithRawResource(int i, int i2) {
        return SimpleMarkerFactory.createMarkerWithRawResource(i, i2, 0.0f, 0.0f, this.mMapView);
    }

    public Marker createMarkerWithBitmap(int i, Bitmap bitmap, int i2, float f, float f2, boolean z) {
        return SimpleMarkerFactory.createMarker(i, bitmap, i2, f, f2, z, this.mMapView);
    }
}
