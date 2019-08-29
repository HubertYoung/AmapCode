package com.autonavi.bundle.routecommute.drive.tips;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.map.overlayholder.OverlayUtil;

public class DriveCommuteTipsSimOverlay extends PointOverlay {
    private View commuteTipSimView = LayoutInflater.from(this.mContext).inflate(R.layout.drive_commute_tips_sim_layout, null);
    private ImageView simIconView = ((ImageView) this.commuteTipSimView.findViewById(R.id.commute_tips_sim_icon));

    public DriveCommuteTipsSimOverlay(bty bty) {
        super(bty);
        setMoveToFocus(false);
    }

    public void updateItem(PointOverlayItem pointOverlayItem, boolean z, int i) {
        if (pointOverlayItem != null && pointOverlayItem.getGeoPoint() != null) {
            if (i != 1) {
                if (i != 3) {
                    if (i != 5) {
                        if (i != 7) {
                            if (i == 9) {
                                this.simIconView.setImageResource(R.drawable.drive_commute_tips_work_home_sim);
                            }
                            this.mOverlayDefaultMarker = createViewMarker(this.commuteTipSimView, 0, pointOverlayItem.getGeoPoint());
                            addItem(pointOverlayItem);
                        }
                    }
                }
                this.simIconView.setImageResource(R.drawable.drive_commute_tips_work_sim);
                this.mOverlayDefaultMarker = createViewMarker(this.commuteTipSimView, 0, pointOverlayItem.getGeoPoint());
                addItem(pointOverlayItem);
            }
            this.simIconView.setImageResource(R.drawable.drive_commute_tips_home_sim);
            this.mOverlayDefaultMarker = createViewMarker(this.commuteTipSimView, 0, pointOverlayItem.getGeoPoint());
            addItem(pointOverlayItem);
        }
    }

    public void addItem(PointOverlayItem pointOverlayItem) {
        clear();
        super.addItem(pointOverlayItem);
    }

    private Marker createViewMarker(View view, int i, GeoPoint geoPoint) {
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
