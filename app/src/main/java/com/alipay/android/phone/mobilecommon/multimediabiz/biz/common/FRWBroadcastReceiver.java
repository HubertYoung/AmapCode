package com.alipay.android.phone.mobilecommon.multimediabiz.biz.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.SimpleConfigProvider;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.base.config.ConfigService.SyncReceiverListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FRWBroadcastReceiver extends BroadcastReceiver implements SyncReceiverListener {
    public static final String ACTION_CONFIG_CHANGED = "com.alipay.mobile.client.CONFIG_CHANGE";
    private static FRWBroadcastReceiver a;
    private static final AtomicBoolean b = new AtomicBoolean(false);
    private transient Handler c;

    public static void initOnce() {
        if (b.compareAndSet(false, true)) {
            if (a == null) {
                a = new FRWBroadcastReceiver();
            }
            a.b();
        }
    }

    private FRWBroadcastReceiver() {
        e();
    }

    private void b() {
        Logger.D("FRWBroadcast", "FRWBroadcastReceiver init", new Object[0]);
        LocalBroadcastManager.getInstance(AppUtils.getApplicationContext()).registerReceiver(this, new IntentFilter("com.alipay.mobile.client.CONFIG_CHANGE"));
        this.c.sendEmptyMessageDelayed(0, 1000);
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null && "com.alipay.mobile.client.CONFIG_CHANGE".equalsIgnoreCase(intent.getAction())) {
            c();
            registerSyncCallback(AppUtils.getConfigService());
        }
    }

    private void c() {
        this.c.removeMessages(2);
        this.c.removeMessages(1);
        this.c.sendEmptyMessageDelayed(2, 1000);
    }

    /* access modifiers changed from: protected */
    public void registerSyncCallback(ConfigService service) {
        if (service != null && !service.isRegistered(this)) {
            service.registerSyncReceiverListener(this);
        }
    }

    /* access modifiers changed from: private */
    public static void d() {
        ConfigManager.getInstance().updateConfig(false);
        SimpleConfigProvider.get().updateConfigCache();
        Logger.D("FRWBroadcast", "onConfigChanged: " + null + ", intent: " + null + ", action: " + null + ", finish", new Object[0]);
    }

    public void onSyncReceiver(String s, String s1) {
        c();
    }

    public List<String> getKeys() {
        List keys = new ArrayList();
        keys.addAll(Arrays.asList(ConfigManager.configKeys));
        keys.addAll(Arrays.asList(ConfigManager.deviceConfigKeys));
        keys.addAll(SimpleConfigProvider.get().getKeys());
        return keys;
    }

    private void e() {
        HandlerThread handlerThread = new HandlerThread("mm-config-update");
        handlerThread.start();
        this.c = new Handler(handlerThread.getLooper()) {
            int a = 10;

            public void handleMessage(Message msg) {
                Logger.D("FRWBroadcast", "updateHandler handleMessage what: " + msg.what, new Object[0]);
                try {
                    if (msg.what == 0) {
                        ConfigService service = AppUtils.getConfigService();
                        if (service != null || this.a <= 0) {
                            Logger.D("FRWBroadcast", "updateHandler registerSyncReceiverListener", new Object[0]);
                            FRWBroadcastReceiver.this.registerSyncCallback(service);
                        } else {
                            sendEmptyMessageDelayed(0, 1000);
                            this.a--;
                            return;
                        }
                    } else {
                        FRWBroadcastReceiver.d();
                    }
                } catch (Throwable t) {
                    Logger.E((String) "FRWBroadcast", t, (String) "updateHandler", new Object[0]);
                }
                removeMessages(1);
                sendEmptyMessageDelayed(1, 3600000);
            }
        };
    }
}
