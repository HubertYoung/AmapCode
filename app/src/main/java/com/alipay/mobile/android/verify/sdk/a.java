package com.alipay.mobile.android.verify.sdk;

import com.alipay.mobile.android.verify.logger.Logger;
import com.autonavi.widget.ui.BalloonLayout;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: CrashHandler */
class a implements UncaughtExceptionHandler {
    private final UncaughtExceptionHandler a = Thread.getDefaultUncaughtExceptionHandler();

    public void a() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (th != null) {
            Logger.t("CrashHandler").e(th, "uncaught exception", new Object[0]);
        }
        try {
            Thread.sleep(BalloonLayout.DEFAULT_DISPLAY_DURATION);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (this.a != null) {
            this.a.uncaughtException(thread, th);
        }
    }
}
