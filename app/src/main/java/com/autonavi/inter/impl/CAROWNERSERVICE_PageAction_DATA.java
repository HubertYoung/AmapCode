package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.carowner_license_scan_page"}, module = "carownerservice", pages = {"com.autonavi.minimap.basemap.route.page.CarLicenseScanPage"})
@KeepName
public final class CAROWNERSERVICE_PageAction_DATA extends HashMap<String, Class<?>> {
    public CAROWNERSERVICE_PageAction_DATA() {
        put("amap.basemap.action.carowner_license_scan_page", CarLicenseScanPage.class);
    }
}
