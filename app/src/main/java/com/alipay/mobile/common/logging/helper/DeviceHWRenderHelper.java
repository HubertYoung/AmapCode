package com.alipay.mobile.common.logging.helper;

import android.content.Context;
import com.alipay.mobile.common.logging.api.DeviceHWInfo;

public class DeviceHWRenderHelper {
    public static int a() {
        return DeviceHWInfo.getNumberOfCPUCores();
    }

    public static int b() {
        int khz = DeviceHWInfo.getCPUMaxFreqKHz();
        if (khz == -1 || khz <= 0) {
            return -1;
        }
        return khz / 1000;
    }

    public static long a(Context context) {
        long bytes = DeviceHWInfo.getTotalMemory(context);
        if (bytes == -1 || bytes <= 0) {
            return -1;
        }
        return bytes / 1048576;
    }
}
