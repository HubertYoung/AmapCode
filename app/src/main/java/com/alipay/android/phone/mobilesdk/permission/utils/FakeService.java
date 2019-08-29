package com.alipay.android.phone.mobilesdk.permission.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class FakeService extends Service {
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
