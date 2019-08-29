package com.autonavi.minimap.map;

import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;

public interface ITrafficOverlayItem {
    boolean equals(Object obj);

    long getGeneratedTime();

    ITrafficTopic getTopic();

    void init(ITrafficTopic iTrafficTopic, String str, boolean z, POIOverlayItem pOIOverlayItem);

    boolean isLocaReport();

    void onPrepareAddItem(PointOverlay pointOverlay);

    void setGeneratedTime(long j);

    void setIsLocalReport(boolean z);
}
