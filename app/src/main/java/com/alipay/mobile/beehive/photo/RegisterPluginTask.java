package com.alipay.mobile.beehive.photo;

import com.alipay.mobile.beehive.util.H5ConfigUtil;

public class RegisterPluginTask implements Runnable {
    public void run() {
        H5ConfigUtil.addConfig("android-phone-wallet-beehive", "com.alipay.mobile.beehive.plugin.H5PhotoPlugin", "page", "imageViewer|imageSelect|mediaFileBrowser");
    }
}
