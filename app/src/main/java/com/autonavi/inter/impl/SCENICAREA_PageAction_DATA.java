package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.bundle.scenicarea.page.SearchScenicEyireMapPage;
import com.autonavi.bundle.scenicarea.page.SearchScenicMapRoutePage;
import com.autonavi.bundle.scenicarea.page.SearchScenicWalkmanMapPage;
import com.autonavi.bundle.smart.scenic.page.SerachSmartScenicSetMapPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"search_scenicarea_walkman_map", "search_scenicarea_eyire", "search_smart_scenicarea_set_page", "search_scenicarea_walk_route"}, module = "scenicarea", pages = {"com.autonavi.bundle.scenicarea.page.SearchScenicWalkmanMapPage", "com.autonavi.bundle.scenicarea.page.SearchScenicEyireMapPage", "com.autonavi.bundle.smart.scenic.page.SerachSmartScenicSetMapPage", "com.autonavi.bundle.scenicarea.page.SearchScenicMapRoutePage"})
@KeepName
public final class SCENICAREA_PageAction_DATA extends HashMap<String, Class<?>> {
    public SCENICAREA_PageAction_DATA() {
        put("search_scenicarea_walkman_map", SearchScenicWalkmanMapPage.class);
        put("search_scenicarea_eyire", SearchScenicEyireMapPage.class);
        put("search_smart_scenicarea_set_page", SerachSmartScenicSetMapPage.class);
        put("search_scenicarea_walk_route", SearchScenicMapRoutePage.class);
    }
}
