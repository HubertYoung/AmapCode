package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amapuri://carownerservice/scan"}, module = "carownerservice", pages = {"com.autonavi.minimap.basemap.route.page.CarLicenseScanPage"})
@KeepName
public final class CAROWNERSERVICE_PageScheme_DATA extends HashMap<String, Class<?>> {
    public CAROWNERSERVICE_PageScheme_DATA() {
        put("amapuri://carownerservice/scan", CarLicenseScanPage.class);
    }
}
