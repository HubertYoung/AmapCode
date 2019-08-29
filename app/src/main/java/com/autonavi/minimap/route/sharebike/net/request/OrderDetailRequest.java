package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.BikeOrderDetailRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.OrderDetailResonser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class OrderDetailRequest extends BaseRequest {
    public OrderDetailRequest(BikeOrderDetailRequest bikeOrderDetailRequest, a aVar) {
        super(bikeOrderDetailRequest, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return OrderDetailResonser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        OrderRequestHolder.getInstance().sendBikeOrderDetail((BikeOrderDetailRequest) this.mParam, aosResponseCallback);
    }
}
