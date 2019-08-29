package defpackage;

import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build.VERSION;
import com.autonavi.minimap.R;
import java.util.ArrayList;

/* renamed from: kz reason: default package */
/* compiled from: NotificationChannelGroupIds */
public final class kz {
    public static final kz a = new kz("ORDER");
    public static final kz b = new kz("ROUTE");
    public static final kz c = new kz("COMMON");
    public String d;
    private String e;

    private kz(String str) {
        this.d = str;
    }

    public static void a(Context context) {
        a.e = context.getString(R.string.noti_group_order);
        b.e = context.getString(R.string.noti_group_route);
        c.e = context.getString(R.string.noti_group_common);
    }

    public static void a(NotificationManager notificationManager) {
        if (VERSION.SDK_INT >= 26) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new NotificationChannelGroup(a.d, a.e));
            arrayList.add(new NotificationChannelGroup(b.d, b.e));
            arrayList.add(new NotificationChannelGroup(c.d, c.e));
            notificationManager.createNotificationChannelGroups(arrayList);
        }
    }
}
