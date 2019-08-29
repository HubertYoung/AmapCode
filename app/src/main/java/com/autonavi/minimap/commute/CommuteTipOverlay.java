package com.autonavi.minimap.commute;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayUtil;

public class CommuteTipOverlay extends PointOverlay<POIOverlayItem> {
    private View commuteTipSimView = LayoutInflater.from(this.mContext).inflate(R.layout.commute_tips_layout_sim, null);
    private cdx gpsLayer;
    private IOverlayManager overlayManager;
    private ImageView simIconView = ((ImageView) this.commuteTipSimView.findViewById(R.id.commute_tips_sim_icon));

    public CommuteTipOverlay(bty bty, IOverlayManager iOverlayManager) {
        super(bty);
        this.overlayManager = iOverlayManager;
        this.gpsLayer = iOverlayManager.getGpsLayer();
        setMoveToFocus(false);
    }

    public void updateItem(POIOverlayItem pOIOverlayItem, boolean z) {
        if (pOIOverlayItem != null && pOIOverlayItem.getGeoPoint() != null) {
            if (z) {
                this.simIconView.setImageResource(R.drawable.commute_tips_home_sim);
            } else {
                this.simIconView.setImageResource(R.drawable.commute_tips_work_sim);
            }
            this.mOverlayDefaultMarker = createViewMarker(this.commuteTipSimView, 0, pOIOverlayItem.getGeoPoint());
            addItem(pOIOverlayItem);
        }
    }

    public void addItem(POIOverlayItem pOIOverlayItem) {
        if (this.gpsLayer != null) {
            setOverlayOnTop(true);
        }
        clear();
        super.addItem(pOIOverlayItem);
    }

    public boolean removeAll() {
        if (this.gpsLayer != null) {
            setOverlayOnTop(false);
        }
        return super.removeAll();
    }

    public Marker createViewMarker(View view, int i, GeoPoint geoPoint) {
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 81);
        mapViewLayoutParams.mode = 0;
        this.mMapView.a(view, (LayoutParams) mapViewLayoutParams);
        Bitmap convertViewToBitmap = OverlayUtil.convertViewToBitmap(view);
        Marker createMarker = createMarker(i, convertViewToBitmap, 5, false);
        this.mMapView.a(view);
        if (convertViewToBitmap != null && !convertViewToBitmap.isRecycled()) {
            convertViewToBitmap.recycle();
        }
        return createMarker;
    }
}
