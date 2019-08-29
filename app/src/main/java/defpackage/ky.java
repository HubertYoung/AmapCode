package defpackage;

import android.app.Notification.Builder;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat;
import com.amap.bundle.blutils.notification.NotificationChannelIds;

/* renamed from: ky reason: default package */
/* compiled from: NotificationCenter */
public final class ky {
    public static void a(Builder builder, NotificationChannelIds notificationChannelIds) {
        if (VERSION.SDK_INT >= 26) {
            builder.setChannelId(notificationChannelIds.a);
        }
    }

    public static void a(NotificationCompat.Builder builder, NotificationChannelIds notificationChannelIds) {
        if (VERSION.SDK_INT >= 26) {
            builder.setChannelId(notificationChannelIds.a);
        }
    }
}
