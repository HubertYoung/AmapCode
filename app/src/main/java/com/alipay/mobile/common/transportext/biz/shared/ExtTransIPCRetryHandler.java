package com.alipay.mobile.common.transportext.biz.shared;

import com.alipay.mobile.common.ipc.api.IPCException;
import com.alipay.mobile.common.ipc.api.IPCRetryHandler;
import com.alipay.mobile.common.ipc.api.push.BindPushServiceManager;
import com.alipay.mobile.common.ipc.api.push.BindPushServiceManager.BindPushServiceFactory;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.mmtp.BindEventListenerManger;
import java.lang.reflect.Method;

public class ExtTransIPCRetryHandler implements IPCRetryHandler {
    private static final String TAG = "IPC_ExtTransIPCRetryHandler";

    public boolean retryIPCCall(Method method, Object[] args, IPCException ipcException, int retryTimes) {
        LogCatUtil.info(TAG, "retryIPCCall. method=[" + method.getName() + "], ipcException=" + ipcException.toString() + ", retryTimes:" + retryTimes);
        if (retryTimes > 5) {
            LogCatUtil.info(TAG, "retryIPCCall. retryTimes > 5 , no retry");
            return false;
        } else if (ipcException == null) {
            LogCatUtil.info(TAG, "retryIPCCall. ipcException == null , no retry");
            return false;
        } else if (ipcException.errorCode == 201) {
            LogCatUtil.info(TAG, "retryIPCCall. errorCode=DEAD_OBJECT_ERROR,   go retry");
            BindPushServiceManager bindPushServiceManager = BindPushServiceFactory.getInstance();
            bindPushServiceManager.unbindService();
            BindEventListenerManger.getInstance().waitToUnBinded();
            bindPushServiceManager.bindService();
            BindEventListenerManger.getInstance().waitToBinded();
            return true;
        } else if (ipcException.errorCode != 2) {
            return false;
        } else {
            LogCatUtil.info(TAG, "retryIPCCall. errorCode=IPC_NO_BINDER_CODE,   go retry");
            BindPushServiceFactory.getInstance().bindService();
            BindEventListenerManger.getInstance().waitToBinded();
            return true;
        }
    }
}
