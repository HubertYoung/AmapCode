package com.autonavi.minimap.route.train.net;

import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = AosURLBuilder.class, host = "aos_sns_url", sign = {"train_num"}, url = "ws/boss/order/train/submit_order/?")
public class TrainTicketPurchasingParam implements ParamEntity {
    public String end_station = null;
    public String start_station = null;
    public String start_time = null;
    public String train_num = null;
}
