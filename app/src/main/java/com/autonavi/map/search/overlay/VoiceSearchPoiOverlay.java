package com.autonavi.map.search.overlay;

import com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.minimap.base.overlay.Marker;

public class VoiceSearchPoiOverlay extends SearchPoiOverlay {
    private static final long serialVersionUID = -3048589140845445262L;

    public VoiceSearchPoiOverlay(bty bty) {
        super(bty);
    }

    /* access modifiers changed from: protected */
    public Marker getDefaultMarker(int i, DynamicRenderData dynamicRenderData) {
        int a = bby.a("b_poi_".concat(String.valueOf(i + 1)), this.mContext);
        if (a > 0) {
            return createMarker(i, bby.a(this.mContext.getResources().getDrawable(a)), 5, false);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Marker getFocusMarkerByIndexAndPoi(int i, SearchPoi searchPoi) {
        int i2 = i + 1;
        int i3 = i + 1000;
        StringBuilder sb = new StringBuilder("b_poi_");
        sb.append(i2);
        sb.append("_hl");
        int a = bby.a(sb.toString(), this.mContext);
        if (a > 0) {
            return createMarker(i3, bby.a(this.mContext.getResources().getDrawable(a)), 5, false);
        }
        return null;
    }
}
