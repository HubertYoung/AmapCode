package com.autonavi.minimap.route.train.net;

import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.amap.bundle.network.request.param.builder.SnsURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;

@Path(builder = SnsURLBuilder.class, host = "aos_sns_url", sign = {"tid"}, url = "ws/boss/order/train/orderlist/?")
public class TrainOrderParam extends ejb implements ParamEntity {
}
