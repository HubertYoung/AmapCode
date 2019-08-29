package com.autonavi.minimap.map;

import android.graphics.BitmapFactory;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.MapPointOverlay;
import com.autonavi.minimap.base.overlay.MapPointOverlayItem;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.TrafficPointOverlayItem;

public class TrafficPointOverlay extends MapPointOverlay {
    /* access modifiers changed from: private */
    public String lastClearIcon = null;
    private cep mOpenLayerInteractiveListener;

    public TrafficPointOverlay(bty bty, int i) {
        super(bty, i);
    }

    public boolean clear() {
        if (this.mOpenLayerInteractiveListener != null) {
            this.mOpenLayerInteractiveListener.OnTrafficLabelClickCancel();
        }
        if (this.lastClearIcon != null) {
            aho.a(new Runnable() {
                public void run() {
                    TrafficPointOverlay.this.mMapView.a(TrafficPointOverlay.this.lastClearIcon);
                }
            });
        }
        return super.clear();
    }

    public void setItem(MapPointOverlayItem mapPointOverlayItem) {
        if (this.mOpenLayerInteractiveListener != null) {
            this.mOpenLayerInteractiveListener.OnTrafficLabelClick();
        }
        this.mOverlayDefaultMarker = createMarker(getMarkerResId(), 5);
        this.mOverlayFocusMarker = this.mOverlayDefaultMarker;
        if (this.mItemList.size() == 0) {
            addItemWithZ(mapPointOverlayItem);
            return;
        }
        clear();
        addItemWithZ(mapPointOverlayItem);
    }

    public void setItem(int i, int i2) {
        setItem(new MapPointOverlayItem(new GeoPoint(i, i2), getMarkerResId()));
    }

    public void setItem(final int i, final int i2, float f, final String str) {
        if (this.lastClearIcon != null) {
            this.mMapView.a(this.lastClearIcon);
        }
        this.lastClearIcon = str;
        aho.a(new Runnable() {
            public void run() {
                Marker createMarker = TrafficPointOverlay.this.createMarker(R.drawable.traffic_road_unimpeded, 4);
                TrafficPointOverlay.this.mMapView.a(i, i2, 2, (float) createMarker.mWidth, (float) createMarker.mHeight, str);
            }
        });
        TrafficPointOverlayItem trafficPointOverlayItem = new TrafficPointOverlayItem(new GeoPoint(i, i2), getMarkerResId(), str);
        if (f > 0.0f) {
            trafficPointOverlayItem.mZ = f;
        }
        setAnimatorType(2);
        setBubbleAnimator(2);
        trafficPointOverlayItem.mDefaultMarker = createMarker(1995, BitmapFactory.decodeResource(this.mMapView.a(), getMarkerResId()), 9, 0.5f, 0.9f, true);
        setItem(trafficPointOverlayItem);
    }

    public void setOpenLayerInteractiveListener(cep cep) {
        this.mOpenLayerInteractiveListener = cep;
    }
}
