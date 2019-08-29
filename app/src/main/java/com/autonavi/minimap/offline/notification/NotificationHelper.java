package com.autonavi.minimap.offline.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.externalimport.IExternalService;

public class NotificationHelper {
    private static NotificationHelper instance = null;
    public static final int notifyId = 2;
    private Builder mBuilder;
    PendingIntent mContentIntent;
    private Context mContext = ((IExternalService) ank.a(IExternalService.class)).getApplication();
    private NotificationManagerCompat mNotificationManager;
    String packageName;

    private NotificationHelper() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("androidamap://openFeature?featureName=downOfflineMap"));
        intent.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
        this.mContentIntent = PendingIntent.getActivity(this.mContext, 110, intent, 134217728);
        this.packageName = this.mContext.getPackageName();
        this.mNotificationManager = NotificationManagerCompat.from(this.mContext);
        this.mBuilder = new Builder(this.mContext);
        this.mBuilder.setWhen(System.currentTimeMillis()).setContentIntent(this.mContentIntent).setTicker(this.mContext.getString(R.string.offline_storage_hint));
        if (VERSION.SDK_INT >= 21) {
            this.mBuilder.setSmallIcon(R.drawable.notification_amap);
        } else {
            this.mBuilder.setSmallIcon(R.drawable.v3_icon);
        }
        this.mBuilder.setLargeIcon(BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.v3_icon));
        ky.a(this.mBuilder, NotificationChannelIds.c);
    }

    public static synchronized NotificationHelper getInstance() {
        NotificationHelper notificationHelper;
        synchronized (NotificationHelper.class) {
            try {
                if (instance == null) {
                    instance = new NotificationHelper();
                }
                notificationHelper = instance;
            }
        }
        return notificationHelper;
    }

    public void update(String str, String str2) {
        if (this.packageName != null && this.mBuilder != null && this.mNotificationManager != null) {
            RemoteViews remoteViews = new RemoteViews(this.packageName, R.layout.offlinedata_notification_view);
            remoteViews.setTextViewText(R.id.tv_custom_progress_title, str);
            remoteViews.setTextViewText(R.id.desc, str2);
            this.mBuilder.setContent(remoteViews);
            Notification build = this.mBuilder.build();
            build.flags |= 16;
            this.mNotificationManager.notify(2, build);
        }
    }

    public void cancel() {
        if (this.mNotificationManager != null) {
            this.mNotificationManager.cancel(2);
        }
    }
}
