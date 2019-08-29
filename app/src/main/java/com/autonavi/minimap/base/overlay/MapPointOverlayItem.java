package com.autonavi.minimap.base.overlay;

import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class MapPointOverlayItem extends PointOverlayItem {
    protected HashMap<String, Serializable> itemExtra = new HashMap<>();
    private List<Long> mSubWayActiveIds = null;
    private int mType;

    public MapPointOverlayItem(GeoPoint geoPoint, int i) {
        super(geoPoint);
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }

    public void setSubWayActiveIds(List<Long> list) {
        this.mSubWayActiveIds = list;
    }

    public List<Long> getSubWayActiveIds() {
        return this.mSubWayActiveIds;
    }

    public HashMap<String, Serializable> getExtra() {
        return this.itemExtra;
    }
}
