package com.alipay.android.phone.mobilecommon.multimedia.api;

import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class APMToolService extends ExternalService {
    public abstract String decodeToPath(String str);

    public abstract String encodeToLocalId(String str);
}
