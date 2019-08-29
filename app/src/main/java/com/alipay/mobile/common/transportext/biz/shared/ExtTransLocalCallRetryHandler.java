package com.alipay.mobile.common.transportext.biz.shared;

import android.text.TextUtils;
import com.alipay.mobile.common.amnet.ipcapi.mainproc.MainProcDataListenService;
import com.alipay.mobile.common.amnet.ipcapi.mainproc.MainProcGeneralListenService;
import com.alipay.mobile.common.ipc.api.LocalCallRetryHandler;
import com.alipay.mobile.common.ipc.api.ServiceBeanManager;
import com.alipay.mobile.common.ipc.api.aidl.IPCParameter;
import com.alipay.mobile.common.ipc.api.aidl.IPCResult;
import com.alipay.mobile.common.transport.ext.MainProcConfigListenService;
import com.alipay.mobile.common.transport.httpdns.ipc.MainProcReloadDnsService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.httpdns.MainProcReloadDnsServiceImpl;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.foreign.AcceptDataListenServiceImpl;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.foreign.GeneralEventListenServiceImpl;
import com.alipay.mobile.common.transportext.biz.shared.config.MainProcConfigListenServiceImpl;

public class ExtTransLocalCallRetryHandler implements LocalCallRetryHandler {
    private static final String TAG = "IPC_ExtTransLocalCallRetryHandler";

    public boolean retryLocalCall(IPCParameter ipcParameter, IPCResult ipcResult, ServiceBeanManager serviceBeanManager, int retryTimes) {
        if (ipcResult.resultCode != 100) {
            LogCatUtil.warn((String) TAG, (String) "Only support SERVICE_NOT_FOUND_CODE retry!");
            return false;
        } else if (TextUtils.isEmpty(ipcParameter.className)) {
            LogCatUtil.warn((String) TAG, (String) "className is null!");
            return false;
        } else if (serviceBeanManager.getServiceBean(ipcParameter.className) != null) {
            LogCatUtil.info(TAG, "serviceBean is not null.  return true;");
            return true;
        } else if (MainProcDataListenService.class.getName().equals(ipcParameter.className)) {
            serviceBeanManager.register(MainProcDataListenService.class.getName(), AcceptDataListenServiceImpl.getInstance());
            LogCatUtil.info(TAG, "register " + ipcParameter.className);
            return true;
        } else if (MainProcGeneralListenService.class.getName().equals(ipcParameter.className)) {
            serviceBeanManager.register(MainProcGeneralListenService.class.getName(), GeneralEventListenServiceImpl.getInstance());
            LogCatUtil.info(TAG, "register " + ipcParameter.className);
            return true;
        } else if (MainProcConfigListenService.class.getName().equals(ipcParameter.className)) {
            serviceBeanManager.register(MainProcConfigListenService.class.getName(), MainProcConfigListenServiceImpl.getInstance());
            LogCatUtil.info(TAG, "register " + ipcParameter.className);
            return true;
        } else if (!MainProcReloadDnsService.class.getName().equals(ipcParameter.className)) {
            return false;
        } else {
            serviceBeanManager.register(MainProcReloadDnsService.class.getName(), new MainProcReloadDnsServiceImpl());
            LogCatUtil.info(TAG, "register " + ipcParameter.className);
            return true;
        }
    }
}
