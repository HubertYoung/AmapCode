package defpackage;

import android.view.View;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.core.Real3DManager.ActionLogFromEnum;
import com.autonavi.map.core.Real3DManager.ActionLogStateEnum;
import com.autonavi.map.core.view.Real3DSwitchView;

/* renamed from: bsg reason: default package */
/* compiled from: Real3DSwitchPresenter */
public final class bsg {
    public final Real3DSwitchView a;
    public bro b;
    public brt c;
    public final Real3DManager d;
    private final cdb e;
    private bsd f;
    private View g;
    private View h;

    public bsg(Real3DSwitchView real3DSwitchView, cdb cdb, bro bro, brt brt, View view, View view2) {
        this.d = Real3DManager.a();
        this.a = real3DSwitchView;
        this.e = cdb;
        this.b = bro;
        this.c = brt;
        this.g = view;
        this.h = view2;
        this.a.setPresenter(this);
    }

    public bsg(bsd bsd, cde cde, MapManager mapManager) {
        this.f = bsd;
        this.e = cde;
        this.b = mapManager;
        this.d = Real3DManager.a();
        this.a = null;
    }

    public final void a() {
        if (this.d.e && this.d.a(false) && this.d.a(this.b, 0)) {
            this.d.a(this.b);
        }
    }

    public final void b() {
        if (this.d.e && this.d.a(false)) {
            Real3DManager.b(this.b);
        }
    }

    public final void c() {
        if (this.f != null) {
            this.f.d();
        }
    }

    public final void a(boolean z) {
        this.d.c(true);
        this.d.a(this.b);
        if (this.b.getMapView().w() < 17 && !z) {
            this.b.getMapView().f(17.0f);
        }
        if (z) {
            if (this.c != null) {
                this.c.d();
            }
            c();
            Real3DManager.a(this.e, this.b);
        }
        ActionLogFromEnum actionLogFromEnum = ActionLogFromEnum.SWITCH;
        ActionLogStateEnum actionLogStateEnum = ActionLogStateEnum.OPEN;
    }
}
