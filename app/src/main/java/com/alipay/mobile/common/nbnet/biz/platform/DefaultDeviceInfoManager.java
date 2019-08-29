package com.alipay.mobile.common.nbnet.biz.platform;

import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetEnvUtils;
import com.alipay.mobile.common.transport.utils.MiscUtils;

public class DefaultDeviceInfoManager implements DeviceInfoManager {
    public String a() {
        return "DefaultProdName";
    }

    public boolean b() {
        if (MiscUtils.isAtFrontDesk(NBNetEnvUtils.a())) {
            return false;
        }
        NBNetLogCat.a((String) "DefaultDeviceInfoManager", (String) "Wallet not at front desk. return.");
        return true;
    }
}
