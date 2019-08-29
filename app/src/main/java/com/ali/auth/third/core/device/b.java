package com.ali.auth.third.core.device;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;

final class b implements Runnable {
    final /* synthetic */ Context a;

    b(Context context) {
        this.a = context;
    }

    public final void run() {
        try {
            Editor edit = this.a.getSharedPreferences("auth_sdk_device", 0).edit();
            edit.putString("deviceId", DeviceInfo.deviceId);
            if (VERSION.SDK_INT >= 9) {
                edit.apply();
            } else {
                edit.commit();
            }
        } catch (Throwable unused) {
        }
    }
}
