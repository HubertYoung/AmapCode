package com.autonavi.minimap.route.train.net.param;

import android.support.annotation.NonNull;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.common.model.POI;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"x1", "x2", "y1", "y2", "date", "time"}, url = "ws/valueadded/train/tickets/?")
public class TrainRequestParamEntity implements ParamEntity {
    public String client_src = "0";
    public String date = "2016-9-6";
    public String pn1 = "北京西站";
    public String pn2 = "广州站";
    public String poiid1 = "B000A83M61";
    public String poiid2 = "B00140WEW0";
    public String req_num = "50";
    public String tickettype = "0";
    public String time = "19-51";
    public String traintype = "0";
    public String ver = "3";
    public String x1 = "116.321337";
    public String x2 = "113.257633";
    public String y1 = "39.894966";
    public String y2 = "23.148876";

    public TrainRequestParamEntity(POI poi, POI poi2, @NonNull String str, @NonNull String str2, @NonNull String str3, int i, String str4, String str5) {
        if (poi != null && poi.getPoint() != null && poi2 != null && poi2.getPoint() != null) {
            this.x1 = String.valueOf(poi.getPoint().getLongitude());
            this.y1 = String.valueOf(poi.getPoint().getLatitude());
            this.x2 = String.valueOf(poi2.getPoint().getLongitude());
            this.y2 = String.valueOf(poi2.getPoint().getLatitude());
            this.poiid1 = poi.getId();
            this.poiid2 = poi2.getId();
            this.pn1 = poi.getName();
            this.pn2 = poi2.getName();
            this.req_num = String.valueOf(i);
            this.date = str;
            this.time = str3;
            this.traintype = str4;
            this.tickettype = str5;
            this.ver = "3";
            this.client_src = str2;
        }
    }
}
