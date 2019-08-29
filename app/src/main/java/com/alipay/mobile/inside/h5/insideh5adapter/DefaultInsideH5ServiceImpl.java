package com.alipay.mobile.inside.h5.insideh5adapter;

import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.mobile.h5container.service.H5Service;

public class DefaultInsideH5ServiceImpl implements IInsideH5Service {
    public H5Service getH5Service() {
        H5Service h5Service = null;
        try {
            LoggerFactory.f().a((String) "DefaultInsideH5ServiceImpl", (String) "getH5Service begin");
            H5Service h5Service2 = (H5Service) Class.forName("com.alipay.android.phone.inside.h5container.InsideH5Helper").getMethod("getH5Service", null).invoke(null, null);
            try {
                LoggerFactory.f().a((String) "DefaultInsideH5ServiceImpl", (String) "getH5Service end");
                return h5Service2;
            } catch (Throwable th) {
                H5Service h5Service3 = h5Service2;
                th = th;
                h5Service = h5Service3;
                LoggerFactory.f().a((String) "DefaultInsideH5ServiceImpl getH5Service error", th);
                return h5Service;
            }
        } catch (Throwable th2) {
            th = th2;
            LoggerFactory.f().a((String) "DefaultInsideH5ServiceImpl getH5Service error", th);
            return h5Service;
        }
    }
}
