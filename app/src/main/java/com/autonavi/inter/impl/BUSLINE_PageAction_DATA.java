package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineResultPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineStationListPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineStationMapPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.extra.route.busline_detail", "amap.extra.route.busline_result", "amap.extra.route.busline_station_list", "amap.extra.route.busline_station_map"}, module = "busline", pages = {"com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage", "com.autonavi.minimap.route.bus.busline.page.BusLineResultPage", "com.autonavi.minimap.route.bus.busline.page.BusLineStationListPage", "com.autonavi.minimap.route.bus.busline.page.BusLineStationMapPage"})
@KeepName
public final class BUSLINE_PageAction_DATA extends HashMap<String, Class<?>> {
    public BUSLINE_PageAction_DATA() {
        put("amap.extra.route.busline_detail", BusLineDetailPage.class);
        put("amap.extra.route.busline_result", BusLineResultPage.class);
        put("amap.extra.route.busline_station_list", BusLineStationListPage.class);
        put("amap.extra.route.busline_station_map", BusLineStationMapPage.class);
    }
}
