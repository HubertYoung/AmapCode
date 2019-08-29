package defpackage;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.bundle.blutils.notification.NotificationChannelIds;

/* renamed from: ead reason: default package */
/* compiled from: RouteNotification */
public final class ead {
    int a;
    int b = -1;
    public String c;
    public String d;

    private ead(int i) {
        this.a = i;
    }

    public static ead a(int i) {
        return new ead(i);
    }

    static final NotificationChannelIds b(int i) {
        NotificationChannelIds notificationChannelIds = NotificationChannelIds.d;
        switch (i) {
            case 1:
                return NotificationChannelIds.k;
            case 2:
                return NotificationChannelIds.i;
            case 3:
                return NotificationChannelIds.j;
            case 4:
                return NotificationChannelIds.l;
            case 5:
                return NotificationChannelIds.m;
            case 6:
                return NotificationChannelIds.f;
            default:
                return notificationChannelIds;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(ead.class.getSimpleName());
        sb2.append(AUScreenAdaptTool.PREFIX_ID);
        sb2.append(Integer.toHexString(hashCode()));
        sb.append(sb2.toString());
        sb.append("{mType=");
        sb.append(this.a);
        sb.append(",mIconResId=");
        sb.append(this.b);
        sb.append(",mTitle=");
        sb.append(this.c);
        sb.append(",mContent=");
        sb.append(this.d);
        sb.append(",}");
        return sb.toString();
    }
}
