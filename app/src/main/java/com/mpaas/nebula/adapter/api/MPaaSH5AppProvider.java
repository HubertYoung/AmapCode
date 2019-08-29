package com.mpaas.nebula.adapter.api;

import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.baseprovider.H5BaseAppProvider;

public class MPaaSH5AppProvider extends H5BaseAppProvider {
    public AppReq setReq(AppReq appReq) {
        appReq.bundleid = "100";
        return appReq;
    }
}
