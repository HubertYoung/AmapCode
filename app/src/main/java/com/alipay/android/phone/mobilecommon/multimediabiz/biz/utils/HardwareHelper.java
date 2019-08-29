package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import com.alipay.android.phone.mobilecommon.multimedia.utils.MMDeviceAssistant;
import com.alipay.android.phone.mobilecommon.multimedia.utils.MMDeviceAssistant.Release;
import com.alipay.android.phone.mobilecommon.multimedia.utils.MMDeviceAssistant.Request;

public class HardwareHelper {
    private static final HardwareHelper a = new HardwareHelper();
    private static final Logger b = Logger.getLogger((String) "HardwareHelper");
    private MMDeviceAssistant c = MMDeviceAssistant.get();

    private HardwareHelper() {
    }

    public static HardwareHelper get() {
        return a;
    }

    public boolean requestMic() {
        boolean ret = a(1);
        b.d("requestMic ret: " + ret, new Object[0]);
        return ret;
    }

    public boolean requestVideo() {
        boolean ret = a(3);
        b.d("requestVideo ret: " + ret, new Object[0]);
        return ret;
    }

    private boolean a(int device) {
        return this.c.requestDevice(new Request(device, "multimedia")) == 0;
    }

    public void releaseMic() {
        this.c.releaseDevice(new Release(1, "multimedia"));
        b.d("releaseMic", new Object[0]);
    }

    public void releaseVideo() {
        this.c.releaseDevice(new Release(3, "multimedia"));
        b.d("releaseVideo", new Object[0]);
    }
}
