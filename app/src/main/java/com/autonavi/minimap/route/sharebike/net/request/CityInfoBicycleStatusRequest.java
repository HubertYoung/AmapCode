package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.bicycle.BicycleRequestHolder;
import com.autonavi.minimap.bicycle.param.BicycleStatusCmd1Param;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.CityInfoBicycleStatusResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class CityInfoBicycleStatusRequest extends BaseRequest {
    public CityInfoBicycleStatusRequest(BicycleStatusCmd1Param bicycleStatusCmd1Param, a aVar) {
        super(bicycleStatusCmd1Param, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return CityInfoBicycleStatusResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        BicycleRequestHolder.getInstance().sendBicycleStatus((BicycleStatusCmd1Param) this.mParam, aosResponseCallback);
    }
}
