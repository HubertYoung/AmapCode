package defpackage;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.widget.RemoteViews;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;

/* renamed from: jf reason: default package */
/* compiled from: AppDownLoadNotification */
public final class jf implements jn {
    String a;
    Context b = AMapAppGlobal.getApplication();
    Notification c;
    Builder d;
    int e;
    private long f = 0;

    public final void a() {
    }

    public jf(String str, int i) {
        this.a = str;
        this.e = i;
    }

    public final void a(int i) {
        if (!(this.c == null || this.b == null || this.a == null || System.currentTimeMillis() - this.f <= 1000 || this.c == null)) {
            this.f = System.currentTimeMillis();
            this.c.contentView = new RemoteViews(this.b.getPackageName(), R.layout.download_notification_layout);
            RemoteViews remoteViews = this.c.contentView;
            int i2 = R.id.appname;
            StringBuilder sb = new StringBuilder();
            sb.append(this.a);
            sb.append(AMapAppGlobal.getApplication().getString(R.string.app_download_downloading));
            remoteViews.setTextViewText(i2, sb.toString());
            RemoteViews remoteViews2 = this.c.contentView;
            int i3 = R.id.progress_txt;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(i);
            sb2.append("%");
            remoteViews2.setTextViewText(i3, sb2.toString());
            this.c.contentView.setProgressBar(R.id.progressbar, 100, i, false);
            this.c.contentIntent = PendingIntent.getActivity(this.b, this.e, new Intent("com.autonavi.minimap.ACTION"), 134217728);
            ((NotificationManager) this.b.getSystemService("notification")).notify(this.e, this.c);
        }
    }

    public final void b() {
        if (this.c != null && this.b != null && this.a != null) {
            this.c.contentView.setTextViewText(R.id.progress_txt, AMapAppGlobal.getApplication().getString(R.string.app_download_down_fail));
            Intent intent = new Intent("com.autonavi.minimap.ACTION");
            StringBuilder sb = new StringBuilder("appDownloadfail");
            sb.append(String.valueOf(this.e));
            intent.setAction(sb.toString());
            PendingIntent activity = PendingIntent.getActivity(this.b, this.e, intent, 134217728);
            String str = this.a;
            String string = AMapAppGlobal.getApplication().getString(R.string.app_download_retry);
            if (this.d != null) {
                this.d.setContentTitle(str).setContentText(string).setContentIntent(activity);
                ky.a(this.d, NotificationChannelIds.b);
                if (VERSION.SDK_INT >= 16) {
                    this.c = this.d.build();
                } else {
                    this.c = this.d.getNotification();
                }
            }
            ((NotificationManager) this.b.getSystemService("notification")).notify(this.e, this.c);
        }
    }

    public final void c() {
        ((NotificationManager) this.b.getSystemService("notification")).cancel(this.e);
    }
}
