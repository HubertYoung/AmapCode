package com.autonavi.bundle.routecommon.model;

import com.amap.bundle.datamodel.IResultData;
import com.autonavi.common.model.POI;
import java.util.ArrayList;

public interface IRouteResultData extends IResultData {
    POI getFromPOI();

    String getMethod();

    ArrayList<POI> getMidPOIs();

    POI getShareFromPOI();

    ArrayList<POI> getShareMidPOIs();

    POI getShareToPOI();

    POI getToPOI();

    boolean needSaveHistory();

    void setFromPOI(POI poi);

    void setMethod(String str);

    void setMidPOIs(ArrayList<POI> arrayList);

    void setToPOI(POI poi);
}
