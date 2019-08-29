package com.autonavi.minimap.life.common.overlay;

import android.text.TextUtils;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;

public class LifeMapBaseOverlay extends PointOverlay {
    public LifeMapBaseOverlay(bty bty) {
        super(bty);
        showReversed(true);
        this.mOverlayFocusMarker = new Marker(-999, 4, 0, 0);
    }

    public POIOverlayItem addSinglePoi(POI poi) {
        POIOverlayItem pOIOverlayItem = new POIOverlayItem(poi);
        pOIOverlayItem.mDefaultMarker = createMarker(R.drawable.b_poi, 5);
        pOIOverlayItem.mBubbleMarker = createMarker(R.drawable.b_poi_hl, 5);
        addItem(pOIOverlayItem);
        return pOIOverlayItem;
    }

    public POIOverlayItem addNormalPoi(POI poi, int i) {
        POIOverlayItem pOIOverlayItem = new POIOverlayItem(poi);
        StringBuilder sb = new StringBuilder("b_poi_");
        int i2 = i + 1;
        sb.append(i2);
        pOIOverlayItem.mDefaultMarker = createMarker(getDrawableId(sb.toString()), 5);
        StringBuilder sb2 = new StringBuilder("b_poi_");
        sb2.append(i2);
        sb2.append("_hl");
        pOIOverlayItem.mBubbleMarker = createMarker(getDrawableId(sb2.toString()), 5);
        setBubbleAnimator(2);
        addItem(pOIOverlayItem);
        return pOIOverlayItem;
    }

    private int getDrawableId(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        return this.mContext.getResources().getIdentifier(str, ResUtils.DRAWABLE, this.mContext.getPackageName());
    }
}
