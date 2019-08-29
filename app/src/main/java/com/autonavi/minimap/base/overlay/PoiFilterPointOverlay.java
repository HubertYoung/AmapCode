package com.autonavi.minimap.base.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.Vector;

public class PoiFilterPointOverlay<T extends PointOverlayItem> extends PointOverlay<T> {
    public static final int DEFAULT_MAX_DISPLAY_LEVEL = 20;
    public static final int DEFAULT_MIN_DISPLAY_LEVEL = 3;
    public static final int FILTER_ALL = -1;
    public static final int FILTER_POI = 1;
    public static final int FILTER_ROADNAME = 2;
    public static final int FILTER_ROADSHIELD = 4;
    private int mAnchor = 2;
    private Vector<String> mFilters = new Vector<>();
    private bty mMapView;
    private int mMaxDisplayLevel = 20;
    private int mMinDisplayLevel = 3;
    private int mPoiFitlerType = 1;

    public PoiFilterPointOverlay(bty bty) {
        super(bty);
        this.mMapView = bty;
    }

    public void setPoiFilterType(int i) {
        this.mPoiFitlerType = i;
    }

    public void setPoiFilterLevelRange(int i, int i2) {
        this.mMinDisplayLevel = i;
        this.mMaxDisplayLevel = i2;
    }

    public void setPoiFilterAnchor(int i) {
        this.mAnchor = i;
    }

    private void addPoiFilter(int i, int i2, int i3, float f, float f2, String str) {
        if (this.mMapView != null) {
            this.mMapView.a(i, i2, i3, f, f2, (float) this.mMinDisplayLevel, (float) this.mMaxDisplayLevel, str, this.mPoiFitlerType);
        }
    }

    public void addItem(T t) {
        super.addItem(t);
        GeoPoint geoPoint = t.getGeoPoint();
        if (geoPoint != null) {
            String valueOf = String.valueOf(t.mItemId);
            addPoiFilter(geoPoint.x, geoPoint.y, this.mAnchor, (float) t.mDefaultMarker.mWidth, (float) t.mDefaultMarker.mHeight, valueOf);
            if (!this.mFilters.contains(valueOf)) {
                this.mFilters.add(valueOf);
            }
        }
    }

    public boolean clear() {
        for (int i = 0; i < this.mFilters.size(); i++) {
            this.mMapView.a(this.mFilters.get(i));
        }
        return super.clear();
    }
}
