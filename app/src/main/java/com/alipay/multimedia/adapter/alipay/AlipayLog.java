package com.alipay.multimedia.adapter.alipay;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.multimedia.adapter.AdapterFactory.L;

public class AlipayLog implements L {
    public void v(String tag, String msg) {
        LoggerFactory.getTraceLogger().verbose(tag, msg);
    }

    public void d(String tag, String msg) {
        LoggerFactory.getTraceLogger().debug(tag, msg);
    }

    public void i(String tag, String msg) {
        LoggerFactory.getTraceLogger().info(tag, msg);
    }

    public void w(String tag, String msg) {
        LoggerFactory.getTraceLogger().warn(tag, msg);
    }

    public void e(String tag, String msg) {
        LoggerFactory.getTraceLogger().error(tag, msg);
    }

    public void e(String tag, String msg, Throwable t) {
        LoggerFactory.getTraceLogger().error(tag, msg, t);
    }
}
