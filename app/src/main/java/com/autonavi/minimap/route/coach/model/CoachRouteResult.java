package com.autonavi.minimap.route.coach.model;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.route.coach.inter.ICoachRouteResult;
import java.util.ArrayList;

public class CoachRouteResult implements ICoachRouteResult {
    private POI mEndPoi;
    private CoachPlanData mResult;
    private POI mStartPoi;
    private boolean request_aos_result;

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

    public CoachRouteResult(dzo dzo) {
        if (dzo != null && dzo.result) {
            this.mResult = dzo.b;
            this.request_aos_result = dzo.result;
        }
    }

    public CoachPlanData getCoachPlanResult() {
        return this.mResult;
    }

    public byte[] getRouteData() {
        return new byte[0];
    }

    public boolean isParseOK() {
        return this.mResult != null && this.request_aos_result && this.mResult.why == 0;
    }

    public boolean isNeedServiceSwitch() {
        return this.mResult != null && this.mResult.service_switch == 1;
    }

    public void setFromPOI(POI poi) {
        this.mStartPoi = poi;
    }

    public POI getFromPOI() {
        return this.mStartPoi;
    }

    public void setToPOI(POI poi) {
        this.mEndPoi = poi;
    }

    public POI getToPOI() {
        return this.mEndPoi;
    }
}
