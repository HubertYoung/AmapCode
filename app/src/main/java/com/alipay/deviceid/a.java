package com.alipay.deviceid;

import com.alipay.deviceid.module.senative.DeviceIdUtil;

final class a implements Runnable {
    final /* synthetic */ DeviceTokenClient a;

    a(DeviceTokenClient deviceTokenClient) {
        this.a = deviceTokenClient;
    }

    public final void run() {
        DeviceIdUtil.getInstance(this.a.context).initialize();
    }
}
