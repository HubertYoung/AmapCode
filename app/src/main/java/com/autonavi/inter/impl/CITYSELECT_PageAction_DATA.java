package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.bundle.cityselect.page.SwitchCityNodePage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.switch_city_node_page"}, module = "cityselect", pages = {"com.autonavi.bundle.cityselect.page.SwitchCityNodePage"})
@KeepName
public final class CITYSELECT_PageAction_DATA extends HashMap<String, Class<?>> {
    public CITYSELECT_PageAction_DATA() {
        put("amap.basemap.action.switch_city_node_page", SwitchCityNodePage.class);
    }
}
