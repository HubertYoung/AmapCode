package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.BikeEndBillingRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.EndBillResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class EndBillingRequest extends BaseRequest {
    public EndBillingRequest(BikeEndBillingRequest bikeEndBillingRequest, a aVar) {
        super(bikeEndBillingRequest, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return EndBillResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        OrderRequestHolder.getInstance().sendBikeEndBilling((BikeEndBillingRequest) this.mParam, aosResponseCallback);
    }
}
