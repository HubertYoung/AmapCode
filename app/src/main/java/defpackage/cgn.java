package defpackage;

import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;
import com.autonavi.minimap.R;

/* renamed from: cgn reason: default package */
/* compiled from: MeasureSuspendViewManager */
public final class cgn extends ccx {
    public final void e() {
    }

    public cgn(IMapPage iMapPage) {
        super(iMapPage);
    }

    public final void c() {
        View n = this.e.n();
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = agn.a(this.b, 4.0f);
        b(n, layoutParams);
        this.e.a((cdp) new cdp() {
            public final void onFloorChanged(int i, int i2) {
            }

            public final void onFloorWidgetVisibilityChanged(ami ami, boolean z, int i) {
                if (z) {
                    cgn.this.e.b(false).setVisibility(8);
                } else {
                    cgn.this.e.b(false).setVisibility(0);
                }
            }
        });
    }

    public final void d() {
        int i = 0;
        View b = this.e.b(false);
        LayoutParams layoutParams = new LayoutParams(this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size), this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
        int a = agn.a(this.a.getContext(), 4.0f);
        layoutParams.rightMargin = a;
        layoutParams.topMargin = a;
        c(b, layoutParams);
        if (this.c.c().c()) {
            i = 8;
        }
        b.setVisibility(i);
    }

    public final void f() {
        ZoomView l = this.e.l();
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.rightMargin = agn.a(this.b, 4.0f);
        layoutParams.bottomMargin = agn.a(this.b, 4.0f);
        d(l, layoutParams);
    }
}
