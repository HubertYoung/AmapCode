package com.alipay.mobile.beehive.plugins.utils;

import com.alipay.mobile.common.logging.api.LoggerFactory;

public class H5PLogger {
    private String BUNDLE_PREFIX = "BeehivePlugin-%s";
    private String TAG;

    public static H5PLogger getLogger(Class<?> clazz) {
        return new H5PLogger(clazz);
    }

    public static H5PLogger getLogger(String subTag) {
        return new H5PLogger(subTag);
    }

    public H5PLogger(String subTag) {
        this.TAG = String.format(this.BUNDLE_PREFIX, new Object[]{subTag});
    }

    private H5PLogger(Class<?> clazz) {
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
