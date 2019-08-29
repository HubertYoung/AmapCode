package com.alipay.deviceid.module.rpc.deviceFp;

import com.alipay.deviceid.module.rpc.mrpc.annotation.OperationType;

public interface BugTrackMessageService {
    @OperationType("alipay.security.errorLog.collect")
    String logCollect(String str);
}
