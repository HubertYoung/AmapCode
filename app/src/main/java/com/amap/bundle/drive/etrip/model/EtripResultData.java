package com.amap.bundle.drive.etrip.model;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import java.util.ArrayList;

public class EtripResultData implements IRouteResultData {
    private POI mFromPoi = null;
    private POI mToPoi = null;

    public Class<?> getClassType() {
        return null;
    }

    public String getKey() {
        return null;
    }

    public String getMethod() {
        return null;
    }

    public ArrayList<POI> getMidPOIs() {
        return null;
    }

    public POI getShareFromPOI() {
        return null;
    }

    public ArrayList<POI> getShareMidPOIs() {
        return null;
    }

    public POI getShareToPOI() {
        return null;
    }

    public boolean hasData() {
        return false;
    }

    public boolean needSaveHistory() {
        return false;
    }

    public void reset() {
    }

    public void restoreData() {
    }

    public void saveData() {
    }

    public void setKey(String str) {
    }

    public void setMethod(String str) {
    }

    public void setMidPOIs(ArrayList<POI> arrayList) {
    }

    public void setFromPOI(POI poi) {
        this.mFromPoi = poi;
    }

    public POI getFromPOI() {
        return this.mFromPoi;
    }

    public void setToPOI(POI poi) {
        this.mToPoi = poi;
    }

    public POI getToPOI() {
        return this.mToPoi;
    }
}
