package com.autonavi.minimap.route.bus.localbus.overlay;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.Iterator;
import java.util.Vector;

public class RouteBusTipOverlay extends PointOverlay {
    private Vector<String> mFilterKeys = new Vector<>();
    private LayoutInflater mInflater = null;

    public RouteBusTipOverlay(bty bty) {
        super(bty);
        setCheckCover(true);
        showReversed(false);
        setHideIconWhenCovered(true);
        this.mInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    /* access modifiers changed from: private */
    @SuppressLint({"RtlHardcoded"})
    public void addBusStationAdvanceTipFor768(dwm dwm, int i) {
        int i2;
        int i3;
        int i4;
        int i5;
        dwm dwm2 = dwm;
        if (dwm2 != null) {
            GeoPoint geoPoint = dwm2.a;
            String str = dwm2.b;
            String str2 = dwm2.c;
            String str3 = dwm2.d;
            int i6 = dwm2.e;
            int i7 = dwm2.f;
            int i8 = dwm2.g;
            String str4 = dwm2.h;
            boolean z = dwm2.j;
            int a = agn.a(AMapPageUtil.getAppContext(), 1.0f);
            int a2 = agn.a(AMapPageUtil.getAppContext(), 10.0f);
            LinearLayout linearLayout = (LinearLayout) this.mInflater.inflate(R.layout.station_tip_for_768, null);
            switch (i6) {
                case 5:
                    linearLayout.setPadding(a, a, a, a2);
                    break;
                case 6:
                    linearLayout.setPadding(a, a2, a, a);
                    break;
                case 7:
                    linearLayout.setPadding(a2, a, a, a);
                    i3 = 3;
                    i2 = 0;
                    break;
                case 8:
                    linearLayout.setPadding(a, a, a2, a);
                    i3 = 5;
                    i2 = 1;
                    break;
                default:
                    i3 = 3;
                    break;
            }
            i3 = 1;
            i2 = 2;
            linearLayout.setGravity(i3);
            TextView textView = (TextView) linearLayout.findViewById(R.id.title_name);
            TextView textView2 = (TextView) linearLayout.findViewById(R.id.title_des);
            TextView textView3 = (TextView) linearLayout.findViewById(R.id.title_des2);
            ImageView imageView = (ImageView) linearLayout.findViewById(R.id.station_tip_arrow);
            if (TextUtils.isEmpty(str)) {
                textView.setVisibility(8);
            } else {
                textView.setText(str);
            }
            if (!TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str2)) {
                String charSequence = str3.toString();
                if (charSequence.startsWith(this.mContext.getString(R.string.route_subway))) {
                    i5 = 2;
                    charSequence = charSequence.substring(2);
                } else {
                    i5 = 2;
                }
                String charSequence2 = str2.toString();
                if (charSequence2.startsWith(this.mContext.getString(R.string.route_subway))) {
                    charSequence2 = charSequence2.substring(i5);
                }
                textView2.setText(charSequence);
                textView3.setText(charSequence2);
                LayerDrawable layerDrawable = (LayerDrawable) textView2.getBackground();
                ((GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.shap_backcolor)).setColor(i8);
                textView2.setBackgroundDrawable(layerDrawable);
                i4 = 0;
                resetDesTvParams(false, textView2);
                resetDesTvParams(false, textView3);
                textView2.setTextColor(-1);
                textView3.setTextColor(-1);
                LayerDrawable layerDrawable2 = (LayerDrawable) textView3.getBackground();
                ((GradientDrawable) layerDrawable2.findDrawableByLayerId(R.id.shap_backcolor)).setColor(i7);
                textView3.setBackgroundDrawable(layerDrawable2);
                textView2.setVisibility(0);
                textView3.setVisibility(0);
                imageView.setVisibility(0);
            } else if (TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str2) && z) {
                String charSequence3 = str2.toString();
                if (charSequence3.startsWith(this.mContext.getString(R.string.route_subway))) {
                    charSequence3 = charSequence3.substring(2);
                }
                textView3.setText(charSequence3);
                textView2.setText("占位");
                i4 = 0;
                textView2.setTextColor(0);
                textView3.setTextColor(-1);
                resetDesTvParams(true, textView2);
                resetDesTvParams(false, textView3);
                textView2.setBackgroundResource(R.drawable.route_bus_detail_ride_icon);
                LayerDrawable layerDrawable3 = (LayerDrawable) textView3.getBackground();
                ((GradientDrawable) layerDrawable3.findDrawableByLayerId(R.id.shap_backcolor)).setColor(i7);
                textView3.setBackgroundDrawable(layerDrawable3);
                textView2.setVisibility(0);
                textView3.setVisibility(0);
                imageView.setVisibility(0);
            } else if (TextUtils.isEmpty(str3) || !TextUtils.isEmpty(str2) || !z) {
                textView3.setVisibility(8);
                imageView.setVisibility(8);
                if (TextUtils.isEmpty(str2)) {
                    textView2.setVisibility(8);
                    i4 = 0;
                } else {
                    String charSequence4 = str2.toString();
                    if (charSequence4.startsWith(this.mContext.getString(R.string.route_subway))) {
                        charSequence4 = charSequence4.substring(2);
                    }
                    textView2.setText(charSequence4);
                    LayerDrawable layerDrawable4 = (LayerDrawable) textView2.getBackground();
                    ((GradientDrawable) layerDrawable4.findDrawableByLayerId(R.id.shap_backcolor)).setColor(i7);
                    textView2.setBackgroundDrawable(layerDrawable4);
                    textView2.setTextColor(-1);
                    i4 = 0;
                    resetDesTvParams(false, textView2);
                    textView2.setVisibility(0);
                }
            } else {
                String charSequence5 = str3.toString();
                if (charSequence5.startsWith(this.mContext.getString(R.string.route_subway))) {
                    charSequence5 = charSequence5.substring(2);
                }
                textView2.setText(charSequence5);
                LayerDrawable layerDrawable5 = (LayerDrawable) textView2.getBackground();
                ((GradientDrawable) layerDrawable5.findDrawableByLayerId(R.id.shap_backcolor)).setColor(i8);
                textView2.setBackgroundDrawable(layerDrawable5);
                textView3.setText("占位");
                i4 = 0;
                textView3.setTextColor(0);
                textView2.setTextColor(-1);
                resetDesTvParams(false, textView2);
                resetDesTvParams(true, textView3);
                textView3.setBackgroundResource(R.drawable.route_bus_detail_ride_icon);
                textView2.setVisibility(0);
                textView3.setVisibility(0);
                imageView.setVisibility(0);
            }
            MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 81);
            mapViewLayoutParams.mode = i4;
            this.mMapView.a((View) linearLayout, (LayoutParams) mapViewLayoutParams);
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.mDefaultMarker = createMarker(i, (View) linearLayout, i6, 0.0f, 0.0f, false);
            PointOverlayItem pointOverlayItem2 = pointOverlayItem;
            LinearLayout linearLayout2 = linearLayout;
            String str5 = str4;
            this.mMapView.a(geoPoint.x, geoPoint.y, i2, (float) pointOverlayItem.mDefaultMarker.mWidth, (float) pointOverlayItem.mDefaultMarker.mHeight, str4, -1);
            if (!this.mFilterKeys.contains(str5)) {
                this.mFilterKeys.add(str5);
            }
            this.mMapView.a((View) linearLayout2);
            addItem(pointOverlayItem2);
        }
    }

    public void addBusStationTip(final dwm dwm, final int i) {
        if (dwm != null) {
            if (Thread.currentThread().getName().equals("main")) {
                addBusStationAdvanceTipFor768(dwm, i);
            } else {
                this.mMapView.a((Runnable) new Runnable() {
                    public final void run() {
                        RouteBusTipOverlay.this.addBusStationAdvanceTipFor768(dwm, i);
                    }
                });
            }
        }
    }

    public boolean clear() {
        if (this.mFilterKeys.size() > 0) {
            Iterator<String> it = this.mFilterKeys.iterator();
            while (it.hasNext()) {
                this.mMapView.a(it.next());
            }
        }
        this.mFilterKeys.clear();
        return super.clear();
    }

    private void resetDesTvParams(boolean z, TextView textView) {
        if (z) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.height = agn.a(AMapPageUtil.getAppContext(), 17.0f);
            layoutParams.width = agn.a(AMapPageUtil.getAppContext(), 28.0f);
            return;
        }
        textView.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
    }
}
