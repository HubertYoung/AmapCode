package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"searchRoute", "commentsWrite", "openPoi", "mapControl", "showOnMap", "getPoiInfo", "searchSuggest", "searchCategory", "showNames"}, jsActions = {"com.autonavi.map.search.js.action.SearchRouteAction", "com.autonavi.map.search.js.action.EditCommentAction", "com.autonavi.map.search.js.action.OpenPoiAction", "com.autonavi.map.search.js.action.MapControlAction", "com.autonavi.map.search.js.action.ShowOnMapAction", "com.autonavi.map.search.js.action.GetPoiInfoAction", "com.autonavi.map.search.js.action.SearchSuggestAction", "com.autonavi.map.search.js.action.SearchCategoryAction", "com.autonavi.map.search.js.action.BrandIconRequestAction"}, module = "searchresult")
@KeepName
public final class SEARCHRESULT_JsAction_DATA extends HashMap<String, Class<?>> {
    public SEARCHRESULT_JsAction_DATA() {
        put("searchRoute", byr.class);
        put("commentsWrite", bym.class);
        put("openPoi", byp.class);
        put("mapControl", byo.class);
        put("showOnMap", byt.class);
        put("getPoiInfo", byn.class);
        put("searchSuggest", bys.class);
        put("searchCategory", byq.class);
        put("showNames", byl.class);
    }
}
