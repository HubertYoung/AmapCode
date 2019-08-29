package defpackage;

import com.autonavi.bundle.amaphome.widget.MapHomePageWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.gps.IGpsMapWidgetDelegate;

/* renamed from: arz reason: default package */
/* compiled from: MapHomeGpsDelegate */
public final class arz implements IGpsMapWidgetDelegate {
    private MapHomePageWidgetManager a;

    public arz(MapHomePageWidgetManager mapHomePageWidgetManager) {
        this.a = mapHomePageWidgetManager;
    }

    public final void doClickBeforeAction() {
        if (this.a != null) {
            this.a.setGpsOverlayProjectCenter();
        }
    }
}
