package com.autonavi.minimap.map;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;
import java.util.ArrayList;
import java.util.Iterator;

public class TrafficOverlayItem extends POIOverlayItem implements ITrafficOverlayItem {
    private ITrafficOverlayItem mDelgate = ((ITrafficOverlayItem) ank.a(ITrafficOverlayItem.class));

    public void init(ITrafficTopic iTrafficTopic, String str, boolean z, POIOverlayItem pOIOverlayItem) {
    }

    public TrafficOverlayItem(ITrafficTopic iTrafficTopic, GeoPoint geoPoint, String str, boolean z) {
        super(geoPoint);
        if (this.mDelgate != null) {
            this.mDelgate.init(iTrafficTopic, str, z, this);
        }
    }

    public ITrafficTopic getTopic() {
        if (this.mDelgate != null) {
            return this.mDelgate.getTopic();
        }
        return null;
    }

    public void onPrepareAddItem(PointOverlay pointOverlay) {
        super.onPrepareAddItem(pointOverlay);
        if (this.mDelgate != null) {
            this.mDelgate.onPrepareAddItem(pointOverlay);
        }
    }

    public static boolean pondingContains(ArrayList<String> arrayList) {
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            if ("110".equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean sinaContains(ArrayList<String> arrayList) {
        if (arrayList == null) {
            return false;
        }
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            if ("107".equals(it.next())) {
                return true;
            }
        }
        return false;
    }

    public void setGeneratedTime(long j) {
        if (this.mDelgate != null) {
            this.mDelgate.setGeneratedTime(j);
        }
    }

    public long getGeneratedTime() {
        if (this.mDelgate != null) {
            return this.mDelgate.getGeneratedTime();
        }
        return 0;
    }

    public void setIsLocalReport(boolean z) {
        if (this.mDelgate != null) {
            this.mDelgate.setIsLocalReport(z);
        }
    }

    public boolean isLocaReport() {
        if (this.mDelgate != null) {
            return this.mDelgate.isLocaReport();
        }
        return false;
    }
}
