package com.alipay.mobilecodec.service.pai;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.SignCheck;
import com.alipay.mobilecodec.service.pai.req.RouteCommandReq;
import com.alipay.mobilecodec.service.pai.res.RouteRes;

public interface PaipaiFacade {
    @OperationType("alipay.mobilecodec.route")
    @SignCheck
    RouteRes route(RouteCommandReq routeCommandReq);

    @OperationType("alipay.mobilecodec.routeSDK")
    @SignCheck
    RouteRes routeSDK(RouteCommandReq routeCommandReq);
}
