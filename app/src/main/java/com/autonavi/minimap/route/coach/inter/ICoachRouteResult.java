package com.autonavi.minimap.route.coach.inter;

import com.autonavi.minimap.route.coach.model.CoachPlanData;
import com.autonavi.minimap.route.export.model.IRouteResultData;

public interface ICoachRouteResult extends IRouteResultData {
    CoachPlanData getCoachPlanResult();

    byte[] getRouteData();

    boolean isNeedServiceSwitch();

    boolean isParseOK();

    boolean parseData(byte[] bArr);
}
