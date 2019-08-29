package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.bicycle.BicycleRequestHolder;
import com.autonavi.minimap.bicycle.param.ShareBikeUserInfoRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.UserInfoResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class UserInfoRequest extends BaseRequest {
    public UserInfoRequest(ShareBikeUserInfoRequest shareBikeUserInfoRequest, a aVar) {
        super(shareBikeUserInfoRequest, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return UserInfoResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        BicycleRequestHolder.getInstance().sendShareBikeUserInfo((ShareBikeUserInfoRequest) this.mParam, aosResponseCallback);
    }
}
