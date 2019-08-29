package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.model.AppRes;

public interface H5AppBizRpcProvider {
    AppRes app(AppReq appReq);

    AppRes handlerPKgJson(String str);

    String rpcCall(String str, AppReq appReq);
}
