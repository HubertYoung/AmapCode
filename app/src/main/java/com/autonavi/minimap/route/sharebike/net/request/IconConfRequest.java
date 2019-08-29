package com.autonavi.minimap.route.sharebike.net.request;

import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.bicycle.BicycleRequestHolder;
import com.autonavi.minimap.bicycle.param.ShareBikeIConconfRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.parser.IconConfResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;

public class IconConfRequest extends BaseRequest {
    public IconConfRequest(ShareBikeIConconfRequest shareBikeIConconfRequest, a aVar) {
        super(shareBikeIConconfRequest, aVar);
    }

    public Class<? extends BaseResponser> getResponsorClass() {
        return IconConfResponser.class;
    }

    public void send(AosResponseCallback<AosByteResponse> aosResponseCallback) {
        BicycleRequestHolder.getInstance().sendShareBikeIConconf((ShareBikeIConconfRequest) this.mParam, aosResponseCallback);
    }
}
