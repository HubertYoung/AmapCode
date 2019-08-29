package com.autonavi.minimap.map;

import android.text.TextUtils;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.base.overlay.POIOverlayItem;

public class FavoriteOverlayItem extends POIOverlayItem {
    private bth mSavePoint;

    public FavoriteOverlayItem(bth bth) {
        super(bth.a());
        this.mSavePoint = bth;
    }

    public FavoriteOverlayItem(GeoPoint geoPoint) {
        super(geoPoint);
    }

    public bth getSavePoint() {
        return this.mSavePoint;
    }

    public int getPointType() {
        if (this.mSavePoint == null || getPOI() == null) {
            return -1;
        }
        POI poi = getPOI();
        int intValue = (poi.getPoiExtra() == null || !poi.getPoiExtra().containsKey("pointType")) ? -1 : ((Integer) poi.getPoiExtra().get("pointType")).intValue();
        if (intValue == -1) {
            String str = this.mSavePoint.d;
            intValue = "家".equals(str) ? 1 : "公司".equals(str) ? 2 : 0;
        }
        return intValue;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof FavoriteOverlayItem)) {
            FavoriteOverlayItem favoriteOverlayItem = (FavoriteOverlayItem) obj;
            if (!(favoriteOverlayItem.getSavePoint() == null || this.mSavePoint == null)) {
                String str = favoriteOverlayItem.getSavePoint().a;
                String str2 = this.mSavePoint.a;
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    return str.equals(str2);
                }
            }
        }
        return false;
    }
}
