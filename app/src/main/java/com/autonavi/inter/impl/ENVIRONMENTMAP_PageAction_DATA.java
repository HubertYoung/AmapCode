package com.autonavi.inter.impl;

import com.amap.bundle.environmentmap.page.SearchEnvironmentMapPage;
import com.autonavi.annotation.helper.PageActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"environment_map_page"}, module = "environmentmap", pages = {"com.amap.bundle.environmentmap.page.SearchEnvironmentMapPage"})
@KeepName
public final class ENVIRONMENTMAP_PageAction_DATA extends HashMap<String, Class<?>> {
    public ENVIRONMENTMAP_PageAction_DATA() {
        put("environment_map_page", SearchEnvironmentMapPage.class);
    }
}
