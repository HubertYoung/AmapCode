package com.alipay.mobile.beehive.audio.utils;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public class BundleLogger {
    private String BUNDLE_PREFIX = "BeehiveAudio-%s";
    private String TAG;

    public static BundleLogger getLogger(Class<?> clazz) {
        return new BundleLogger(clazz);
    }

    private BundleLogger(Class<?> clazz) {
        this.TAG = String.format(this.BUNDLE_PREFIX, new Object[]{clazz.getSimpleName()});
    }

    public void i(String msg) {
        if (msg != null) {
            LoggerFactory.getTraceLogger().info(this.TAG, msg);
        }
    }

    public void e(String msg) {
        if (msg != null) {
            LoggerFactory.getTraceLogger().error(this.TAG, msg);
        }
    }

    public void e(Throwable tr) {
        e(tr == null ? "" : tr.getMessage());
    }

    public void d(String msg) {
        if (msg != null) {
            LoggerFactory.getTraceLogger().debug(this.TAG, msg);
        }
    }

    public void w(String msg) {
        if (msg != null) {
            LoggerFactory.getTraceLogger().warn(this.TAG, msg);
        }
    }
}
