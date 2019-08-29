package com.alipay.mobile.common.transportext.biz.util;

import com.alipay.mobile.common.info.AppInfo;
import com.alipay.mobile.common.info.DeviceInfo;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv;

public class AmnetAppInfo {
    private static final String ALIPAY_WALLET_NAME = "ALIPAY_WALLET";

    public static String getAppName() {
        return "ALIPAY_WALLET";
    }

    public static String getProductVersion() {
        return AppInfo.createInstance(ExtTransportEnv.getAppContext()).getmProductVersion();
    }

    public static String getProductId() {
        return AppInfo.createInstance(ExtTransportEnv.getAppContext()).getProductID();
    }

    public static String getUtdId() {
        return DeviceInfo.createInstance(ExtTransportEnv.getAppContext()).getmDid();
    }
}
