package defpackage;

import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn.AutoRemoteWidgetPresenter;

/* renamed from: aqa reason: default package */
/* compiled from: AliCarService */
public final class aqa implements czd {
    public final void a() {
        AutoRemoteWidgetPresenter autoRemoteWidgetPresenter = (AutoRemoteWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.AUTO_REMOTE);
        if (autoRemoteWidgetPresenter != null) {
            autoRemoteWidgetPresenter.updateIconVisible();
        }
    }
}
