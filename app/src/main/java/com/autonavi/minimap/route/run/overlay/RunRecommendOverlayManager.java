package com.autonavi.minimap.route.run.overlay;

import android.graphics.Rect;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.common.view.HelPointOverlay;
import com.autonavi.minimap.route.foot.overlay.FootWheelOverlay;
import com.autonavi.minimap.route.foot.overlay.RouteFootLineOverlay;
import com.autonavi.minimap.route.run.page.RunRecommendPage;
import com.autonavi.sdk.location.LocationInstrument;

public class RunRecommendOverlayManager implements Callback<Status> {
    private static final int MAP_TRACE_PADDING = 50;
    private static int MAX_ZOOM_LEVEL = 19;
    private static int MIN_ZOOM_LEVEL = 3;
    private int mBottomOffSet;
    private int mLeftOffSet;
    private RouteFootLineOverlay mLineOverlay;
    private bty mMapView;
    private int mRightOffSet;
    private int mScreenHoriticalOffSet;
    private int mScreenVerticalOffSet;
    private HelPointOverlay mStartPointOverlay;
    private int mTopOffSet;
    private FootWheelOverlay mWheelOverlay;

    public void error(Throwable th, boolean z) {
    }

    public RunRecommendOverlayManager(RunRecommendPage runRecommendPage) {
        if (runRecommendPage != null && runRecommendPage.getMapManager() != null && runRecommendPage.getMapManager().getMapView() != null) {
            this.mMapView = runRecommendPage.getMapManager().getMapView();
            this.mLineOverlay = new RouteFootLineOverlay(this.mMapView);
            runRecommendPage.addOverlay(this.mLineOverlay);
            this.mStartPointOverlay = new HelPointOverlay(this.mMapView);
            runRecommendPage.addOverlay(this.mStartPointOverlay);
            this.mWheelOverlay = new FootWheelOverlay(this.mMapView);
            runRecommendPage.addOverlay(this.mWheelOverlay);
            this.mWheelOverlay.resumeMarker();
        }
    }

    public void drawableLineOverlay(GeoPoint[] geoPointArr) {
        if (this.mLineOverlay != null && geoPointArr != null && geoPointArr.length != 0) {
            this.mLineOverlay.clear();
            this.mLineOverlay.createAndAddBackgroundLineItem(geoPointArr);
            this.mLineOverlay.createAndAddArrowLineItem(geoPointArr);
            zoomBoundMap();
            drawableStartPointOverlay(geoPointArr[0]);
        }
    }

    private void drawableStartPointOverlay(GeoPoint geoPoint) {
        this.mStartPointOverlay.clear();
        if (geoPoint != null) {
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.mDefaultMarker = this.mStartPointOverlay.createMarker(R.drawable.bubble_start, 5);
            this.mStartPointOverlay.addItem(pointOverlayItem);
        }
    }

    public void drawableWheelOverlay(GeoPoint geoPoint, boolean z) {
        if (this.mWheelOverlay != null) {
            eao.a((String) "wheel", (String) "foot drawableWheelOverlay");
            this.mWheelOverlay.updateWheel(geoPoint.x, geoPoint.y, 0);
            if (z) {
                showWheelOverlay();
            }
        }
    }

    public void clearOverlay() {
        if (this.mLineOverlay != null) {
            this.mLineOverlay.clear();
        }
        if (this.mStartPointOverlay != null) {
            this.mStartPointOverlay.clear();
        }
        clearWheelOverlay();
    }

    public void clearWheelOverlay() {
        if (this.mWheelOverlay != null) {
            this.mWheelOverlay.hideWheel();
            this.mWheelOverlay.clear();
        }
    }

    public void showWheelOverlay() {
        if (this.mWheelOverlay != null) {
            eao.a((String) "wheel", (String) "foot showWheel");
            this.mWheelOverlay.showWheel();
        }
    }

    public void zoomBoundMap() {
        if (this.mLineOverlay != null) {
            zoomBound(this.mLineOverlay.getBound());
        }
    }

    private void zoomBound(Rect rect) {
        if (rect != null && this.mMapView != null) {
            float zoomLevelByBound = getZoomLevelByBound(rect);
            GeoPoint center = getCenter(rect, zoomLevelByBound);
            if (zoomLevelByBound != 0.0f && center != null) {
                this.mMapView.a(400, zoomLevelByBound == -1.0f ? -9999.0f : zoomLevelByBound, 0, 0, center.x, center.y, true);
            }
        }
    }

    public float getZoomLevelByBound(Rect rect) {
        if (this.mMapView == null) {
            return 0.0f;
        }
        float U = this.mMapView.U();
        return Math.min((float) MAX_ZOOM_LEVEL, Math.max((float) MIN_ZOOM_LEVEL, Math.min((float) getZoomLevel((double) ((((float) this.mMapView.al()) - ((float) this.mScreenHoriticalOffSet)) * U), (double) rect.width()), (float) getZoomLevel((double) ((((float) this.mMapView.am()) - ((float) this.mScreenVerticalOffSet)) * U), (double) rect.height()))));
    }

    private double getZoomLevel(double d, double d2) {
        return 20.0d - (Math.log(d2 / d) / Math.log(2.0d));
    }

    private GeoPoint getCenter(Rect rect, float f) {
        if (this.mMapView == null) {
            return null;
        }
        float U = this.mMapView.U();
        float f2 = ((float) (this.mBottomOffSet - this.mTopOffSet)) * U;
        double d = (double) (19.0f - f);
        return new GeoPoint(rect.centerX() + ((int) (((double) (((float) (this.mRightOffSet - this.mLeftOffSet)) * U)) * Math.pow(2.0d, d))), rect.centerY() + ((int) (((double) f2) * Math.pow(2.0d, d))));
    }

    public void setScreenDisplayMargin(int i, int i2, int i3, int i4) {
        this.mLeftOffSet = i + 50;
        this.mTopOffSet = i2 + 50;
        this.mRightOffSet = i3 + 50;
        this.mBottomOffSet = i4 + 50;
        this.mScreenVerticalOffSet = this.mTopOffSet + this.mBottomOffSet;
        this.mScreenHoriticalOffSet = this.mLeftOffSet + this.mRightOffSet;
    }

    public void callback(Status status) {
        if (status == Status.ON_LOCATION_OK) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                drawableWheelOverlay(latestPosition, this.mLineOverlay != null && this.mLineOverlay.getSize() > 0);
            }
        }
    }
}
