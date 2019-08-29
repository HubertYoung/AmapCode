package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.bicycle.BicycleRequestHolder;
import com.autonavi.minimap.bicycle.param.ShareBikeUserTagRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.UserTagResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class UserTagRequest extends BaseRequest {
    public UserTagRequest(ShareBikeUserTagRequest shareBikeUserTagRequest, a aVar) {
        super(shareBikeUserTagRequest, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return UserTagResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        BicycleRequestHolder.getInstance().sendShareBikeUserTag((ShareBikeUserTagRequest) this.mParam, aosResponseCallback);
    }
}
