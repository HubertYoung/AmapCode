package defpackage;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.amaphome.compat.service.NotifyServiceImpl;
import com.autonavi.bundle.amaphome.compat.service.NotifyServiceImpl.MyBinder;
import com.autonavi.common.utils.Constant;
import com.autonavi.minimap.R;

/* renamed from: aqk reason: default package */
/* compiled from: NotifyService */
public final class aqk implements czk {
    /* access modifiers changed from: private */
    public NotifyServiceImpl a;
    private ServiceConnection b;
    /* access modifiers changed from: private */
    public boolean c;

    public final void a(final a aVar) {
        if (!this.c) {
            this.b = new ServiceConnection() {
                public final void onServiceDisconnected(ComponentName componentName) {
                }

                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    aqk.this.a = ((MyBinder) iBinder).getService();
                    aqk.this.c = true;
                    if (aVar != null) {
                        aVar.a();
                    }
                }
            };
            Context applicationContext = AMapAppGlobal.getApplication().getApplicationContext();
            Intent intent = new Intent();
            intent.setClass(applicationContext, NotifyServiceImpl.class);
            applicationContext.bindService(intent, this.b, 1);
        }
    }

    public final void a(NotificationChannelIds notificationChannelIds, int i, String str, String str2) {
        if (this.a != null && this.c) {
            NotifyServiceImpl notifyServiceImpl = this.a;
            int i2 = R.drawable.v3_icon;
            if (i <= 0) {
                i = i2;
            }
            if (notifyServiceImpl.c != i || !TextUtils.equals(str, notifyServiceImpl.d) || !TextUtils.equals(str2, notifyServiceImpl.e)) {
                notifyServiceImpl.a();
            }
            notifyServiceImpl.c = i;
            notifyServiceImpl.d = str;
            notifyServiceImpl.e = str2;
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(notifyServiceImpl, Constant.LAUNCHER_ACTIVITY_NAME));
            intent.addFlags(603979776);
            PendingIntent activity = PendingIntent.getActivity(notifyServiceImpl, 0, intent, 0);
            Bundle bundle = new Bundle();
            bundle.putBoolean("com.autonavi.minimap.navigating", true);
            Builder ongoing = new Builder(notifyServiceImpl).setLargeIcon(BitmapFactory.decodeResource(notifyServiceImpl.getResources(), i)).setSmallIcon(R.drawable.notification_amap).setTicker(str).setWhen(System.currentTimeMillis()).setContentTitle(str).setContentText(str2).setContentIntent(activity).setExtras(bundle).setAutoCancel(false).setOngoing(true);
            ky.a(ongoing, notificationChannelIds);
            if (NotificationChannelIds.g.a.equals(notificationChannelIds.a)) {
                NotifyServiceImpl.a = 99910001;
            } else if (NotificationChannelIds.i.a.equals(notificationChannelIds.a)) {
                NotifyServiceImpl.a = 99910002;
            } else if (NotificationChannelIds.j.a.equals(notificationChannelIds.a)) {
                NotifyServiceImpl.a = 99910003;
            } else {
                NotifyServiceImpl.a = 10000;
            }
            Notification build = ongoing.build();
            if (NotifyServiceImpl.b()) {
                notifyServiceImpl.startForeground(NotifyServiceImpl.a, build);
                notifyServiceImpl.b.notify(NotifyServiceImpl.a, build);
                return;
            }
            notifyServiceImpl.startForeground(NotifyServiceImpl.a, build);
        }
    }

    public final void a() {
        if (this.c) {
            Context applicationContext = AMapAppGlobal.getApplication().getApplicationContext();
            if (this.b != null) {
                try {
                    applicationContext.unbindService(this.b);
                    this.b = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.a = null;
            Intent intent = new Intent();
            intent.setClass(applicationContext, NotifyServiceImpl.class);
            applicationContext.stopService(intent);
            this.c = false;
        }
    }

    public final boolean b() {
        return this.c;
    }
}
