package com.amap.bundle.drive.ajx.inter;

import com.autonavi.common.model.POI;
import java.util.List;

public interface OnTripPoiChangeListener {
    POI getEndPOI();

    List<POI> getMidPOIs();

    te getRegoPOI();

    POI getStartPOI();

    void onEndPOIChanged(POI poi, int i);

    void onMidPOIChanged(List<POI> list);
}
