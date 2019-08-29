package com.ali.user.mobile.accountbiz.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ali.user.mobile.accountbiz.extservice.SecurityInitService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecurityInitMsgReceiver extends BroadcastReceiver {
    private static final String TAG = "SecurityInitMsgReceiver";

    public void onReceive(final Context context, final Intent intent) {
        if (LauncherApplication.a() != null) {
            ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
            if ("com.alipay.security.init".equals(intent.getAction())) {
                newCachedThreadPool.execute(new Runnable() {
                    public void run() {
                        SecurityInitService securityInitService = AntExtServiceManager.getSecurityInitService(context);
                        if (securityInitService != null) {
                            securityInitService.securityInit(intent);
                        }
                    }
                });
                AliUserLog.c(TAG, String.format("SecurityInitMsgReceiver.securityInit(intent=%s)", new Object[]{intent.toString()}));
                return;
            }
            if ("com.alipay.security.login".equals(intent.getAction())) {
                SecurityInitService securityInitService = AntExtServiceManager.getSecurityInitService(context);
                if (securityInitService != null) {
                    securityInitService.updateWalletLoginAuth(null);
                }
            }
        }
    }
}
