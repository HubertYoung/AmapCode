package com.ali.auth.third.core.device;

import android.content.Context;
import com.ali.auth.third.core.trace.SDKLogger;

final class a implements Runnable {
    final /* synthetic */ Context a;

    a(Context context) {
        this.a = context;
    }

    public final void run() {
        try {
            DeviceInfo.deviceId = this.a.getSharedPreferences("auth_sdk_device", 0).getString("deviceId", "");
            StringBuilder sb = new StringBuilder("DeviceInfo.deviceId = ");
            sb.append(DeviceInfo.deviceId);
            SDKLogger.e("", sb.toString());
        } catch (Throwable unused) {
        }
    }
}
