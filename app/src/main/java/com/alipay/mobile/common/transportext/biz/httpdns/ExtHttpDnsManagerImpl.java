package com.alipay.mobile.common.transportext.biz.httpdns;

import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.transport.ext.ExtHttpDnsManager;
import com.alipay.mobile.common.transport.httpdns.ipc.UpdateDnsService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;

public class ExtHttpDnsManagerImpl implements ExtHttpDnsManager {
    public void ayncNotifyUpdateDnsInfo(final boolean firstUpdate) {
        NetworkAsyncTaskExecutor.executeLowPri(new Runnable() {
            public void run() {
                try {
                    ((UpdateDnsService) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(UpdateDnsService.class)).notifyUpdateDns(firstUpdate);
                } catch (Exception e) {
                    LogCatUtil.error((String) "ExtHttpDnsManager", (Throwable) e);
                }
            }
        });
    }
}
