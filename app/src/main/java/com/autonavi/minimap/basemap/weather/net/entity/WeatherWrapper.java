package com.autonavi.minimap.basemap.weather.net.entity;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.common.model.GeoPoint;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"lon", "lat", "adiu"}, url = "/ws/valueadded/weather/v2")
@KeepImplementations
@KeepName
public class WeatherWrapper implements ParamEntity {
    String image_standard = "5";
    String lat;
    String lon;

    public WeatherWrapper(GeoPoint geoPoint) {
        this.lon = String.valueOf(geoPoint.getLongitude());
        this.lat = String.valueOf(geoPoint.getLatitude());
    }
}
