package com.autonavi.minimap.route.logs.operation;

import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.minimap.archivement.param.ReportRequest;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"tid", "type"}, url = "ws/oss/achievement/report/?")
public class OperationCollectionParam implements ParamEntity {
    public static final int TYPE_ADD_ROUTECOMMUTE_SHORTCUT = 19;
    public static final int TYPE_BUS_NAV = 2;
    public static final int TYPE_BUS_ROUTE_SELECT = 1;
    public static final int TYPE_DRIVE_NAV = 4;
    public static final int TYPE_ELE_RIDE_NAV = 13;
    public static final int TYPE_ELE_RIDE_ROUTE_SELECT = 12;
    public static final int TYPE_FOOT_NAV = 3;
    public static final int TYPE_FOOT_ROUTE_SELECT = 5;
    public static final int TYPE_HEALTH_RIDE = 8;
    public static final int TYPE_HEALTH_RUN = 9;
    public static final int TYPE_HEALTH_RUN_RECOMMEND = 10;
    public static final int TYPE_RIDE_NAV = 6;
    public static final int TYPE_RIDE_ROUTE_SELECT = 7;
    public static final int TYPE_SHAREBIKE_END = 11;
    public String biz_flag;
    public int distance;
    public int end_time;
    public String order;
    public int start_time;
    public String tid;
    public int type;

    public static ReportRequest buildParam(int i) {
        return buildParam(i, (i == 1 || i == 5 || i == 7) ? (int) (System.currentTimeMillis() / 1000) : 0, 0, 0);
    }

    public static ReportRequest buildParam(int i, int i2, int i3, int i4) {
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.b = NetworkParam.getTaobaoID();
        reportRequest.c = String.valueOf(i);
        reportRequest.d = String.valueOf(i2);
        reportRequest.e = String.valueOf(i3);
        reportRequest.f = String.valueOf(i4);
        return reportRequest;
    }

    public static ReportRequest buildParam(int i, int i2, int i3, int i4, String str, String str2) {
        ReportRequest buildParam = buildParam(i, i2, i3, i4);
        buildParam.g = str;
        buildParam.i = str2;
        return buildParam;
    }
}
