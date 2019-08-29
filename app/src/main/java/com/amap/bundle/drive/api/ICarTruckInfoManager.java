package com.amap.bundle.drive.api;

import com.autonavi.bundle.routecommon.model.RouteType;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface ICarTruckInfoManager extends bie {
    boolean checkCanRoute(RouteType routeType);

    Class getCruisePage();

    Class getMotorNaviPage();

    Class getNaviEndPage();

    Class getNaviPage();

    Class getSimNaviPage();

    boolean isInNavi();

    boolean isSimTruckNaviPage(String str);

    boolean isTruckNaviPage(String str);
}
