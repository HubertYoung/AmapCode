package com.autonavi.mine.measure;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayUtil;

public class MeasurePointOverlay extends PointOverlay<POIOverlayItem> {
    private TextView overlayText;
    private View overlayView;

    public MeasurePointOverlay(bty bty) {
        super(bty);
    }

    private View drawOverlayView(String str) {
        if (this.overlayView == null || this.overlayText == null) {
            this.overlayView = LayoutInflater.from(AMapPageUtil.getAppContext()).inflate(R.layout.tip_measure, null);
            this.overlayText = (TextView) this.overlayView.findViewById(R.id.txt_distance);
        }
        this.overlayText.setText(str);
        return this.overlayView;
    }

    public Marker createViewMarker(int i, String str, POI poi, int i2) {
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, poi.getPoint(), 81);
        mapViewLayoutParams.mode = 0;
        View drawOverlayView = drawOverlayView(str);
        this.mMapView.a(drawOverlayView, (LayoutParams) mapViewLayoutParams);
        Bitmap convertViewToBitmap = OverlayUtil.convertViewToBitmap(drawOverlayView);
        Marker createMarker = createMarker(i, convertViewToBitmap, i2, false);
        this.mMapView.a(drawOverlayView);
        if (convertViewToBitmap != null && !convertViewToBitmap.isRecycled()) {
            convertViewToBitmap.recycle();
        }
        return createMarker;
    }
}
