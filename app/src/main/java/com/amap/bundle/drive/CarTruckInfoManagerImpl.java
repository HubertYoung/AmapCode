package com.amap.bundle.drive;

import com.amap.bundle.drive.api.ICarTruckInfoManager;
import com.amap.bundle.drive.cruise.page.AjxRouteCarCruisePage;
import com.amap.bundle.drive.navi.drivenavi.normal.page.AjxRouteCarNaviPage;
import com.amap.bundle.drive.navi.drivenavi.simulate.page.AjxRouteCarNaviSimulatePage;
import com.amap.bundle.drive.navi.motornavi.page.AjxRouteMotorNaviPage;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager.NaviType;
import com.amap.bundle.drive.naviend.drive.page.AjxRouteCarNaviEndPage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.routecommon.model.RouteType;

public class CarTruckInfoManagerImpl implements ICarTruckInfoManager {
    public boolean checkCanRoute(RouteType routeType) {
        if (routeType == RouteType.CAR) {
            return true;
        }
        if (routeType != RouteType.TRUCK || DriveUtil.getCarTruckInfo() == null) {
            return false;
        }
        if (tk.a() && (!tk.c() || !"0".equals(tk.a(tk.b())))) {
            return true;
        }
        return false;
    }

    public Class getNaviPage() {
        return AjxRouteCarNaviPage.class;
    }

    public Class getNaviEndPage() {
        return AjxRouteCarNaviEndPage.class;
    }

    public Class getSimNaviPage() {
        return AjxRouteCarNaviSimulatePage.class;
    }

    public Class getCruisePage() {
        return AjxRouteCarCruisePage.class;
    }

    public boolean isTruckNaviPage(String str) {
        if (!AjxRouteCarNaviPage.class.getName().equals(str)) {
            return false;
        }
        NaviManager a = NaviManager.a();
        if (a.h == null || a.h != NaviType.TRUCK_NAVI) {
            return false;
        }
        return true;
    }

    public boolean isSimTruckNaviPage(String str) {
        if (!AjxRouteCarNaviSimulatePage.class.getName().equals(str)) {
            return false;
        }
        NaviManager a = NaviManager.a();
        if (a.h == null || a.h != NaviType.TRUCK_SIMULATE) {
            return false;
        }
        return true;
    }

    public Class getMotorNaviPage() {
        return AjxRouteMotorNaviPage.class;
    }

    public boolean isInNavi() {
        return NaviManager.a().e();
    }
}
