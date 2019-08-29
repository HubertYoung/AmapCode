package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.bicycle.BicycleRequestHolder;
import com.autonavi.minimap.bicycle.param.BicycleStatusCmd3Request;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.BicycleDetailResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class BicycleDetailRequest extends BaseRequest {
    public BicycleDetailRequest(BicycleStatusCmd3Request bicycleStatusCmd3Request, a aVar) {
        super(bicycleStatusCmd3Request, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return BicycleDetailResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        BicycleRequestHolder.getInstance().sendBicycleStatus((BicycleStatusCmd3Request) this.mParam, aosResponseCallback);
    }
}
