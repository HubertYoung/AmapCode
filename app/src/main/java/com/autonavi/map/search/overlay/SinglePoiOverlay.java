package com.autonavi.map.search.overlay;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;

public class SinglePoiOverlay extends PointOverlay<POIOverlayItem> {
    private static final int CHILD_STATION_POI_TYPE = 1;
    private static final int DEFAULT_POI_TYPE = 0;
    private static final long serialVersionUID = -3032963367351171246L;
    private int mPoiType = 0;

    public SinglePoiOverlay(bty bty) {
        super(bty);
    }

    public void setPoiType(int i) {
        this.mPoiType = i;
    }

    public void addPoi(POI poi) {
        if (poi != null) {
            POIOverlayItem pOIOverlayItem = new POIOverlayItem(poi);
            if (this.mPoiType == 1) {
                pOIOverlayItem.mBubbleMarker = createMarker(R.drawable.poi_station, 5);
            } else {
                pOIOverlayItem.mBubbleMarker = createMarker(R.drawable.b_poi_hl, 5);
            }
            addItem(pOIOverlayItem);
        }
    }
}
