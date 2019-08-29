package com.taobao.statistic.core;

import android.content.Context;
import com.alibaba.analytics.utils.PhoneInfoUtils;
import com.ut.device.UTDevice;

public class DeviceInfo {
    private static Device s_device;

    @Deprecated
    public static Device getDevice(Context context) {
        if (s_device != null) {
            return s_device;
        }
        Device device = new Device();
        device.setImei(PhoneInfoUtils.getImei(context));
        device.setImsi(PhoneInfoUtils.getImsi(context));
        device.setUdid(UTDevice.getUtdid(context));
        s_device = device;
        return device;
    }
}
