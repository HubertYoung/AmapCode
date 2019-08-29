package com.autonavi.minimap.map;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;

public class BasePoiOverlayItem extends POIOverlayItem {
    private static final int[] DEFAULT_MARKER = {R.drawable.b_poi_1, R.drawable.b_poi_2, R.drawable.b_poi_3, R.drawable.b_poi_4, R.drawable.b_poi_5, R.drawable.b_poi_6, R.drawable.b_poi_7, R.drawable.b_poi_8, R.drawable.b_poi_9, R.drawable.b_poi_10, R.drawable.b_poi_geo_hl};
    private static final int[] FOCUS_MARKER = {R.drawable.b_poi_1_hl, R.drawable.b_poi_2_hl, R.drawable.b_poi_3_hl, R.drawable.b_poi_4_hl, R.drawable.b_poi_5_hl, R.drawable.b_poi_6_hl, R.drawable.b_poi_7_hl, R.drawable.b_poi_8_hl, R.drawable.b_poi_9_hl, R.drawable.b_poi_10_hl, R.drawable.b_poi_hl};
    public static final int LINE_ID_DEFAULT = -1;
    public static final int LINE_ID_NOLINE = -100;
    private int iconIndex;
    private int mPageIndex = 0;
    protected String mTitle;
    public String tileId = "";

    public BasePoiOverlayItem(POI poi, int i) {
        int i2;
        int i3;
        super(poi);
        this.iconIndex = i;
        if (poi != null && poi.as(ISearchPoiData.class) != null) {
            GeoPoint displayPoint = ((ISearchPoiData) poi.as(ISearchPoiData.class)).getDisplayPoint();
            if (displayPoint == null || displayPoint.x <= 0 || displayPoint.y <= 0) {
                int i4 = poi.getPoint().x;
                i2 = poi.getPoint().y;
                i3 = i4;
            } else {
                i3 = displayPoint.x;
                i2 = displayPoint.y;
            }
            getGeoPoint().x = i3;
            getGeoPoint().y = i2;
        }
    }

    public void setPageIndex(int i) {
        this.mPageIndex = i;
    }

    public int getPageIndex() {
        return this.mPageIndex;
    }

    public void onPrepareAddItem(PointOverlay pointOverlay) {
        super.onPrepareAddItem(pointOverlay);
        if (getPOI().getIconId() == 0) {
            this.mDefaultMarker = pointOverlay.createMarker(DEFAULT_MARKER[this.iconIndex], 5);
        } else {
            this.mDefaultMarker = pointOverlay.createMarker(getPOI().getIconId(), 5);
        }
    }

    public void onPrepareSetFocus(PointOverlay pointOverlay) {
        super.onPrepareSetFocus(pointOverlay);
        int i = this.mDefaultMarker != null ? this.mDefaultMarker.mID : -999;
        if (i != -999) {
            for (int i2 = 0; i2 < DEFAULT_MARKER.length; i2++) {
                if (DEFAULT_MARKER[i2] == i) {
                    this.mBubbleMarker = pointOverlay.createMarker(FOCUS_MARKER[i2], 5);
                }
            }
            if (this.mBubbleMarker == null) {
                this.mBubbleMarker = pointOverlay.createMarker(R.drawable.b_poi_hl, 5);
            }
        }
    }
}
