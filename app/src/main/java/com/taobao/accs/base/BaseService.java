package com.taobao.accs.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.taobao.accs.common.Constants;
import com.taobao.accs.update.ACCSClassLoader;
import com.taobao.accs.utl.ALog;

public class BaseService extends Service {
    private static final String TAG = "BaseService";
    IBaseService mBaseService = null;

    public void onCreate() {
        Object newInstance;
        super.onCreate();
        try {
            this.mBaseService = (IBaseService) ACCSClassLoader.getInstance().getClassLoader(getApplicationContext()).loadClass(Constants.SERVICE_IMPL_NAME).getDeclaredConstructor(new Class[]{Service.class}).newInstance(new Object[]{this});
            if (this.mBaseService == null) {
                newInstance = Class.forName(Constants.SERVICE_IMPL_NAME).getDeclaredConstructor(new Class[]{Service.class}).newInstance(new Object[]{this});
                this.mBaseService = (IBaseService) newInstance;
            }
        } catch (Throwable unused) {
        }
        ALog.d(TAG, "onCreate", new Object[0]);
        if (this.mBaseService != null) {
            this.mBaseService.onCreate();
            return;
        } else {
            ALog.e(TAG, "onCreate cann't start ServiceImpl!", new Object[0]);
            return;
        }
        throw th;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (this.mBaseService != null) {
            return this.mBaseService.onStartCommand(intent, i, i2);
        }
        ALog.e(TAG, "onStartCommand mBaseService null", new Object[0]);
        return 2;
    }

    public IBinder onBind(Intent intent) {
        ALog.d(TAG, "onBind", "intent", intent);
        return this.mBaseService.onBind(intent);
    }

    public boolean onUnbind(Intent intent) {
        return this.mBaseService.onUnbind(intent);
    }

    public void onDestroy() {
        if (this.mBaseService != null) {
            this.mBaseService.onDestroy();
            this.mBaseService = null;
        }
        super.onDestroy();
    }
}
