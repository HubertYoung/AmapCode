package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.bundle.routecommute.bus.details.BusCommuteDetailsPage;
import com.autonavi.bundle.routecommute.bus.details.BusCommuteListPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"bus_commute_details_page", "bus_commute_list_page"}, module = "routecommute", pages = {"com.autonavi.bundle.routecommute.bus.details.BusCommuteDetailsPage", "com.autonavi.bundle.routecommute.bus.details.BusCommuteListPage"})
@KeepName
public final class ROUTECOMMUTE_PageAction_DATA extends HashMap<String, Class<?>> {
    public ROUTECOMMUTE_PageAction_DATA() {
        put("bus_commute_details_page", BusCommuteDetailsPage.class);
        put("bus_commute_list_page", BusCommuteListPage.class);
    }
}
