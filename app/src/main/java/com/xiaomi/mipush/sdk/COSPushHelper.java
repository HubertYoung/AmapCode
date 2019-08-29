package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.xiaomi.channel.commonutils.logger.b;

public class COSPushHelper {
    private static volatile boolean a = false;
    private static long b;

    public static void convertMessage(Intent intent) {
        g.a(intent);
    }

    public static void doInNetworkChange(Context context) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (!getNeedRegister()) {
            return;
        }
        if (b <= 0 || b + 300000 <= elapsedRealtime) {
            b = elapsedRealtime;
            registerCOSAssemblePush(context);
        }
    }

    public static boolean getNeedRegister() {
        return a;
    }

    public static boolean hasNetwork(Context context) {
        return g.a(context);
    }

    public static void onNotificationMessageCome(Context context, String str) {
    }

    public static void onPassThoughMessageCome(Context context, String str) {
    }

    public static void registerCOSAssemblePush(Context context) {
        AbstractPushManager c = e.a(context).c(d.ASSEMBLE_PUSH_COS);
        if (c != null) {
            b.c("register cos when network change!");
            c.register();
        }
    }

    public static synchronized void setNeedRegister(boolean z) {
        synchronized (COSPushHelper.class) {
            a = z;
        }
    }

    public static void uploadToken(Context context, String str) {
        g.a(context, d.ASSEMBLE_PUSH_COS, str);
    }
}
