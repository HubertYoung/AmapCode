package com.alipay.mobile.common.transportext.biz.mmtp;

import com.alipay.mobile.common.amnet.ipcapi.pushproc.OutEventNotifyService;
import com.alipay.mobile.common.ipc.api.IPCApiFactory;
import com.alipay.mobile.common.transport.utils.LogCatUtil;

public class MMTPSceneManager {
    public static void setScene(String scene, boolean pass) {
        try {
            ((OutEventNotifyService) IPCApiFactory.getSingletonIPCContextManager().getIpcCallManager().getIpcProxy(OutEventNotifyService.class)).notifySceneEvent(scene, pass);
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MMTPSceneManager", ex);
        }
    }
}
