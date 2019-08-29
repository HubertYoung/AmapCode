package com.alipay.mobile.common.transportext.biz.appevent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.alipay.mobile.common.amnet.api.AmnetEnvHelper;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;

public class AmnetScreenOnOffReceiver {
    private static final String LOGTAG = "amnet_AmnetScreenOnOffReceiver";
    private static SecreenBroadcastReceiver broadcastReceiver = null;

    class SecreenBroadcastReceiver extends BroadcastReceiver {
        SecreenBroadcastReceiver() {
        }

        public void register() {
            Context context = AmnetEnvHelper.getAppContext();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            context.registerReceiver(this, intentFilter);
        }

        public void unregister() {
            AmnetEnvHelper.getAppContext().unregisterReceiver(this);
        }

        public void onReceive(Context context, final Intent intent) {
            NetworkAsyncTaskExecutor.executeHighSerial(new Runnable() {
                public void run() {
                    LogCatUtil.info(AmnetScreenOnOffReceiver.LOGTAG, "onReceive: [ AmnetScreenOnOffReceiver ] [ Action=" + intent.getAction() + " ]");
                    String action = intent.getAction();
                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
                        AppEventManager.getListener().onSeceenOffEvent();
                    } else if ("android.intent.action.SCREEN_ON".equals(action)) {
                        AppEventManager.getListener().onSeceenOnEvent();
                    }
                }
            });
        }
    }

    public static void start() {
        LogCatUtil.info(LOGTAG, "onCreate: [ AmnetScreenOnOffReceiver ] ");
        getSecreenBroadcastReceiver().register();
    }

    public static void stop() {
        LogCatUtil.info(LOGTAG, "stop: [ AmnetScreenOnOffReceiver ]  ");
        if (broadcastReceiver != null) {
            broadcastReceiver.unregister();
        }
    }

    private static SecreenBroadcastReceiver getSecreenBroadcastReceiver() {
        if (broadcastReceiver != null) {
            return broadcastReceiver;
        }
        synchronized (AmnetScreenOnOffReceiver.class) {
            if (broadcastReceiver != null) {
                SecreenBroadcastReceiver secreenBroadcastReceiver = broadcastReceiver;
                return secreenBroadcastReceiver;
            }
            broadcastReceiver = new SecreenBroadcastReceiver();
            return broadcastReceiver;
        }
    }
}
