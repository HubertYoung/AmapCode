package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.mine.measure.page.MeasurePage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.measure_page"}, module = "toolbox", pages = {"com.autonavi.mine.measure.page.MeasurePage"})
@KeepName
public final class TOOLBOX_PageAction_DATA extends HashMap<String, Class<?>> {
    public TOOLBOX_PageAction_DATA() {
        put("amap.basemap.action.measure_page", MeasurePage.class);
    }
}
