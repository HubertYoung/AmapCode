package com.amap.bundle.drive.api;

import com.autonavi.bundle.routecommon.inter.IRouteUI;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IMotorGuideManager extends bie {
    void checkShowGuide(IRouteUI iRouteUI, boolean z);

    boolean isGuideShowing();
}
