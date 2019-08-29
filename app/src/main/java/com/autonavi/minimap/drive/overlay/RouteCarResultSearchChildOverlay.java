package com.autonavi.minimap.drive.overlay;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointItem;
import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointOverlay;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.widget.StrokeTextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RouteCarResultSearchChildOverlay extends DriveBaseBoardPointOverlay<DriveBaseBoardPointItem> {
    public static final int CHILD_ICON_PARK = 41;
    private boolean mIsCalcingOverlay = true;
    private boolean mIsFromEtrip;
    private int mItemIndex = -1;
    private ArrayList<String> poiFilters = new ArrayList<>();

    public int getCollideType() {
        return 7;
    }

    public RouteCarResultSearchChildOverlay(bty bty) {
        super(bty);
        setClearWhenLoseFocus(true);
        this.mIsCalcingOverlay = true;
    }

    public void setFromEtrip(boolean z) {
        this.mIsFromEtrip = z;
    }

    public void addChildrenPOI(List<POI> list) {
        if (list != null) {
            for (POI as : list) {
                dhq createChildPoiItem = createChildPoiItem((ISearchPoiData) as.as(ISearchPoiData.class));
                addItem(createChildPoiItem);
                setPointItemVisble((PointOverlayItem) createChildPoiItem, true, true);
            }
        }
    }

    private dhq createChildPoiItem(ISearchPoiData iSearchPoiData) {
        ISearchPoiData iSearchPoiData2 = iSearchPoiData;
        if (iSearchPoiData2 == null) {
            return null;
        }
        dhq dhq = new dhq(iSearchPoiData2);
        dhq.mBubbleMarker = new Marker(-999, 5, 0, 0);
        dhq.mDefaultMarker = createMarker(iSearchPoiData.getIconId(), 4);
        if (iSearchPoiData.getPoiChildrenInfo() == null || iSearchPoiData.getPoiChildrenInfo().childType == 41) {
            bty bty = this.mMapView;
            int i = iSearchPoiData.getPoint().x;
            bty.a(i, iSearchPoiData.getPoint().y, 2, (float) agn.a((Context) AMapAppGlobal.getApplication(), 8.0f), (float) agn.a((Context) AMapAppGlobal.getApplication(), 8.0f), iSearchPoiData.getId());
        } else {
            MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, dhq.getGeoPoint(), 3);
            mapViewLayoutParams.mode = 0;
            TextView createPoiNameView = createPoiNameView(this.mContext, iSearchPoiData.getShortName());
            createPoiNameView.setTextSize(1, 11.0f);
            createPoiNameView.setTextColor(this.mContext.getResources().getColor(R.color.map_poi_name));
            this.mMapView.a((View) createPoiNameView, (LayoutParams) mapViewLayoutParams);
            TextView createPoiNameView2 = createPoiNameView(this.mContext, iSearchPoiData.getShortName());
            createPoiNameView2.setTextSize(1, 12.0f);
            createPoiNameView2.setTextColor(this.mContext.getResources().getColor(R.color.map_poi_name_hl));
            this.mMapView.a((View) createPoiNameView2, (LayoutParams) mapViewLayoutParams);
            int i2 = this.mItemIndex + 1;
            this.mItemIndex = i2;
            dhq.mBgFocusMarker = createMarker(i2, (View) createPoiNameView2, 7, 0.0f, 0.0f, false);
            int i3 = this.mItemIndex + 1;
            this.mItemIndex = i3;
            dhq.mBgMarker = createMarker(i3, (View) createPoiNameView, 7, 0.0f, 0.0f, false);
            this.mMapView.a((View) createPoiNameView);
            this.mMapView.a((View) createPoiNameView2);
            this.mMapView.a(iSearchPoiData.getPoint().x, iSearchPoiData.getPoint().y, 0, (float) createPoiNameView.getMeasuredWidth(), (float) createPoiNameView.getMeasuredHeight(), iSearchPoiData.getId());
        }
        this.poiFilters.add(iSearchPoiData.getId());
        return dhq;
    }

    private TextView createPoiNameView(Context context, String str) {
        StrokeTextView strokeTextView = new StrokeTextView(context);
        strokeTextView.setTextSize(1, 11.0f);
        strokeTextView.setTextColor(context.getResources().getColor(R.color.map_poi_name));
        strokeTextView.setPadding(agn.a(context, 8.0f), agn.a(context, 3.0f), agn.a(context, 2.0f), agn.a(context, 3.0f));
        strokeTextView.setText(str);
        strokeTextView.setSingleLine();
        strokeTextView.measure(0, 0);
        strokeTextView.layout(0, 0, strokeTextView.getMeasuredWidth(), strokeTextView.getMeasuredHeight());
        return strokeTextView;
    }

    public void clearFocus() {
        super.clearFocus();
    }

    public void setVisible(boolean z) {
        super.setVisible(z);
    }

    public boolean clear() {
        this.mItemIndex = -1;
        Iterator<String> it = this.poiFilters.iterator();
        while (it.hasNext()) {
            this.mMapView.a(it.next());
        }
        this.poiFilters.clear();
        return super.clear();
    }

    public void onUpdateDirection(int i, int i2) {
        boolean z;
        DriveBaseBoardPointItem driveBaseBoardPointItem = (DriveBaseBoardPointItem) getItem(i);
        boolean z2 = true;
        if (driveBaseBoardPointItem == null || !driveBaseBoardPointItem.c()) {
            z = i2 != -1;
            if (i2 == -1) {
                z2 = false;
            }
        } else {
            z = true;
        }
        setPointItemVisble(i, z, z2);
    }

    public void setFocus(int i, boolean z) {
        if (!this.mIsFromEtrip) {
            super.setFocus(i, z);
            setShowFocusTop(true);
        }
    }

    public boolean isCalcingOverlay() {
        return this.mIsCalcingOverlay;
    }

    public void setIsCalcingOverlay(boolean z) {
        this.mIsCalcingOverlay = z;
    }
}
