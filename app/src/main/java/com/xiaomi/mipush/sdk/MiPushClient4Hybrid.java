package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.android.f;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.string.d;
import com.xiaomi.mipush.sdk.c.a;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.aj;
import com.xiaomi.xmpush.thrift.ak;
import com.xiaomi.xmpush.thrift.aq;
import com.xiaomi.xmpush.thrift.ar;
import com.xiaomi.xmpush.thrift.au;
import com.xiaomi.xmpush.thrift.r;
import com.xiaomi.xmpush.thrift.u;
import com.xiaomi.xmpush.thrift.w;
import com.xiaomi.xmpush.thrift.z;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MiPushClient4Hybrid {
    private static Map<String, a> a = new HashMap();
    private static Map<String, Long> b = new HashMap();
    private static MiPushClientCallbackV2 c;

    private static void a(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("mipush_extra", 0);
        sharedPreferences.edit().putLong("last_pull_notification_".concat(String.valueOf(str)), System.currentTimeMillis()).commit();
    }

    public static void ackPassThroughMsg(Context context, MiPushMessage miPushMessage) {
        if (miPushMessage == null || miPushMessage.getExtra() == null) {
            b.a((String) "do not ack pass through message, message is null");
            return;
        }
        try {
            z zVar = new z();
            String str = miPushMessage.getExtra().get(Constants.EXTRA_KEY_MINA_APPID);
            zVar.b(str);
            zVar.a(miPushMessage.getMessageId());
            zVar.a(Long.valueOf(miPushMessage.getExtra().get(Constants.EXTRA_KEY_MINA_MESSAGE_TS)).longValue());
            zVar.a(Short.valueOf(miPushMessage.getExtra().get(Constants.EXTRA_KEY_MINA_DEVICE_STATUS)).shortValue());
            if (!TextUtils.isEmpty(miPushMessage.getTopic())) {
                zVar.c(miPushMessage.getTopic());
            }
            aj.a(context).a(zVar, com.xiaomi.xmpush.thrift.a.AckMessage, false, (u) null);
            StringBuilder sb = new StringBuilder("MiPushClient4Hybrid ack mina pass through message, appId is :");
            sb.append(str);
            sb.append(", messageId is ");
            sb.append(miPushMessage.getMessageId());
            b.b(sb.toString());
        } catch (Throwable th) {
            b.a(th);
        } finally {
            miPushMessage.getExtra().remove(Constants.EXTRA_KEY_MINA_APPID);
            miPushMessage.getExtra().remove(Constants.EXTRA_KEY_MINA_MESSAGE_TS);
            miPushMessage.getExtra().remove(Constants.EXTRA_KEY_MINA_DEVICE_STATUS);
        }
    }

    private static boolean b(Context context, String str) {
        return Math.abs(System.currentTimeMillis() - context.getSharedPreferences("mipush_extra", 0).getLong("last_pull_notification_".concat(String.valueOf(str)), -1)) > 300000;
    }

    public static void onNotificationMessageArrived(Context context, String str, MiPushMessage miPushMessage) {
        if (!TextUtils.isEmpty(str) && c != null) {
            c.onNotificationMessageArrived(str, miPushMessage);
        }
    }

    public static void onNotificationMessageClicked(Context context, String str, MiPushMessage miPushMessage) {
        if (!TextUtils.isEmpty(str) && c != null) {
            c.onNotificationMessageClicked(str, miPushMessage);
        }
    }

    public static void onReceivePassThroughMessage(Context context, String str, MiPushMessage miPushMessage) {
        if (!TextUtils.isEmpty(str) && c != null) {
            c.onReceivePassThroughMessage(str, miPushMessage);
        }
    }

    public static void onReceiveRegisterResult(Context context, ak akVar) {
        String l = akVar.l();
        if (akVar.g() == 0) {
            a aVar = a.get(l);
            if (aVar != null) {
                aVar.b(akVar.h, akVar.i);
                c.a(context).a(l, aVar);
            }
        }
        ArrayList arrayList = null;
        if (!TextUtils.isEmpty(akVar.h)) {
            arrayList = new ArrayList();
            arrayList.add(akVar.h);
        }
        MiPushCommandMessage generateCommandMessage = PushMessageHelper.generateCommandMessage("register", arrayList, akVar.f, akVar.g, null);
        if (c != null) {
            c.onReceiveRegisterResult(l, generateCommandMessage);
        }
    }

    public static void onReceiveUnregisterResult(Context context, ar arVar) {
        MiPushCommandMessage generateCommandMessage = PushMessageHelper.generateCommandMessage("unregister", null, arVar.f, arVar.g, null);
        String h = arVar.h();
        if (c != null) {
            c.onReceiveUnregisterResult(h, generateCommandMessage);
        }
    }

    public static void registerPush(Context context, String str, String str2, String str3) {
        if (c.a(context).b(str2, str3, str)) {
            ArrayList arrayList = new ArrayList();
            a b2 = c.a(context).b(str);
            if (b2 != null) {
                arrayList.add(b2.c);
                MiPushCommandMessage generateCommandMessage = PushMessageHelper.generateCommandMessage("register", arrayList, 0, null, null);
                if (c != null) {
                    c.onReceiveRegisterResult(str, generateCommandMessage);
                }
            }
            if (b(context, str)) {
                ai aiVar = new ai();
                aiVar.b(str2);
                aiVar.c(r.PullOfflineMessage.W);
                aiVar.a(MiPushClient.generatePacketID());
                aiVar.a(false);
                aj.a(context).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, false, true, null, false, str, str2);
                b.b("MiPushClient4Hybrid pull offline pass through message");
                a(context, str);
            }
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - (b.get(str) != null ? b.get(str).longValue() : 0)) < 5000) {
            b.a((String) "MiPushClient4Hybrid  Could not send register message within 5s repeatedly.");
            return;
        }
        b.put(str, Long.valueOf(currentTimeMillis));
        String a2 = d.a(6);
        a aVar = new a(context);
        aVar.b(str2, str3, a2);
        a.put(str, aVar);
        aj ajVar = new aj();
        ajVar.a(MiPushClient.generatePacketID());
        ajVar.b(str2);
        ajVar.e(str3);
        ajVar.d(str);
        ajVar.f(a2);
        ajVar.c(com.xiaomi.channel.commonutils.android.a.a(context, context.getPackageName()));
        ajVar.b(com.xiaomi.channel.commonutils.android.a.b(context, context.getPackageName()));
        ajVar.g("3_6_2");
        ajVar.a(30602);
        ajVar.h(com.xiaomi.channel.commonutils.android.d.a(context));
        ajVar.a(w.Init);
        if (f.e()) {
            String c2 = com.xiaomi.channel.commonutils.android.d.c(context);
            if (!TextUtils.isEmpty(c2)) {
                if (f.b()) {
                    ajVar.i(c2);
                }
                ajVar.k(d.a(c2));
            }
        }
        ajVar.j(com.xiaomi.channel.commonutils.android.d.a());
        int b3 = com.xiaomi.channel.commonutils.android.d.b();
        if (b3 >= 0) {
            ajVar.c(b3);
        }
        ai aiVar2 = new ai();
        aiVar2.c(r.HybridRegister.W);
        aiVar2.b(c.a(context).c());
        aiVar2.d(context.getPackageName());
        aiVar2.a(au.a(ajVar));
        aiVar2.a(MiPushClient.generatePacketID());
        aj.a(context).a(aiVar2, com.xiaomi.xmpush.thrift.a.Notification, (u) null);
    }

    public static void removeDuplicateCache(Context context, MiPushMessage miPushMessage) {
        String str = miPushMessage.getExtra() != null ? miPushMessage.getExtra().get("jobkey") : null;
        if (TextUtils.isEmpty(str)) {
            str = miPushMessage.getMessageId();
        }
        ah.a(context, str);
    }

    public static void setCallback(MiPushClientCallbackV2 miPushClientCallbackV2) {
        c = miPushClientCallbackV2;
    }

    public static void unregisterPush(Context context, String str) {
        a b2 = c.a(context).b(str);
        if (b2 != null) {
            aq aqVar = new aq();
            aqVar.a(MiPushClient.generatePacketID());
            aqVar.d(str);
            aqVar.b(b2.a);
            aqVar.c(b2.c);
            aqVar.e(b2.b);
            ai aiVar = new ai();
            aiVar.c(r.HybridUnregister.W);
            aiVar.b(c.a(context).c());
            aiVar.d(context.getPackageName());
            aiVar.a(au.a(aqVar));
            aiVar.a(MiPushClient.generatePacketID());
            aj.a(context).a(aiVar, com.xiaomi.xmpush.thrift.a.Notification, (u) null);
            c.a(context).c(str);
            MIPushNotificationHelper4Hybrid.clearNotification(context, str);
        }
    }
}
