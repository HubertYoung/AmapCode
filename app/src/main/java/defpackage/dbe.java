package defpackage;

import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import java.util.Comparator;

/* renamed from: dbe reason: default package */
/* compiled from: MsgboxMsgComparator */
public final class dbe implements Comparator<AmapMessage> {
    public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
        AmapMessage amapMessage = (AmapMessage) obj;
        AmapMessage amapMessage2 = (AmapMessage) obj2;
        if (amapMessage.priority > amapMessage2.priority) {
            return 1;
        }
        if (amapMessage.priority < amapMessage2.priority) {
            return -1;
        }
        return (int) (amapMessage2.createdTime - amapMessage.createdTime);
    }
}
