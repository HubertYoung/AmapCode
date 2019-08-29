package com.alipay.mobile.framework.service.common;

import com.alipay.mobile.framework.service.CommonService;

public abstract class TimeService extends CommonService {
    public abstract long getServerTime();

    public abstract long getServerTime(boolean z);

    public abstract long getServerTimeMayOffline();
}
