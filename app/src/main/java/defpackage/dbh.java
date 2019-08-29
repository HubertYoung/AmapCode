package defpackage;

import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.UIUpdater;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.b;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* renamed from: dbh reason: default package */
/* compiled from: RouteBusListPageListener */
public final class dbh implements b {
    private UIUpdater a;

    public dbh(UIUpdater uIUpdater) {
        this.a = uIUpdater;
    }

    public final void a(List<AmapMessage> list, List<btb> list2, boolean z) {
        if (list.size() > 0) {
            Collections.sort(list, new Comparator<AmapMessage>() {
                public final /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
                    int i = ((AmapMessage) obj).priority;
                    int i2 = ((AmapMessage) obj2).priority;
                    if (i > i2) {
                        return 1;
                    }
                    return i == i2 ? 0 : -1;
                }
            });
            this.a.updateUI(list.get(0), false, -1);
        }
    }
}
