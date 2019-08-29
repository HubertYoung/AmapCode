package com.autonavi.minimap.route.coach.net.param;

import android.support.annotation.NonNull;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.autonavi.common.model.POI;

@Path(builder = AosURLBuilder.class, host = "aos_url", sign = {"x1", "x2", "y1", "y2", "date", "time", "coachAgentID"}, url = "ws/valueadded/coach/tickets/?")
public class CoachRequestEntity implements ParamEntity {
    public String coachAgentID = "1";
    public String date = "2017-2-26";
    public String pn1 = "磐石市";
    public String pn2 = "长春市";
    public String poiid1 = "";
    public String poiid2 = "";
    public int req_num = 10;
    public String time = "00-51";
    public String ver = "3";
    public double x1 = 121.060427d;
    public double x2 = 123.324889d;
    public double y1 = 43.946285d;
    public double y2 = 42.828006d;

    public CoachRequestEntity(@NonNull POI poi, @NonNull POI poi2, @NonNull String str, @NonNull String str2, @NonNull String str3, int i) {
        this.x1 = poi.getPoint().getLongitude();
        this.y1 = poi.getPoint().getLatitude();
        this.poiid1 = poi.getPid();
        this.pn1 = poi.getName();
        this.x2 = poi2.getPoint().getLongitude();
        this.y2 = poi2.getPoint().getLatitude();
        this.poiid2 = poi2.getPid();
        this.pn2 = poi2.getName();
        this.date = str;
        this.time = str3;
        this.req_num = i;
        this.coachAgentID = str2;
    }
}
