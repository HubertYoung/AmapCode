package com.autonavi.minimap.route.foot.inter;

import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.minimap.route.foot.model.OnFootNaviResult;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMemberNames;

@KeepClassMemberNames
@Keep
public interface IFootRouteResult extends IRouteResultData {
    int getFocusStationIndex();

    int getFocusTabIndex();

    OnFootNaviResult getOnFootPlanResult();

    byte[] getRouteData();

    boolean isParseOK();

    boolean parseData(byte[] bArr);

    void setFocusStationIndex(int i);

    void setFocusTabIndex(int i);
}
