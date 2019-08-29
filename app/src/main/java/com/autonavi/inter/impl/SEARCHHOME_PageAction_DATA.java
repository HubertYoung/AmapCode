package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.map.search.page.SearchPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.search.action.searchfragment"}, module = "searchhome", pages = {"com.autonavi.map.search.page.SearchPage"})
@KeepName
public final class SEARCHHOME_PageAction_DATA extends HashMap<String, Class<?>> {
    public SEARCHHOME_PageAction_DATA() {
        put("amap.search.action.searchfragment", SearchPage.class);
    }
}
