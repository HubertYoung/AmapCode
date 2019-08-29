package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amapuri://bus/busRemind"}, module = "busnavi", pages = {"com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailPage"})
@KeepName
public final class BUSNAVI_PageScheme_DATA extends HashMap<String, Class<?>> {
    public BUSNAVI_PageScheme_DATA() {
        put("amapuri://bus/busRemind", BusNaviDetailPage.class);
    }
}
