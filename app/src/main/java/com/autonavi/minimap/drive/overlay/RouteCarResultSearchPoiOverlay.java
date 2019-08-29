package com.autonavi.minimap.drive.overlay;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.widget.StrokeTextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class RouteCarResultSearchPoiOverlay extends PointOverlay<dhq> {
    private static final int FOCUSEDPOIINDEX = -2;
    private Context mContext;
    private HashMap<String, Marker> mMarkerCache;
    private int mNormalCurIndex = 0;
    private ISearchPoiData mSearchPoi;
    private ArrayList<String> poiFilters;

    public void setFocus(int i, boolean z) {
    }

    public RouteCarResultSearchPoiOverlay(bty bty) {
        super(bty);
        if (bty != null) {
            this.mMarkerCache = new HashMap<>();
            this.mContext = bty.d().getApplicationContext();
            this.poiFilters = new ArrayList<>();
            setMoveToFocus(false);
        }
    }

    public void addPoiItem(POI poi, int i, boolean z) {
        if (poi == null) {
            this.mSearchPoi = null;
            return;
        }
        this.mSearchPoi = (ISearchPoiData) poi.as(ISearchPoiData.class);
        GeoPoint displayPoint = this.mSearchPoi.getDisplayPoint();
        if (displayPoint == null || displayPoint.x <= 0 || displayPoint.y <= 0) {
            displayPoint = this.mSearchPoi.getPoint();
        }
        dhq dhq = new dhq(this.mSearchPoi, displayPoint);
        int iconId = poi.getIconId();
        if (iconId > 0) {
            dhq.mDefaultMarker = createMarker(iconId, 5);
            dhq.mFocusMarker = createMarker(R.drawable.b_poi_hl, 5);
            dhq.mBgMarker = getIconBgMarker(this.mSearchPoi);
        } else if (showIconBgMarker(this.mSearchPoi)) {
            dhq.mBgMarker = getIconBgMarker(this.mSearchPoi);
            dhq.mBgFocusMarker = getFocusIconBgMarker();
        } else if (showTxtBgMarker(this.mSearchPoi)) {
            dhq.mBgMarker = getTxtBgMarker(this.mSearchPoi);
            dhq.mBgFocusMarker = getFocusTxtBgMarker(this.mSearchPoi);
        }
        if (dhq.mBgMarker != null) {
            GeoPoint point = poi.getPoint();
            if (((ISearchPoiData) poi.as(ISearchPoiData.class)).getPoiChildrenInfo() != null && ((ISearchPoiData) poi.as(ISearchPoiData.class)).getPoiChildrenInfo().childType == 1) {
                point = ((ISearchPoiData) poi.as(ISearchPoiData.class)).getDisplayPoint();
            }
            this.mMapView.a(point.x, point.y, 0, (float) dhq.mBgMarker.mWidth, (float) dhq.mBgMarker.mHeight, poi.getId());
            this.poiFilters.add(poi.getId());
        }
        addItem(dhq);
    }

    public boolean clear() {
        if (this.mMarkerCache != null) {
            this.mMarkerCache.clear();
        }
        this.mNormalCurIndex = 0;
        Iterator<String> it = this.poiFilters.iterator();
        while (it.hasNext()) {
            this.mMapView.a(it.next());
        }
        this.poiFilters.clear();
        return super.clear();
    }

    public void clearFocus() {
        super.clearFocus();
    }

    private boolean showIconBgMarker(ISearchPoiData iSearchPoiData) {
        if (iSearchPoiData.getDisplayIconNameState() != 1 || TextUtils.isEmpty(iSearchPoiData.getIconSrcName())) {
            return false;
        }
        return true;
    }

    private boolean showTxtBgMarker(ISearchPoiData iSearchPoiData) {
        return iSearchPoiData.getDisplayIconNameState() == 2 && !TextUtils.isEmpty(iSearchPoiData.getName()) && iSearchPoiData.getName().length() <= 8;
    }

    private Marker getIconBgMarker(ISearchPoiData iSearchPoiData) {
        int drawableId = getDrawableId(iSearchPoiData.getIconSrcName(), this.mContext);
        if (drawableId > 0) {
            return createMarker(drawableId, 4);
        }
        return null;
    }

    private Marker getTxtBgMarker(ISearchPoiData iSearchPoiData) {
        String name = iSearchPoiData.getName();
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.poi_bg_nor, null);
        inflate.setLayoutParams(new LayoutParams(-2, -2));
        StrokeTextView strokeTextView = (StrokeTextView) inflate.findViewById(R.id.tv_name);
        strokeTextView.setText(name);
        strokeTextView.setTextColor(AMapAppGlobal.getApplication().getResources().getColor(R.color.map_poi_name));
        ((TextView) inflate.findViewById(R.id.tv_space)).setVisibility(8);
        inflate.measure(0, 0);
        inflate.layout(0, 0, inflate.getMeasuredWidth(), inflate.getMeasuredHeight());
        ImageView imageView = (ImageView) inflate.findViewById(R.id.img_dot);
        if (iSearchPoiData.getIconId() <= 0) {
            imageView.setVisibility(8);
        }
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, iSearchPoiData.getPoint(), 81);
        mapViewLayoutParams.mode = 0;
        this.mMapView.a(inflate, (FrameLayout.LayoutParams) mapViewLayoutParams);
        int i = this.mNormalCurIndex;
        this.mNormalCurIndex = i + 1;
        Marker createMarker = createMarker(i, inflate, 7, 0.0f, 0.0f, false);
        this.mMapView.a(inflate);
        return createMarker;
    }

    private Marker getFocusIconBgMarker() {
        if (isHideBgMarker()) {
            return new Marker(-999, 5, 0, 0);
        }
        return null;
    }

    private Marker getFocusTxtBgMarker(ISearchPoiData iSearchPoiData) {
        String name = iSearchPoiData.getName();
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.poi_bg_nor, null);
        inflate.setLayoutParams(new LayoutParams(-1, -2));
        StrokeTextView strokeTextView = (StrokeTextView) inflate.findViewById(R.id.tv_name);
        strokeTextView.setText(name);
        strokeTextView.setTextColor(AMapAppGlobal.getApplication().getResources().getColor(R.color.map_poi_name_hl));
        TextView textView = (TextView) inflate.findViewById(R.id.tv_space);
        textView.setText(name);
        textView.setWillNotDraw(true);
        inflate.measure(0, 0);
        inflate.layout(0, 0, inflate.getMeasuredWidth(), inflate.getMeasuredHeight());
        ImageView imageView = (ImageView) inflate.findViewById(R.id.img_dot);
        if (iSearchPoiData.getIconId() <= 0) {
            imageView.setVisibility(4);
        }
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, iSearchPoiData.getPoint(), 81);
        mapViewLayoutParams.mode = 0;
        this.mMapView.a(inflate, (FrameLayout.LayoutParams) mapViewLayoutParams);
        int i = this.mNormalCurIndex;
        this.mNormalCurIndex = i + 1;
        Marker createMarker = createMarker(i, inflate, 4, 0.0f, 0.0f, false);
        this.mMapView.a(inflate);
        return createMarker;
    }

    private boolean isHideBgMarker() {
        if (this.mSearchPoi != null && !TextUtils.equals("150500", this.mSearchPoi.getType()) && !TextUtils.equals("150600", this.mSearchPoi.getType())) {
            String id = this.mSearchPoi.getId();
            if (!TextUtils.isEmpty(id) && id.startsWith("BV")) {
                return true;
            }
        }
        return false;
    }

    private int getDrawableId(String str, Context context) {
        if (TextUtils.isEmpty(str) || context == null) {
            return -1;
        }
        return context.getResources().getIdentifier(str, ResUtils.DRAWABLE, context.getPackageName());
    }
}
