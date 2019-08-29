package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.bicycle.BicycleRequestHolder;
import com.autonavi.minimap.bicycle.param.ShareBikeRideStateRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.RideStateResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class RideStateRequest extends BaseRequest {
    public RideStateRequest(ShareBikeRideStateRequest shareBikeRideStateRequest, a aVar) {
        super(shareBikeRideStateRequest, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return RideStateResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        BicycleRequestHolder.getInstance().sendShareBikeRideState((ShareBikeRideStateRequest) this.mParam, aosResponseCallback);
    }
}
