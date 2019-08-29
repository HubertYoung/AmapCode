package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.bundle.amaphome.page.MapHomeTabPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.default_page"}, module = "amaphome", pages = {"com.autonavi.bundle.amaphome.page.MapHomeTabPage"})
@KeepName
public final class AMAPHOME_PageAction_DATA extends HashMap<String, Class<?>> {
    public AMAPHOME_PageAction_DATA() {
        put("amap.basemap.action.default_page", MapHomeTabPage.class);
    }
}
