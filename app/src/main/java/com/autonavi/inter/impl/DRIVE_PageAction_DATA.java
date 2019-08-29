package com.autonavi.inter.impl;

import com.amap.bundle.drive.cruise.page.AjxRouteCarCruisePage;
import com.amap.bundle.drive.result.driveresult.restrict.AjxRouteCarRestrictPage;
import com.amap.bundle.drive.setting.navisetting.page.NaviSettingPage;
import com.autonavi.annotation.helper.PageActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.drive.action.navigation.prefer", "amap.drive.action.edog", "amap.basemap.action.car_restrict"}, module = "drive", pages = {"com.amap.bundle.drive.setting.navisetting.page.NaviSettingPage", "com.amap.bundle.drive.cruise.page.AjxRouteCarCruisePage", "com.amap.bundle.drive.result.driveresult.restrict.AjxRouteCarRestrictPage"})
@KeepName
public final class DRIVE_PageAction_DATA extends HashMap<String, Class<?>> {
    public DRIVE_PageAction_DATA() {
        put("amap.drive.action.navigation.prefer", NaviSettingPage.class);
        put("amap.drive.action.edog", AjxRouteCarCruisePage.class);
        put("amap.basemap.action.car_restrict", AjxRouteCarRestrictPage.class);
    }
}
