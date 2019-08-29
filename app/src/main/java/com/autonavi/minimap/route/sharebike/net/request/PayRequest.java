package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.BikePayRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.PayResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class PayRequest extends BaseRequest {
    public PayRequest(BikePayRequest bikePayRequest, a aVar) {
        super(bikePayRequest, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return PayResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        OrderRequestHolder.getInstance().sendBikePay((BikePayRequest) this.mParam, aosResponseCallback);
    }
}
