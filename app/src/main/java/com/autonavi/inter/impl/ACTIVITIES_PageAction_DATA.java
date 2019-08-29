package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.bundle.activities.page.ActivitiesPage;
import com.autonavi.minimap.bundle.activities.page.ExamplePicturePage;
import com.autonavi.minimap.bundle.activities.page.LookOverPicturePage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.acticities", "amap.basemap.action.examplepage", "amap.basemap.action.lookoverpicture"}, module = "activities", pages = {"com.autonavi.minimap.bundle.activities.page.ActivitiesPage", "com.autonavi.minimap.bundle.activities.page.ExamplePicturePage", "com.autonavi.minimap.bundle.activities.page.LookOverPicturePage"})
@KeepName
public final class ACTIVITIES_PageAction_DATA extends HashMap<String, Class<?>> {
    public ACTIVITIES_PageAction_DATA() {
        put("amap.basemap.action.acticities", ActivitiesPage.class);
        put("amap.basemap.action.examplepage", ExamplePicturePage.class);
        put("amap.basemap.action.lookoverpicture", LookOverPicturePage.class);
    }
}
