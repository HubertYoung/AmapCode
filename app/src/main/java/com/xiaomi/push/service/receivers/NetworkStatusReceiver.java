package com.xiaomi.push.service.receivers;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.network.d;
import com.xiaomi.mipush.sdk.COSPushHelper;
import com.xiaomi.mipush.sdk.HWPushHelper;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.ab;
import com.xiaomi.mipush.sdk.aj;
import com.xiaomi.mipush.sdk.ao;
import com.xiaomi.mipush.sdk.c;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NetworkStatusReceiver extends BroadcastReceiver {
    private static int a = 1;
    private static int b = 1;
    private static int c = 2;
    private static BlockingQueue<Runnable> d = new LinkedBlockingQueue();
    private static ThreadPoolExecutor e = null;
    private static boolean f = false;
    private boolean g;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(a, b, (long) c, TimeUnit.SECONDS, d);
        e = threadPoolExecutor;
    }

    public NetworkStatusReceiver() {
        this.g = false;
        this.g = true;
    }

    public NetworkStatusReceiver(Object obj) {
        this.g = false;
        f = true;
    }

    /* access modifiers changed from: private */
    public void a(Context context) {
        if (!aj.a(context).c() && c.a(context).i() && !c.a(context).m()) {
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(context, "com.xiaomi.push.service.XMPushService"));
                intent.setAction("com.xiaomi.push.network_status_changed");
                context.startService(intent);
            } catch (Exception e2) {
                b.a((Throwable) e2);
            }
        }
        if (d.c(context) && aj.a(context).g()) {
            aj.a(context).d();
        }
        if (d.c(context)) {
            if ("syncing".equals(ab.a(context).a(ao.DISABLE_PUSH))) {
                MiPushClient.disablePush(context);
            }
            if ("syncing".equals(ab.a(context).a(ao.ENABLE_PUSH))) {
                MiPushClient.enablePush(context);
            }
            if ("syncing".equals(ab.a(context).a(ao.UPLOAD_HUAWEI_TOKEN))) {
                MiPushClient.syncAssemblePushToken(context);
            }
            if ("syncing".equals(ab.a(context).a(ao.UPLOAD_FCM_TOKEN))) {
                MiPushClient.syncAssembleFCMPushToken(context);
            }
            if ("syncing".equals(ab.a(context).a(ao.UPLOAD_COS_TOKEN))) {
                MiPushClient.syncAssembleCOSPushToken(context);
            }
            if (HWPushHelper.needConnect() && HWPushHelper.shouldTryConnect(context)) {
                HWPushHelper.setConnectTime(context);
                HWPushHelper.registerHuaWeiAssemblePush(context);
            }
            COSPushHelper.doInNetworkChange(context);
        }
    }

    public static boolean a() {
        return f;
    }

    public void onReceive(Context context, Intent intent) {
        if (!this.g) {
            e.execute(new a(this, context));
        }
    }
}
