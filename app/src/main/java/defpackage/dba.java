package defpackage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.alipay.mobile.nebula.appcenter.apphandler.H5PreferAppList;
import java.util.HashMap;

/* renamed from: dba reason: default package */
/* compiled from: MsgboxBadgeAlarmManager */
public class dba {
    public static final long[] a = {TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL, TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL, 3600000, H5PreferAppList.defaultTime, 25200000, 43200000, 86400000};
    private static dba e;
    private HashMap<String, PendingIntent> b = new HashMap<>();
    private AlarmManager c;
    private Context d;

    private dba(Context context) {
        this.d = context.getApplicationContext();
        this.c = (AlarmManager) this.d.getSystemService(NotificationCompat.CATEGORY_ALARM);
    }

    public static dba a(Context context) {
        if (e == null) {
            synchronized (dba.class) {
                try {
                    if (e == null) {
                        e = new dba(context);
                    }
                }
            }
        }
        return e;
    }

    public final void a(int i) {
        a();
        if (i >= 0 && i < a.length) {
            Intent intent = new Intent("com.autonavi.minimap.action.Badge");
            intent.putExtra("extra_key_index", i);
            PendingIntent broadcast = PendingIntent.getBroadcast(this.d, 0, intent, 134217728);
            this.b.put("com.autonavi.minimap.action.Badge", broadcast);
            this.c.set(2, SystemClock.elapsedRealtime() + a[i], broadcast);
        }
    }

    public final boolean a() {
        PendingIntent pendingIntent = this.b.get("com.autonavi.minimap.action.Badge");
        if (pendingIntent != null) {
            this.c.cancel(pendingIntent);
            this.b.remove("com.autonavi.minimap.action.Badge");
            return true;
        }
        PendingIntent broadcast = PendingIntent.getBroadcast(this.d, 0, new Intent("com.autonavi.minimap.action.Badge"), 134217728);
        if (broadcast != null) {
            this.c.cancel(broadcast);
        }
        return false;
    }
}
