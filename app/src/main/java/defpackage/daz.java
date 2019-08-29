package defpackage;

import com.autonavi.minimap.bundle.msgbox.api.IMsgboxService.UIUpdater;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.b;
import java.util.Collections;
import java.util.List;

/* renamed from: daz reason: default package */
/* compiled from: MorePageListener */
public final class daz implements b {
    private UIUpdater a;

    public daz(UIUpdater uIUpdater) {
        this.a = uIUpdater;
    }

    public final void a(List<AmapMessage> list, List<btb> list2, boolean z) {
        if (list.size() > 0) {
            AmapMessage curDispBubbleMsg = MessageBoxManager.getInstance().getCurDispBubbleMsg();
            if (curDispBubbleMsg != null) {
                if (this.a != null) {
                    this.a.updateUI(curDispBubbleMsg, false, -1);
                }
                MessageBoxManager.getInstance().setCurDispBubbleMsg(null);
                return;
            }
            Collections.sort(list, new dbe());
            if (this.a != null) {
                this.a.updateUI(list.get(0), false, -1);
            }
            return;
        }
        if (this.a != null) {
            this.a.updateUI(null, true, -1);
        }
    }
}
