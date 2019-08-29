package defpackage;

import android.content.Context;
import android.view.View;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.suspend.refactor.floor.FloorWidgetLayoutWithGuildTip;
import com.autonavi.map.suspend.refactor.floor.FloorWidgetLayoutWithLocationTip;
import com.autonavi.map.suspend.refactor.floor.FloorWidgetView.a;

/* renamed from: cdl reason: default package */
/* compiled from: DefaultFloorWidgetOwner */
public final class cdl implements cdr {
    private cdc a;
    private FloorWidgetLayoutWithGuildTip b;

    public cdl(cdc cdc, FloorWidgetLayoutWithGuildTip floorWidgetLayoutWithGuildTip) {
        this.a = cdc;
        this.b = floorWidgetLayoutWithGuildTip;
    }

    public final Context a() {
        return this.a.a();
    }

    public final bty b() {
        return this.a.b().getMapView();
    }

    public final MapManager c() {
        return this.a.b();
    }

    public final boolean d() {
        return this.a.d().c();
    }

    public final boolean e() {
        return this.a.e() != null && this.a.e().isViewEnable(32768);
    }

    public final boolean f() {
        return this.a.b().getMapView().J() > 0.0f;
    }

    public final FloorWidgetLayoutWithLocationTip g() {
        if (this.b != null) {
            return this.b.getFloorWidgetViewLayout();
        }
        return null;
    }

    public final View h() {
        if (this.b != null) {
            return this.b.getGuideTipView();
        }
        return null;
    }

    public final a i() {
        return this.b;
    }

    public final void j() {
        if (this.a.d() != null) {
            this.a.d().f();
        }
    }

    public final FloorWidgetLayoutWithGuildTip k() {
        return this.b;
    }
}
