package com.autonavi.minimap.route.bus.extbus.overlay;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

public class ExtBusPointOverlay extends PointOverlay<PointOverlayItem> {
    private LayoutInflater inflater;

    public ExtBusPointOverlay(Context context, bty bty) {
        super(bty);
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public PointOverlayItem addStation(GeoPoint geoPoint, int i, int i2, int i3) {
        dvc dvc = new dvc(geoPoint);
        dvc.a = i2;
        if (i == -999) {
            dvc.mDefaultMarker = new Marker(-999, i3, 0, 0);
        } else {
            dvc.mDefaultMarker = createMarker(i, i3);
        }
        addItem(dvc);
        return dvc;
    }

    public void addBusStationAdvanceTip(GeoPoint geoPoint, int i, CharSequence charSequence, CharSequence charSequence2, int i2) {
        if (Thread.currentThread().getName().equals("main")) {
            addBusStationAdvanceTipEx(geoPoint, i, charSequence, charSequence2, i2);
            return;
        }
        bty bty = this.mMapView;
        final GeoPoint geoPoint2 = geoPoint;
        final int i3 = i;
        final CharSequence charSequence3 = charSequence;
        final CharSequence charSequence4 = charSequence2;
        final int i4 = i2;
        AnonymousClass1 r1 = new Runnable() {
            public final void run() {
                ExtBusPointOverlay.this.addBusStationAdvanceTipEx(geoPoint2, i3, charSequence3, charSequence4, i4);
            }
        };
        bty.a((Runnable) r1);
    }

    /* access modifiers changed from: private */
    public void addBusStationAdvanceTipEx(GeoPoint geoPoint, int i, CharSequence charSequence, CharSequence charSequence2, int i2) {
        View view;
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        }
        if (i2 == 0) {
            view = this.inflater.inflate(R.layout.station_tip_left_top_new_layout, null);
        } else if (i2 == 1) {
            view = this.inflater.inflate(R.layout.station_tip_right_top_new_layout, null);
        } else if (i2 == 2) {
            view = this.inflater.inflate(R.layout.station_tip_left_bottom_new_layout, null);
        } else {
            view = this.inflater.inflate(R.layout.station_tip_right_bottom_new_layout, null);
        }
        TextView textView = (TextView) view.findViewById(R.id.title_name);
        TextView textView2 = (TextView) view.findViewById(R.id.title_des);
        View findViewById = view.findViewById(R.id.line);
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setText(charSequence);
        }
        if (TextUtils.isEmpty(charSequence2)) {
            textView2.setVisibility(8);
            findViewById.setVisibility(8);
        } else {
            textView2.setText(charSequence2);
            textView2.setVisibility(0);
            findViewById.setVisibility(0);
        }
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 3);
        mapViewLayoutParams.mode = 0;
        this.mMapView.a(view, (LayoutParams) mapViewLayoutParams);
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
        pointOverlayItem.mDefaultMarker = createMarker(i, view, i2, 0.0f, 0.0f, false);
        this.mMapView.a(view);
        addItem(pointOverlayItem);
    }
}
