package defpackage;

import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import java.util.Comparator;

/* renamed from: dbd reason: default package */
/* compiled from: MsgboxMainPageComparator */
public final class dbd implements Comparator<AmapMessage> {
    public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
        AmapMessage amapMessage = (AmapMessage) obj;
        AmapMessage amapMessage2 = (AmapMessage) obj2;
        if (amapMessage.createdTime < amapMessage2.createdTime) {
            return -1;
        }
        return amapMessage.createdTime > amapMessage2.createdTime ? 1 : 0;
    }
}
