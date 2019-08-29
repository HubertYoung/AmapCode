package com.xiaomi.mipush.sdk;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.android.f;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.service.ah;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class MIPushNotificationHelper4Hybrid {
    public static final String KEY_PKGNAME = "miui.category";
    public static final String KEY_TITLE = "miui.substName";
    private static final LinkedList<a> a = new LinkedList<>();

    static class a {
        int a;
        String b;
        MiPushMessage c;

        public a(int i, String str, MiPushMessage miPushMessage) {
            this.a = i;
            this.b = str;
            this.c = miPushMessage;
        }
    }

    private static Notification a(Notification notification) {
        Object a2 = com.xiaomi.channel.commonutils.reflect.a.a((Object) notification, (String) "extraNotification");
        if (a2 != null) {
            com.xiaomi.channel.commonutils.reflect.a.a(a2, (String) "setCustomizedIcon", Boolean.TRUE);
        }
        return notification;
    }

    @SuppressLint({"NewApi"})
    private static Notification a(Context context, MiPushMessage miPushMessage, PendingIntent pendingIntent, Bitmap bitmap) {
        boolean z;
        Map<String, String> extra = miPushMessage.getExtra();
        Builder builder = new Builder(context);
        if (VERSION.SDK_INT >= 26) {
            builder.setChannelId("default");
        }
        builder.setContentTitle(miPushMessage.getTitle());
        builder.setContentText(miPushMessage.getDescription());
        if (VERSION.SDK_INT >= 16) {
            builder.setStyle(new BigTextStyle().bigText(miPushMessage.getDescription()));
        }
        builder.setWhen(System.currentTimeMillis());
        String str = extra == null ? null : extra.get("notification_show_when");
        if (!TextUtils.isEmpty(str)) {
            builder.setShowWhen(Boolean.parseBoolean(str));
        } else if (VERSION.SDK_INT >= 24) {
            builder.setShowWhen(true);
        }
        builder.setContentIntent(pendingIntent);
        int i = context.getApplicationInfo().icon;
        if (i == 0 && VERSION.SDK_INT >= 9) {
            i = context.getApplicationInfo().logo;
        }
        builder.setSmallIcon(i);
        if (bitmap != null) {
            builder.setLargeIcon(bitmap);
            z = true;
        } else {
            z = false;
        }
        if (extra != null && VERSION.SDK_INT >= 24) {
            boolean parseBoolean = Boolean.parseBoolean(extra.get("notification_is_summary"));
            com.xiaomi.channel.commonutils.reflect.a.a((Object) builder, (String) "setGroup", extra.get("notification_group"));
            com.xiaomi.channel.commonutils.reflect.a.a((Object) builder, (String) "setGroupSummary", Boolean.valueOf(parseBoolean));
        }
        builder.setAutoCancel(true);
        long currentTimeMillis = System.currentTimeMillis();
        if (extra != null && extra.containsKey("ticker")) {
            builder.setTicker(extra.get("ticker"));
        }
        if (currentTimeMillis - ah.a > 10000) {
            ah.a = currentTimeMillis;
            builder.setDefaults(miPushMessage.getNotifyType());
        }
        Notification notification = builder.getNotification();
        if (z && f.a()) {
            a(notification);
        }
        return notification;
    }

    private static PendingIntent a(Context context, String str, MiPushMessage miPushMessage) {
        Intent intent = new Intent("com.xiaomi.mipush.RECEIVE_MESSAGE");
        intent.setComponent(new ComponentName(context.getPackageName(), "com.xiaomi.mipush.sdk.PushMessageHandler"));
        intent.setAction("com.xiaomi.mipush.sdk.HYBRID_NOTIFICATION_CLICK");
        intent.putExtra("mipush_payload", miPushMessage);
        intent.putExtra("mipush_hybrid_app_pkg", str);
        intent.putExtra("mipush_notified", true);
        intent.addCategory(String.valueOf(miPushMessage.getNotifyId()));
        return PendingIntent.getService(context, 0, intent, 134217728);
    }

    private static void a(Notification notification, String str) {
        try {
            Object a2 = com.xiaomi.channel.commonutils.reflect.a.a((Object) notification, (String) "extraNotification");
            if (a2 != null) {
                com.xiaomi.channel.commonutils.reflect.a.b(a2, "setMessageClassName", str);
                return;
            }
            b.d("Get null extraNotification, setShortcutId failed.");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void clearNotification(Context context, String str) {
        clearNotification(context, str, -1);
    }

    public static void clearNotification(Context context, String str, int i) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        int hashCode = ((str.hashCode() / 10) * 10) + i;
        LinkedList linkedList = new LinkedList();
        if (i >= 0) {
            notificationManager.cancel(hashCode);
        }
        synchronized (a) {
            Iterator it = a.iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                if (i >= 0) {
                    if (hashCode == aVar.a) {
                        if (!TextUtils.equals(str, aVar.b)) {
                        }
                    }
                } else if (i == -1 && TextUtils.equals(str, aVar.b)) {
                    notificationManager.cancel(aVar.a);
                }
                linkedList.add(aVar);
            }
            if (a != null) {
                a.removeAll(linkedList);
                ah.a(context, linkedList);
            }
        }
    }

    public static void notifyPushMessage(Context context, String str, String str2, String str3, Bitmap bitmap, MiPushMessage miPushMessage) {
        if (VERSION.SDK_INT >= 11) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            PendingIntent a2 = a(context, str, miPushMessage);
            if (a2 == null) {
                b.a((String) "The click PendingIntent is null. ");
                return;
            }
            Notification a3 = a(context, miPushMessage, a2, bitmap);
            if (f.a()) {
                if (VERSION.SDK_INT >= 19 && !TextUtils.isEmpty(miPushMessage.getMessageId())) {
                    a3.extras.putString("message_id", miPushMessage.getMessageId());
                }
                a(a3, str3);
                Bundle bundle = a3.extras;
                if (bundle == null) {
                    bundle = new Bundle();
                    a3.extras = bundle;
                }
                bundle.putString(KEY_PKGNAME, str);
                bundle.putString(KEY_TITLE, str2);
            }
            int hashCode = ((str.hashCode() / 10) * 10) + miPushMessage.getNotifyId();
            notificationManager.notify(hashCode, a3);
            a aVar = new a(hashCode, str, miPushMessage);
            synchronized (a) {
                a.add(aVar);
                if (a.size() > 100) {
                    a.remove();
                }
            }
        }
    }
}
