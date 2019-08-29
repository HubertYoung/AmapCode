package com.alipay.apmobilesecuritysdk.rpc.gen;

import com.alipay.inside.mobile.framework.service.annotation.OperationType;

public interface BugTrackMessageService {
    @OperationType("alipay.security.errorLog.collect")
    String logCollect(String str);
}
