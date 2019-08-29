package com.autonavi.map.search.overlay;

import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlay;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"SE_NO_SERIALVERSIONID"})
public class SearchGeoOverlay extends PointOverlay<bzv> {
    public SearchGeoOverlay(bty bty) {
        super(bty);
        setAnimatorType(2);
    }

    public void addGeoItem(POI poi) {
        if (poi != null) {
            bzv bzv = new bzv(poi.getPoint());
            bzv.mDefaultMarker = createMarker(R.drawable.b_poi_geo_hl, 4);
            bzv.mFocusMarker = createMarker(R.drawable.b_poi_hl, 9, 0.5f, 0.87f);
            bzv.mBgMarker = getIconBgMarker((SearchPoi) poi.as(SearchPoi.class));
            bzv.a = poi;
            addItem(bzv);
            setMoveToFocus(false);
        }
    }

    private Marker getIconBgMarker(SearchPoi searchPoi) {
        int a = bby.a(searchPoi.getIconSrcName(), this.mContext);
        if (a > 0) {
            return createMarker(a, 4);
        }
        return null;
    }
}
