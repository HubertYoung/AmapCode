package com.autonavi.inter.impl;

import com.amap.bundle.planhome.page.PlanHomePage;
import com.autonavi.annotation.helper.PageActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.extra.route.route"}, module = "planhome", pages = {"com.amap.bundle.planhome.page.PlanHomePage"})
@KeepName
public final class PLANHOME_PageAction_DATA extends HashMap<String, Class<?>> {
    public PLANHOME_PageAction_DATA() {
        put("amap.extra.route.route", PlanHomePage.class);
    }
}
