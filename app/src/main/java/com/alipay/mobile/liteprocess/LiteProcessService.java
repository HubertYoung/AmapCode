package com.alipay.mobile.liteprocess;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.CookieManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.liteprocess.ipc.IClientService.Stub;
import com.alipay.mobile.liteprocess.ipc.IpcService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

public class LiteProcessService extends IpcService {
    static Class[] a = {LiteProcessService1.class, LiteProcessService2.class, LiteProcessService3.class, LiteProcessService4.class, LiteProcessService5.class};
    private static boolean b = false;
    private final Stub c = new Stub() {
        public void destoryClient() {
            LiteProcessClientManager.c();
        }

        public void moveTaskToFront(int runningTaskInfoId, boolean enter, boolean fromForeground, Bundle params) {
            ActivityManager activityManager = (ActivityManager) Util.getContext().getSystemService(WidgetType.ACTIVITY);
            if (activityManager != null) {
                Util.moveTaskToFront(activityManager, (Activity) Util.getMicroAppContext().getTopActivity().get(), runningTaskInfoId, enter, fromForeground, params, false);
            }
        }

        public void notifyLogout() {
            CookieManager.getInstance().removeAllCookie();
        }
    };

    public class LiteProcessService1 extends LiteProcessService {
    }

    public class LiteProcessService2 extends LiteProcessService {
    }

    public class LiteProcessService3 extends LiteProcessService {
    }

    public class LiteProcessService4 extends LiteProcessService {
    }

    public class LiteProcessService5 extends LiteProcessService {
    }

    LiteProcessService() {
    }

    public void onCreate() {
        super.onCreate();
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " onCreate");
        Util.setContext(getApplicationContext());
        LiteProcessClientManager.getAsyncHandler().post(new Runnable() {
            public void run() {
                LiteProcessPipeline.litePipelineRun(LiteProcessService.this);
                LiteProcessService.this.a();
            }
        });
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " onBind");
        if (intent != null) {
            Util.b(intent.getStringExtra("UID"));
        }
        return this.c;
    }

    /* access modifiers changed from: private */
    public synchronized void a() {
        LoggerFactory.getTraceLogger().debug(Const.TAG, getClass().getName() + " preload " + b);
        if (!b) {
            LiteProcessClientManager.a();
            b = true;
            try {
                Log.i("mytest", "geth5service: " + LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName()));
            } catch (Throwable t) {
                Log.i("mytest", "register engine step error", t);
            }
            LiteProcessClientManager.d();
        }
        return;
    }
}
