package com.alipay.mobilecodec.service.coderoute;

import com.alipay.mobile.framework.service.annotation.OperationType;
import com.alipay.mobile.framework.service.annotation.SignCheck;

public interface CodeRouteRpcService {
    @OperationType("alipay.mobilecodec.codeRoute.pb.route")
    @SignCheck
    RoutePbRep route(RouteCommandPbReq routeCommandPbReq);
}
