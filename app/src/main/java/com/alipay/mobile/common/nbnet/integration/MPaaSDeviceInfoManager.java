package com.alipay.mobile.common.nbnet.integration;

import com.alipay.mobile.common.info.AppInfo;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.platform.DefaultDeviceInfoManager;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.framework.app.ui.ActivityHelper;

public class MPaaSDeviceInfoManager extends DefaultDeviceInfoManager {
    public final String a() {
        return AppInfo.createInstance(NBNetEnvUtils.a()).getProductName();
    }

    public final boolean b() {
        try {
            boolean isMainProc = MiscUtils.isMainProcessRuning(NBNetEnvUtils.a());
            boolean isAlipayClient = MiscUtils.isInAlipayClient(NBNetEnvUtils.a());
            boolean background = ActivityHelper.isBackgroundRunning();
            if (isMainProc && isAlipayClient && background) {
                NBNetLogCat.a((String) "MPaaSDeviceInfoManager", (String) "Wallet background running. return.");
                return true;
            }
        } catch (Throwable e) {
            NBNetLogCat.d("MPaaSDeviceInfoManager", "ActivityHelper#isBackgroundRunning error, info: " + e.toString());
        }
        if (!MiscUtils.isAtFrontDesk(NBNetEnvUtils.a())) {
            NBNetLogCat.a((String) "MPaaSDeviceInfoManager", (String) "Wallet not at front desk. return.");
            return true;
        }
        NBNetLogCat.a((String) "MPaaSDeviceInfoManager", (String) "Wallet at front desk.");
        return false;
    }
}
