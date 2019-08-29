package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.route.coach.page.CoachOrderListPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.extra.route.coach_order_list"}, module = "coach", pages = {"com.autonavi.minimap.route.coach.page.CoachOrderListPage"})
@KeepName
public final class COACH_PageAction_DATA extends HashMap<String, Class<?>> {
    public COACH_PageAction_DATA() {
        put("amap.extra.route.coach_order_list", CoachOrderListPage.class);
    }
}
