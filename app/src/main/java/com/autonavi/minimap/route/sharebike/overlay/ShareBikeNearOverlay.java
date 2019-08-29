package com.autonavi.minimap.route.sharebike.overlay;

import android.view.LayoutInflater;
import android.view.View;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.sharebike.model.BicycleBasicItem;

public class ShareBikeNearOverlay extends PointOverlay {
    private PointOverlayItem mNearTipItem;

    public ShareBikeNearOverlay(bty bty) {
        super(bty);
    }

    public void drawNearOverlay(BicycleBasicItem bicycleBasicItem) {
        clear();
        if (bicycleBasicItem != null) {
            View inflate = LayoutInflater.from(AMapPageUtil.getAppContext()).inflate(R.layout.sharebike_near_overlay_tip, null);
            this.mNearTipItem = new PointOverlayItem(new GeoPoint(bicycleBasicItem.getX(), bicycleBasicItem.getY()));
            this.mNearTipItem.mDefaultMarker = createMarker(0, inflate, 5, 0.0f, 0.0f, false);
            addItem(this.mNearTipItem);
        }
    }

    public void disappear() {
        for (PointOverlayItem pointOverlayItem : getItems()) {
            ((GLPointOverlay) getGLOverlay()).ModifyAnimationPointItem(pointOverlayItem.mItemId, 10);
            if (this.mMapView != null) {
                this.mMapView.b(25);
            }
        }
    }

    public boolean clear() {
        synchronized (this) {
            this.mNearTipItem = null;
        }
        return super.clear();
    }
}
