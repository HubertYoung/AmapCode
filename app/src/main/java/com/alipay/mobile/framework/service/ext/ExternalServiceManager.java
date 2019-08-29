package com.alipay.mobile.framework.service.ext;

import com.alipay.mobile.framework.service.CommonService;
import com.alipay.mobile.framework.service.ServiceDescription;

public abstract class ExternalServiceManager extends CommonService {
    public abstract boolean createExternalService(ServiceDescription serviceDescription);

    public abstract ExternalService getExternalService(String str);

    public abstract void registerExternalService(ServiceDescription serviceDescription);

    public abstract void registerExternalServiceOnly(ServiceDescription serviceDescription);
}
