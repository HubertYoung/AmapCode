package com.autonavi.minimap.route.train.model;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.train.inter.ITrainRouteResult;
import java.util.ArrayList;

public class RouteTrainPlanResult implements ITrainRouteResult {
    public boolean isSameCity;
    private POI mFootEndPOI;
    private POI mFootStartPOI;
    public String reasonStr;
    public ArrayList<TrainPlanBaseInfoItem> results;
    public boolean serviceSwitch = true;
    public boolean shouldShowTicket;

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

    public int getMinPrice() {
        return 0;
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

    public boolean isParseOK() {
        return true;
    }

    public boolean needSaveHistory() {
        return false;
    }

    public boolean parseData(byte[] bArr) {
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

    public ArrayList<TrainPlanBaseInfoItem> getTrainPlanInfoResult() {
        return this.results;
    }

    public RouteTrainPlanResult() {
    }

    public RouteTrainPlanResult(ejh ejh) {
        if (ejh != null && ejh.result) {
            this.results = ejh.a;
            this.shouldShowTicket = ejh.b;
            this.isSameCity = ejh.d;
            this.reasonStr = ejh.e;
            this.serviceSwitch = ejh.g;
        }
    }

    public byte[] getRouteData() {
        return new byte[0];
    }

    public boolean isNeedServiceSwitch() {
        return this.serviceSwitch;
    }

    public void setFromPOI(POI poi) {
        this.mFootStartPOI = poi;
    }

    public POI getFromPOI() {
        return this.mFootStartPOI;
    }

    public void setToPOI(POI poi) {
        this.mFootEndPOI = poi;
    }

    public POI getToPOI() {
        return this.mFootEndPOI;
    }

    public boolean hasData() {
        if (this.results != null && this.results.size() > 0) {
            return true;
        }
        return false;
    }
}
