package com.autonavi.minimap.route.bus.realtimebus.data;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

public final class SearchBuslines {

    @KeepClassMembers
    @Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"channel", "diu", "div", "_aosmd5"}, url = "ws/shield/maps/mapapi/realtimebus/search/lines/")
    @Keep
    @KeepName
    public static class BuslinesEntity implements ParamEntity {
        private String station_id;

        public BuslinesEntity(String str) {
            this.station_id = str;
        }
    }
}
