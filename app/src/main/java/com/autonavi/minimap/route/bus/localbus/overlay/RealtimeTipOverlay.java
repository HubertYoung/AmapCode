package com.autonavi.minimap.route.bus.localbus.overlay;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.wireless.security.SecExceptionCode;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;

public class RealtimeTipOverlay extends PointOverlay<PointOverlayItem> {
    private static final int MAX_LEN5 = 5;
    private static final int MAX_LEN7 = 7;
    /* access modifiers changed from: private */
    public boolean mIsTipClick;
    private boolean mIsTipShow;

    public RealtimeTipOverlay(bty bty) {
        super(bty);
    }

    public void addTipUpBusIcon(GeoPoint geoPoint, String str, String str2) {
        if (geoPoint != null) {
            clearTip();
            MapViewLayoutParams mapParams = getMapParams(geoPoint);
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.realtimebus_lines_style_inquiry, null);
            TextView textView = (TextView) inflate.findViewById(R.id.realbus_lines_inquiry_linename);
            if (textView != null) {
                textView.setText(getMAXContent(str, 5));
            }
            TextView textView2 = (TextView) inflate.findViewById(R.id.realbus_lines_inquiry_linetime);
            if (textView2 != null) {
                textView2.setText(str2);
            }
            createItem(geoPoint, mapParams, inflate);
        }
    }

    public void updateTipMsg() {
        if (this.mIsTipShow) {
            setOnItemClickListener(null);
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.realtimebus_friend_tip_layout, null);
            if (inflate != null) {
                resetItemDefaultMarker(SecExceptionCode.SEC_ERROR_STA_KEY_NOT_EXISTED, createMarker((int) SecExceptionCode.SEC_ERROR_STA_KEY_NOT_EXISTED, inflate, 5, 0.0f, 0.0f, false));
            }
        }
    }

    public void showNoNetTip(GeoPoint geoPoint) {
        showNoNetTip(geoPoint, null, true);
    }

    public void showNoNetTip(GeoPoint geoPoint, String str, boolean z) {
        if (geoPoint != null) {
            clearTip();
            MapViewLayoutParams mapParams = getMapParams(geoPoint);
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.realtime_no_net_tip_layout, null);
            if (!TextUtils.isEmpty(str)) {
                ((TextView) inflate.findViewById(R.id.no_net_msg)).setText(str);
            }
            if (!z) {
                ((ImageView) inflate.findViewById(R.id.refresh_icon)).setVisibility(8);
            }
            createItem(geoPoint, mapParams, inflate);
        }
    }

    private void setClickArea(View view) {
        if (view != null) {
            view.measure(0, 0);
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            int i = (measuredWidth * 2) / 3;
            ArrayList arrayList = new ArrayList();
            arrayList.add(new aly(i, measuredHeight));
            aly aly = new aly(measuredWidth - i, measuredHeight);
            aly.d = true;
            aly.e = new a() {
                public final void a() {
                    RealtimeTipOverlay.this.mIsTipClick = true;
                    RealtimeTipOverlay.this.clearTip();
                }
            };
            arrayList.add(aly);
            setClickList(arrayList);
        }
    }

    public void resetTipClickState() {
        this.mIsTipClick = false;
    }

    public boolean isTipClick() {
        return this.mIsTipClick;
    }

    public String getMAXContent(String str, int i) {
        if (TextUtils.isEmpty(str) || str.length() <= i) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, i));
        sb.append("...");
        return sb.toString();
    }

    public void addTipUpStationIcon(GeoPoint geoPoint, String str, String str2, String str3) {
        if (geoPoint != null) {
            clearTip();
            MapViewLayoutParams mapParams = getMapParams(geoPoint);
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.realtimebus_lines_style_notime, null);
            TextView textView = (TextView) inflate.findViewById(R.id.realbus_notime_linename);
            if (textView != null) {
                textView.setText(getMAXContent(str, 7));
            }
            TextView textView2 = (TextView) inflate.findViewById(R.id.realbus_start_time);
            if (textView2 != null) {
                efx.a(textView2);
                textView2.setText(str2);
            }
            TextView textView3 = (TextView) inflate.findViewById(R.id.realbus_end_time);
            if (textView3 != null) {
                efx.a(textView3);
                textView3.setText(str3);
            }
            createItem(geoPoint, mapParams, inflate);
            setClickArea(inflate);
        }
    }

    public boolean isTipShow() {
        return this.mIsTipShow;
    }

    public void clearTip() {
        clear();
        clearFocus();
        setOnItemClickListener(null);
        this.mIsTipShow = false;
    }

    private void createItem(GeoPoint geoPoint, MapViewLayoutParams mapViewLayoutParams, View view) {
        this.mMapView.a(view, (LayoutParams) mapViewLayoutParams);
        PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
        pointOverlayItem.mDefaultMarker = createMarker((int) SecExceptionCode.SEC_ERROR_STA_KEY_NOT_EXISTED, view, 5, 0.0f, 0.0f, false);
        this.mMapView.a(view);
        addItem(pointOverlayItem);
        this.mIsTipShow = true;
    }

    private MapViewLayoutParams getMapParams(GeoPoint geoPoint) {
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, geoPoint, 81);
        mapViewLayoutParams.mode = 0;
        return mapViewLayoutParams;
    }
}
