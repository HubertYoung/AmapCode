package com.uc.webview.export.internal.setup;

import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.location.sdk.fusion.LocationParams;
import com.uc.webview.export.cyclone.UCLogger;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.v;
import com.uc.webview.export.internal.uc.CoreClassPreLoader;
import com.uc.webview.export.internal.uc.startup.StartupTrace;

/* compiled from: ProGuard */
public final class v<RETURN_TYPE extends v<RETURN_TYPE, CALLBACK_TYPE>, CALLBACK_TYPE extends v<RETURN_TYPE, CALLBACK_TYPE>> extends UCSubSetupTask<RETURN_TYPE, CALLBACK_TYPE> {
    private static UCAsyncTask a;
    private static int b;

    private static synchronized UCAsyncTask a() {
        UCAsyncTask uCAsyncTask;
        synchronized (v.class) {
            if (a == null) {
                a = new y(Integer.valueOf(b)).onEvent(H5PageData.KEY_UC_START, new x()).onEvent(LocationParams.PARA_COMMON_DIE, new w());
            }
            uCAsyncTask = a;
        }
        return uCAsyncTask;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public synchronized RETURN_TYPE start() {
        StartupTrace.traceEvent("LoadClassTask.start");
        if (invokeO(10005, new Object[0]) == null) {
            Integer num = (Integer) this.mOptions.get(UCCore.OPTION_SETUP_THREAD_PRIORITY);
            UCLogger create = UCLogger.create("d", "LoadClassTask");
            if (create != null) {
                create.print("start: setup_priority=".concat(String.valueOf(num)), new Throwable[0]);
            }
            if (num != null) {
                b = num.intValue();
            }
            UCAsyncTask a2 = a();
            invoke(10001, a2);
            RETURN_TYPE return_type = (v) super.start();
            a2.start();
            return return_type;
        }
        return (v) super.start();
    }

    public final void run() {
        StartupTrace.a();
        StartupTrace.a();
        CoreClassPreLoader.updateLazy(this.mCL);
        StartupTrace.traceEventEnd("LoadClassTask.setUpEnv");
        CoreClassPreLoader.loadCoreClass(this.mUCM.mCoreClassLoader);
        StartupTrace.traceEventEnd("LoadClassTask.run");
    }
}
