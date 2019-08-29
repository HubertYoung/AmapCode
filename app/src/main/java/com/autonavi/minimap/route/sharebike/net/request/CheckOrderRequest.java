package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.BikeCheckOrderRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.CheckOrderResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class CheckOrderRequest extends BaseRequest {
    public CheckOrderRequest(BikeCheckOrderRequest bikeCheckOrderRequest, a aVar) {
        super(bikeCheckOrderRequest, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return CheckOrderResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        OrderRequestHolder.getInstance().sendBikeCheckOrder((BikeCheckOrderRequest) this.mParam, aosResponseCallback);
    }
}
