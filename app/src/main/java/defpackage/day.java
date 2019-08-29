package defpackage;

import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.a;

/* renamed from: day reason: default package */
/* compiled from: MorePageFilter */
public final class day extends a {
    public final boolean a(AmapMessage amapMessage) {
        if (amapMessage.hasSub && amapMessage.sub_page == 1 && amapMessage.sub_location == 0 && amapMessage.sub_unread) {
            return amapMessage.expireAt == 0 || System.currentTimeMillis() <= amapMessage.expireAt;
        }
        return false;
    }
}
