package com.autonavi.minimap.route.voice;

import com.autonavi.minimap.route.voice.model.PoiModel;
import com.autonavi.minimap.route.voice.model.RoutePlanModel;
import com.autonavi.minimap.route.voice.model.RouteRideNaviModel;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public interface IRouteVoice {
    void exitNavi(int i);

    void requestRouteFootNavi(int i, PoiModel poiModel);

    void requestRoutePlan(int i, RoutePlanModel routePlanModel);

    void requestRouteRideNavi(int i, RouteRideNaviModel routeRideNaviModel);

    void searchBusLine(int i, String str, String str2);

    void searchSubwayLine(int i, String str);

    void setExitNaviListener(ekm ekm);
}
