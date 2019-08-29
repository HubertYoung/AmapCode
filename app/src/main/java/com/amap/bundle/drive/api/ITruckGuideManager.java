package com.amap.bundle.drive.api;

import com.autonavi.bundle.routecommon.inter.IRouteUI;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepImplementations
@KeepName
public interface ITruckGuideManager extends bie {

    public interface a {
        void a(boolean z);
    }

    void checkShowGuide(IRouteUI iRouteUI);

    boolean isGuideShowing();

    void setTruckGuideListener(a aVar);
}
