package com.autonavi.server.request;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"diu"}, url = "ws/auth/user-device?")
public class UserDeviceUrlWrapper implements ParamEntity {
    public String agooid;
    public int cache_expired = 0;
    public int city_switched = 0;
    public int collect_info = 0;
    public String dai;
    public String dbrand;
    public String dcs;
    public String ddevice;
    public String device_id;
    public String dfp;
    public String dhost;
    public String dhw;
    public String dmf;
    public String dmodel;
    public String dproduct;
    public String dsn;
    public String dtags;
    public String dtime;
    public String dvcode;
    public String dvid;
    public String dvinc;
    public String dvrel;
    public String dvsdk;
    public String imsi;
    public String lat;
    public String lon;
    public String os = "ANDROID";
    public int pushopen = 0;
    public String rom_ver;
    public String token;
}
