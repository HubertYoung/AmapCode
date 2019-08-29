package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.netdet;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class NetDetect {
    /* access modifiers changed from: private */
    public static native int ping(String str);

    static {
        AppUtils.loadLibrary("netdetect");
        Logger.D("NetDetect", "loadLibrary finish", new Object[0]);
    }

    public static void nativePing(final String target) {
        if (!TextUtils.isEmpty(target)) {
            TaskScheduleManager.get().localPingSingleExecutor().submit(new Runnable() {
                public final void run() {
                    NetDetect.ping(target);
                }
            });
        }
    }
}
