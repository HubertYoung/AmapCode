package com.autonavi.minimap.base.overlay;

import com.autonavi.minimap.R;

public class MapPointOverlay extends PointOverlay<MapPointOverlayItem> {
    private OnClearFocusListener mOnClearFocusListener;
    private int mResId = 0;

    public interface OnClearFocusListener {
        void onClearFocus();
    }

    public MapPointOverlay(bty bty, int i) {
        super(bty);
        this.mResId = i;
    }

    public void setItem(MapPointOverlayItem mapPointOverlayItem) {
        if (this.mResId == R.drawable.b_poi_hl) {
            this.mOverlayDefaultMarker = createMarker(this.mResId, 9, SimpleMarkerFactory.POI_HL_X_RATIO, SimpleMarkerFactory.POI_HL_Y_RATIO);
        } else {
            this.mOverlayDefaultMarker = createMarker(this.mResId, 5);
        }
        this.mOverlayFocusMarker = this.mOverlayDefaultMarker;
        if (this.mItemList.size() == 0) {
            addItem(mapPointOverlayItem);
            return;
        }
        clear();
        addItem(mapPointOverlayItem);
    }

    public void setOnClearFocusListener(OnClearFocusListener onClearFocusListener) {
        this.mOnClearFocusListener = onClearFocusListener;
    }

    public boolean clear() {
        boolean clear = super.clear();
        if (this.mOnClearFocusListener != null) {
            this.mOnClearFocusListener.onClearFocus();
        }
        return clear;
    }

    public void clearFocus() {
        super.clearFocus();
        if (this.mOnClearFocusListener != null) {
            this.mOnClearFocusListener.onClearFocus();
        }
    }

    public int getMarkerResId() {
        return this.mResId;
    }
}
