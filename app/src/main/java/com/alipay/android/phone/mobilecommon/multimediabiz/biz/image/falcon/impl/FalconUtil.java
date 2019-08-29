package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.streammedia.encode.utils.OMXConfig;

public class FalconUtil {
    private FalconUtil() {
    }

    public static boolean omxSwitch(OMXConfig config) {
        int ret = ConfigManager.getInstance().getCommonConfigItem().videoConf.omx;
        if (config == null || !config.support || ret != 1) {
            return false;
        }
        return true;
    }
}
