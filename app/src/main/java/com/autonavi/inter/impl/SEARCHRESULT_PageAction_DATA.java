package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.map.search.fragment.PoiDetailPageNew;
import com.autonavi.map.search.fragment.SearchResultMapPage;
import com.autonavi.map.search.page.PoiDetailWebPage;
import com.autonavi.map.search.page.SearchAjxErrorPage;
import com.autonavi.map.search.page.SearchAjxNoResultPage;
import com.autonavi.map.search.page.SearchErrorIndoorPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.search.action.nativenoresult", "amap.search.action.searcherrorindoor", "amap.search.action.resultmap", "amap.search.action.detial", "amap.search.action.poidetail", "amap.search.action.searcherror"}, module = "searchresult", pages = {"com.autonavi.map.search.page.SearchAjxNoResultPage", "com.autonavi.map.search.page.SearchErrorIndoorPage", "com.autonavi.map.search.fragment.SearchResultMapPage", "com.autonavi.map.search.page.PoiDetailWebPage", "com.autonavi.map.search.fragment.PoiDetailPageNew", "com.autonavi.map.search.page.SearchAjxErrorPage"})
@KeepName
public final class SEARCHRESULT_PageAction_DATA extends HashMap<String, Class<?>> {
    public SEARCHRESULT_PageAction_DATA() {
        put("amap.search.action.nativenoresult", SearchAjxNoResultPage.class);
        put("amap.search.action.searcherrorindoor", SearchErrorIndoorPage.class);
        put("amap.search.action.resultmap", SearchResultMapPage.class);
        put("amap.search.action.detial", PoiDetailWebPage.class);
        put("amap.search.action.poidetail", PoiDetailPageNew.class);
        put("amap.search.action.searcherror", SearchAjxErrorPage.class);
    }
}
