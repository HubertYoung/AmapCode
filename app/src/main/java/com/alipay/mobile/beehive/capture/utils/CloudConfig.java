package com.alipay.mobile.beehive.capture.utils;

import android.text.TextUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.beehive.util.MicroServiceUtil;

public class CloudConfig {
    private static final String KEY_DISABLE_EDGE_DETECTOR = "beehive_disable_edge_detector";
    private static boolean isConfigInit = false;
    private static boolean mDisableEdgeDetector = false;

    private static void updateConfig() {
        if (!isConfigInit) {
            ConfigService s = (ConfigService) MicroServiceUtil.getMicroService(ConfigService.class);
            if (s != null) {
                mDisableEdgeDetector = TextUtils.equals("true", s.getConfig(KEY_DISABLE_EDGE_DETECTOR));
                isConfigInit = true;
            }
        }
    }

    public static boolean isDisableEdgeDetectorByCloudConfig() {
        updateConfig();
        return mDisableEdgeDetector;
    }
}
