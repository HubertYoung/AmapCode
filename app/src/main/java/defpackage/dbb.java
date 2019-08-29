package defpackage;

import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import java.util.Comparator;

/* renamed from: dbb reason: default package */
/* compiled from: MsgboxBarComparator */
public final class dbb implements Comparator<AmapMessage> {
    public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
        return ((AmapMessage) obj).priority >= ((AmapMessage) obj2).priority ? 1 : -1;
    }
}
