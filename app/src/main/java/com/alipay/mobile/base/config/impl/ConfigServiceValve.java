package com.alipay.mobile.base.config.impl;

public class ConfigServiceValve implements Runnable {
    public void run() {
        ConfigMonitor.getInstance().reportBizRequest("ConfigArrivalCount1");
    }
}
