package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.bicycle.BicycleRequestHolder;
import com.autonavi.minimap.bicycle.param.BicycleStatusCmd2Param;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.BicycleStatusResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class BicycleStatusRequest extends BaseRequest {
    public BicycleStatusRequest(BicycleStatusCmd2Param bicycleStatusCmd2Param, a aVar) {
        super(bicycleStatusCmd2Param, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return BicycleStatusResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        BicycleRequestHolder.getInstance().sendBicycleStatus((BicycleStatusCmd2Param) this.mParam, aosResponseCallback);
    }
}
