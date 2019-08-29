package com.amap.bundle.environmentmap.overlay;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;
import java.util.List;

public class SearchEnvironmentOverlay extends PointOverlay {
    private String mId = "";
    private PointOverlayItem mItem;
    private int mItemIndex = -1;
    private List<String> mPoiFilters = new ArrayList();
    private GeoPoint mPoint;

    public SearchEnvironmentOverlay(bty bty) {
        super(bty);
    }

    public void addEnvironmentOverlayItem(GeoPoint geoPoint, String str, bty bty) {
        clear();
        this.mId = str;
        this.mPoint = geoPoint;
        this.mItem = new PointOverlayItem(geoPoint);
        this.mItem.mDefaultMarker = createMarker(R.drawable.b_poi_envrionment, 9, 0.5f, 0.87f);
        this.mMapView.a(this.mPoint.x, this.mPoint.y, 2, (float) this.mContext.getResources().getDrawable(R.drawable.b_poi_envrionment).getIntrinsicWidth(), (float) this.mContext.getResources().getDrawable(R.drawable.b_poi_envrionment).getIntrinsicHeight(), this.mId);
        this.mPoiFilters.add(this.mId);
        addItem(this.mItem);
        if (bty != null) {
            bty.a((GLGeoPoint) geoPoint);
        }
    }

    private View createNameView(String str) {
        LinearLayout linearLayout = new LinearLayout(this.mContext);
        TextView textView = new TextView(this.mContext);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.topMargin = agn.a(this.mContext, 11.0f);
        linearLayout.setOrientation(1);
        layoutParams.gravity = 1;
        linearLayout.setBackgroundResource(R.drawable.b_poi_envrionment);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTextSize(16.0f);
        textView.setText(str);
        textView.setSingleLine();
        textView.setEllipsize(TruncateAt.END);
        textView.setTypeface(Typeface.defaultFromStyle(1));
        try {
            if (Integer.parseInt(str) > 999) {
                textView.setTextSize(15.0f);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        linearLayout.measure(0, 0);
        linearLayout.addView(textView);
        return linearLayout;
    }

    public boolean clear() {
        for (String a : this.mPoiFilters) {
            this.mMapView.a(a);
        }
        this.mPoiFilters.clear();
        this.mItemIndex = -1;
        this.mId = "";
        this.mItem = null;
        this.mPoint = null;
        return super.clear();
    }

    public void addHightLightOverlay(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && str2.equals(this.mId) && this.mItem != null && this.mPoint != null) {
            View createNameView = createNameView(str);
            PointOverlayItem pointOverlayItem = this.mItem;
            int i = this.mItemIndex + 1;
            this.mItemIndex = i;
            pointOverlayItem.mFocusMarker = createMarker(i, createNameView, 9, 0.5f, 0.87f, false);
            setFocus(this.mItem, true);
        }
    }
}
