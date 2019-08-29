package com.autonavi.bundle.entity.infolite.external;

import com.autonavi.common.model.POI;
import java.io.Serializable;
import java.util.ArrayList;

public class PoiLocationInfo implements Serializable, Cloneable {
    public ArrayList<POI> POIList;
    public int locationType;
    public int total;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
