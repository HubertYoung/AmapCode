package com.autonavi.minimap.search.model.searchpoi.searchpoitype;

import com.autonavi.common.model.POI;
import java.io.Serializable;
import java.util.Collection;

public class ChildrenPoiData implements Serializable {
    public static final int POI_CHILD = 2;
    public static final int STATION_CHILD = 1;
    public int childType;
    public Collection<? extends POI> geoList;
    public Collection<? extends POI> poiList;
    public Collection<? extends POI> stationList;
}
