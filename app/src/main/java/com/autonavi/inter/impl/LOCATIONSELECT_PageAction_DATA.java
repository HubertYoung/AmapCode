package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.bundle.locationselect.page.SelectFixPoiFromMapAjx3Page;
import com.autonavi.minimap.bundle.locationselect.page.SelectFixPoiFromMapPage;
import com.autonavi.minimap.bundle.locationselect.page.SelectPoiFromMapPage;
import com.autonavi.minimap.bundle.locationselect.page.SelectRoadFromMapPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.base_select_fix_poi_from_map_ajx_page", "amap.basemap.action.base_select_fix_poi_from_map_page", "amap.basemap.action.select_road_from_map", "amap.basemap.action.base_select_poi_from_map_page"}, module = "locationselect", pages = {"com.autonavi.minimap.bundle.locationselect.page.SelectFixPoiFromMapAjx3Page", "com.autonavi.minimap.bundle.locationselect.page.SelectFixPoiFromMapPage", "com.autonavi.minimap.bundle.locationselect.page.SelectRoadFromMapPage", "com.autonavi.minimap.bundle.locationselect.page.SelectPoiFromMapPage"})
@KeepName
public final class LOCATIONSELECT_PageAction_DATA extends HashMap<String, Class<?>> {
    public LOCATIONSELECT_PageAction_DATA() {
        put("amap.basemap.action.base_select_fix_poi_from_map_ajx_page", SelectFixPoiFromMapAjx3Page.class);
        put("amap.basemap.action.base_select_fix_poi_from_map_page", SelectFixPoiFromMapPage.class);
        put("amap.basemap.action.select_road_from_map", SelectRoadFromMapPage.class);
        put("amap.basemap.action.base_select_poi_from_map_page", SelectPoiFromMapPage.class);
    }
}
