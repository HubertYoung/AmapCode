package com.alipay.mobile.beehive.compositeui.app;

import com.alipay.mobile.beehive.util.H5ConfigUtil;

public class InitTask implements Runnable {
    public void run() {
        H5ConfigUtil.addConfig("android-phone-wallet-beehive", "com.alipay.mobile.beehive.compositeui.app.H5BeehiveViewPlugin", "page", "beehiveOptionsPicker|beehiveLimitedHoursPicker|beehiveMultilevelSelect");
    }
}
