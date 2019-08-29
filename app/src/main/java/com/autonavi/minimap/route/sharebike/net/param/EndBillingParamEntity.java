package com.autonavi.minimap.route.sharebike.net.param;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"cpSource", "orderId"}, url = "ws/boss/order/bike/end_billing/?")
public class EndBillingParamEntity implements ParamEntity {
    public String cpSource;
    public String latitude;
    public String longitude;
    public String orderId;

    public EndBillingParamEntity(String str, String str2, String str3, String str4) {
        this.cpSource = str;
        this.orderId = str2;
        this.longitude = str3;
        this.latitude = str4;
    }
}
