package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.BikeBalancePayRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BalancePayResponser;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class BalancePayRequest extends BaseRequest {
    public BalancePayRequest(BikeBalancePayRequest bikeBalancePayRequest, a aVar) {
        super(bikeBalancePayRequest, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return BalancePayResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        OrderRequestHolder.getInstance().sendBikeBalancePay((BikeBalancePayRequest) this.mParam, aosResponseCallback);
    }
}
