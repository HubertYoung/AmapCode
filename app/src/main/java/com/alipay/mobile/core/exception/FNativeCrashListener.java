package com.alipay.mobile.core.exception;

import android.app.Activity;
import android.text.TextUtils;
import com.alipay.mobile.core.impl.AppExitHelper;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.WeakReference;

public class FNativeCrashListener {
    private final String a = "FNativeCrashListener";
    private MicroApplicationContext b;
    private AppExitHelper c;

    public void setMicroApplicationContext(MicroApplicationContext mac) {
        this.b = mac;
    }

    public void setAppExitHelper(AppExitHelper aeh) {
        this.c = aeh;
    }

    public Object onReportCrash(String bizKey, String crashInfo, String filePath, String callStack, boolean isReturnJVM) {
        if (isReturnJVM && !TextUtils.isEmpty(crashInfo)) {
            try {
                TraceLogger.i((String) "FNativeCrashListener", (String) "Framework FNativeCrashListener: return to java.\r\n");
                if (this.b != null) {
                    this.b.clearState();
                    this.b.clearTopApps();
                    WeakReference weakReference = this.b.getTopActivity();
                    if (this.c != null) {
                        if (weakReference != null) {
                            this.c.finishAllActivities((Activity) weakReference.get(), null);
                        } else {
                            this.c.finishAllActivities(null, null);
                        }
                    }
                }
            } catch (Throwable tr) {
                TraceLogger.w((String) "FNativeCrashListener", tr);
            }
        }
        return null;
    }
}
