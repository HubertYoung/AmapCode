package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.route.sharebike.page.ShareBikePage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amapuri://shareBike/shareBikeMainView", "amapuri://shareBike/shareBikeBikingView"}, module = "sharebike", pages = {"com.autonavi.minimap.route.sharebike.page.ShareBikePage", "com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage"})
@KeepName
public final class SHAREBIKE_PageScheme_DATA extends HashMap<String, Class<?>> {
    public SHAREBIKE_PageScheme_DATA() {
        put("amapuri://shareBike/shareBikeMainView", ShareBikePage.class);
        put("amapuri://shareBike/shareBikeBikingView", ShareRidingMapPage.class);
    }
}
