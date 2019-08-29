package com.autonavi.minimap.route.foot.footnavi;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat.Builder;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class FootNaviService extends Service {
    private static final int NOTIFICATION_ID = 1;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        if (AMapAppGlobal.getTopActivity() != null) {
            if (VERSION.SDK_INT >= 26) {
                startForeground(1, new Builder(AMapAppGlobal.getApplication().getApplicationContext()).setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, AMapAppGlobal.getTopActivity().getClass()), 0)).setSmallIcon(R.drawable.ic_launcher).setTicker(getResources().getString(R.string.autonavi_navi_ing)).setContentTitle(getResources().getString(R.string.autonavi_navi_ing)).setContentText(getResources().getString(R.string.autonavi_app_name_in_route)).setChannelId(NotificationChannelIds.i.a).build());
                return;
            }
            startForeground(1, new Notification());
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        return 2;
    }

    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
}
