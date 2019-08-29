package com.amap.bundle.blutils.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.HashMap;

public final class NotificationChannelIds {
    public static final NotificationChannelIds b = new NotificationChannelIds(null, "download_app");
    public static final NotificationChannelIds c = new NotificationChannelIds(null, "download_offline_map");
    public static final NotificationChannelIds d = new NotificationChannelIds(null, "default");
    public static final NotificationChannelIds e = new NotificationChannelIds(kz.a, "jiaoche");
    public static final NotificationChannelIds f = new NotificationChannelIds(kz.a, "danche");
    public static final NotificationChannelIds g = new NotificationChannelIds(kz.b, "car");
    public static final NotificationChannelIds h = new NotificationChannelIds(kz.b, "edog");
    public static final NotificationChannelIds i = new NotificationChannelIds(kz.b, ShowRouteActionProcessor.SEARCH_TYPE_WALK);
    public static final NotificationChannelIds j = new NotificationChannelIds(kz.b, ShowRouteActionProcessor.SEARCH_TYPE_RIDE);
    public static final NotificationChannelIds k = new NotificationChannelIds(kz.b, "bus_arrival");
    public static final NotificationChannelIds l = new NotificationChannelIds(kz.c, "noti_run");
    public static final NotificationChannelIds m = new NotificationChannelIds(kz.c, "noti_ride");
    public static final NotificationChannelIds n = new NotificationChannelIds(kz.c, WidgetType.ACTIVITY);
    public static final NotificationChannelIds o = new NotificationChannelIds(kz.c, "personal_msg");
    public static final NotificationChannelIds p = new NotificationChannelIds(kz.c, "school_bus");
    private static HashMap<String, NotificationChannelIds> q = new HashMap<>();
    public String a;
    private kz r;
    private String s;
    private int t;
    private String u;
    private Influence v;

    public enum Influence {
        FUNCTION,
        EXPERIENCE,
        NONE
    }

    public static void a(Context context) {
        b.a(context.getString(R.string.noti_other_app_download), 4, Influence.FUNCTION, "");
        c.a(context.getString(R.string.noti_other_offline_map_download), 2, Influence.NONE, "");
        d.a(context.getString(R.string.noti_other_default), 2, Influence.NONE, "");
        e.a(context.getString(R.string.noti_order_dache), 4, Influence.FUNCTION, context.getString(R.string.noti_channel_desc_order));
        f.a(context.getString(R.string.noti_order_danche), 2, Influence.FUNCTION, context.getString(R.string.noti_channel_desc_order));
        g.a(context.getString(R.string.noti_route_car), 2, Influence.FUNCTION, context.getString(R.string.noti_channel_desc_route));
        h.a(context.getString(R.string.noti_route_car_edog), 2, Influence.FUNCTION, context.getString(R.string.noti_channel_desc_route));
        i.a(context.getString(R.string.noti_route_walk), 2, Influence.FUNCTION, context.getString(R.string.noti_channel_desc_route));
        j.a(context.getString(R.string.noti_route_ride), 2, Influence.FUNCTION, context.getString(R.string.noti_channel_desc_route));
        k.a(context.getString(R.string.noti_route_bus_arrival), 4, Influence.FUNCTION, context.getString(R.string.noti_channel_desc_route));
        p.a(context.getString(R.string.noti_school_bus), 4, Influence.FUNCTION, context.getString(R.string.noti_channel_desc_route));
        l.a(context.getString(R.string.noti_common_run), 3, Influence.NONE, context.getString(R.string.noti_channel_desc_common));
        m.a(context.getString(R.string.noti_common_ride), 3, Influence.NONE, context.getString(R.string.noti_channel_desc_common));
        n.a(context.getString(R.string.noti_common_activity), 3, Influence.NONE, context.getString(R.string.noti_channel_desc_common));
        o.a(context.getString(R.string.noti_common_personal_msg), 4, Influence.FUNCTION, context.getString(R.string.noti_channel_desc_common));
    }

    public static void a(NotificationManager notificationManager) {
        NotificationChannel notificationChannel;
        if (VERSION.SDK_INT >= 26) {
            ArrayList arrayList = new ArrayList();
            for (NotificationChannelIds next : q.values()) {
                if (VERSION.SDK_INT >= 26) {
                    notificationChannel = new NotificationChannel(next.a, next.s, next.t);
                    notificationChannel.setDescription(next.u);
                    if (next.r != null) {
                        notificationChannel.setGroup(next.r.d);
                    }
                    switch (next.t) {
                        case 3:
                            notificationChannel.setLockscreenVisibility(-1);
                            break;
                        case 4:
                            notificationChannel.setLockscreenVisibility(0);
                            break;
                        default:
                            notificationChannel.setLockscreenVisibility(-1);
                            break;
                    }
                } else {
                    notificationChannel = null;
                }
                if (notificationChannel != null) {
                    arrayList.add(notificationChannel);
                }
            }
            notificationManager.createNotificationChannels(arrayList);
        }
    }

    private NotificationChannelIds(kz kzVar, String str) {
        this.r = kzVar;
        this.a = str;
        if (kzVar != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(kzVar.d);
            sb.append("_");
            sb.append(this.a);
            this.a = sb.toString();
        }
        if (!q.containsKey(str)) {
            q.put(str, this);
        } else if (a.a) {
            throw new RuntimeException("channel id duplicated:".concat(String.valueOf(str)));
        }
    }

    private void a(String str, int i2, Influence influence, String str2) {
        if (!TextUtils.isEmpty(str) && i2 <= 5 && i2 > 0 && influence != null) {
            this.s = str;
            this.t = i2;
            this.u = str2;
            this.v = influence;
        } else if (a.a) {
            throw new RuntimeException("params not legal");
        }
    }
}
