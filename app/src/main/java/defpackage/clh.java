package defpackage;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import com.amap.bundle.blutils.notification.NotificationChannelIds;

/* renamed from: clh reason: default package */
/* compiled from: NotificationChannel */
public final class clh extends cky {
    /* access modifiers changed from: protected */
    @NonNull
    public final String a() {
        return "NotificationChannel";
    }

    /* access modifiers changed from: protected */
    public final void a(Application application) {
        kz.a((Context) application);
        NotificationChannelIds.a((Context) application);
        NotificationManager notificationManager = (NotificationManager) application.getSystemService("notification");
        kz.a(notificationManager);
        NotificationChannelIds.a(notificationManager);
    }
}
