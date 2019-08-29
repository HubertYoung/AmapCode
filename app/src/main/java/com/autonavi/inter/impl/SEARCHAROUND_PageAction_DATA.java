package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;
import search.page.SearchCategoryFromTipPage;
import search.page.SearchFromAroundPage;

@PageActionLogger(actions = {"amap.search.action.category", "amap.search.action.arround"}, module = "searcharound", pages = {"search.page.SearchCategoryFromTipPage", "search.page.SearchFromAroundPage"})
@KeepName
public final class SEARCHAROUND_PageAction_DATA extends HashMap<String, Class<?>> {
    public SEARCHAROUND_PageAction_DATA() {
        put("amap.search.action.category", SearchCategoryFromTipPage.class);
        put("amap.search.action.arround", SearchFromAroundPage.class);
    }
}
