package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist;

import android.content.Context;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.multimedia.adjuster.api.APMAdjuster;
import com.alipay.multimedia.adjuster.api.APMSandboxProcessor;
import com.alipay.multimedia.adjuster.api.data.IConfig;
import com.alipay.multimedia.adjuster.api.data.ITraceId;

public class AdjusterHelper {
    public static void init() {
        setApplicationContext(AppUtils.getApplicationContext());
        registerConfig();
        registerTraceIdBuilder();
    }

    public static void setApplicationContext(Context context) {
        APMSandboxProcessor.setApplicationContext(context);
    }

    public static void registerConfig() {
        APMAdjuster.registerConfig(new IConfig() {
            public final String getConfig(String key) {
                return ConfigManager.getInstance().getStringValue(key, "");
            }
        });
    }

    public static void registerTraceIdBuilder() {
        APMAdjuster.registerITraceId(new ITraceId() {
            public final String genTraceId() {
                return "";
            }
        });
    }

    public static boolean checkFileExist(String path) {
        return APMSandboxProcessor.checkFileExist(path);
    }
}
