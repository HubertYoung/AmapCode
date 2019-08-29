package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.map.search.fragment.SearchResultMapPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amapuri://search/resultPage"}, module = "searchresult", pages = {"com.autonavi.map.search.fragment.SearchResultMapPage"})
@KeepName
public final class SEARCHRESULT_PageScheme_DATA extends HashMap<String, Class<?>> {
    public SEARCHRESULT_PageScheme_DATA() {
        put("amapuri://search/resultPage", SearchResultMapPage.class);
    }
}
