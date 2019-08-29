package defpackage;

import com.autonavi.bundle.uitemplate.mapwidget.widget.msg.IMsgGroupEventDelegate;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager;

/* renamed from: asa reason: default package */
/* compiled from: MsgGroupEventDelegate */
public final class asa implements IMsgGroupEventDelegate {
    public final void executeAction(AmapMessage amapMessage) {
        MessageBoxManager.getInstance().executeAction(amapMessage);
    }

    public final void setRead(AmapMessage amapMessage) {
        MessageBoxManager.getInstance().setRead(amapMessage);
    }

    public final void setSubRead(String str) {
        MessageBoxManager.getInstance().setSubRead(str);
    }

    public final void setCurDispBubbleMsg() {
        MessageBoxManager.getInstance().setCurDispBubbleMsg(null);
    }

    public final void setShowOnMap(AmapMessage amapMessage) {
        MessageBoxManager.getInstance().setShowOnMap(amapMessage);
    }

    public final String getCurHourInterval() {
        return dbf.a();
    }

    public final void clearMainLauncher() {
        fhb fhb = (fhb) a.a.a(fhb.class);
        if (fhb != null) {
            fhb.d().a();
        }
    }
}
