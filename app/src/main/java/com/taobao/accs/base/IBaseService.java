package com.taobao.accs.base;

import android.content.Intent;
import android.os.IBinder;

public interface IBaseService {
    IBinder onBind(Intent intent);

    void onCreate();

    void onDestroy();

    int onStartCommand(Intent intent, int i, int i2);

    boolean onUnbind(Intent intent);
}
