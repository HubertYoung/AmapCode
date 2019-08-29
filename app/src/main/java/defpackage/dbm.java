package defpackage;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.minimap.R;
import com.taobao.accs.IAppReceiver;

@BundleInterface(fhb.class)
/* renamed from: dbm reason: default package */
/* compiled from: NotificationService */
public class dbm extends esi implements fhb {
    public final fhc a() {
        return dbr.a();
    }

    public final IAppReceiver c() {
        return new dbo();
    }

    public final fhd d() {
        return new dbt();
    }

    public final boolean e() {
        return dbq.a();
    }

    public final boolean a(Context context) {
        return dbr.a(context);
    }

    public final void b() {
        try {
            ((NotificationManager) AMapAppGlobal.getApplication().getSystemService("notification")).cancelAll();
        } catch (SecurityException unused) {
        }
    }

    public final void a(String str, String str2, String str3, String str4, int i) {
        PendingIntent pendingIntent;
        Bitmap bitmap;
        if (!TextUtils.isEmpty(str4)) {
            pendingIntent = PendingIntent.getActivity(AMapAppGlobal.getApplication(), i, new Intent("android.intent.action.VIEW", Uri.parse(str4)), 268435456);
        } else {
            pendingIntent = null;
        }
        Builder builder = new Builder(AMapAppGlobal.getApplication());
        builder.setTicker(str2).setContentTitle(str).setContentText(str2).setWhen(System.currentTimeMillis()).setDefaults(-1).setOngoing(false).setContentIntent(pendingIntent);
        if (VERSION.SDK_INT >= 21) {
            builder.setSmallIcon(R.drawable.notification_amap);
        } else {
            builder.setSmallIcon(R.drawable.v3_icon);
        }
        if (TextUtils.isEmpty(str3)) {
            bitmap = BitmapFactory.decodeResource(AMapAppGlobal.getApplication().getResources(), R.drawable.v3_icon);
        } else {
            bitmap = BitmapFactory.decodeFile(str3);
        }
        builder.setLargeIcon(bitmap);
        ky.a(builder, NotificationChannelIds.d);
        Notification build = builder.build();
        build.flags |= 16;
        ((NotificationManager) AMapAppGlobal.getApplication().getSystemService("notification")).notify(i, build);
    }
}
