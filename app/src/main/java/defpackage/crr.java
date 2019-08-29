package defpackage;

import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;
import com.autonavi.minimap.R;

/* renamed from: crr reason: default package */
/* compiled from: SaveSuspendViewManager */
public final class crr extends ccx {
    public final void e() {
    }

    public crr(IMapPage iMapPage) {
        super(iMapPage);
    }

    public final void b() {
        View a = this.e.a(false);
        a.setContentDescription("指南针");
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = agn.a(this.b, 8.0f);
        layoutParams.topMargin = agn.a(this.b, 7.0f);
        a(a, layoutParams);
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
                    crr.this.e.b(false).setVisibility(8);
                    crr.this.e.c(false).setVisibility(8);
                    return;
                }
                crr.this.e.b(false).setVisibility(0);
                crr.this.e.c(false).setVisibility(0);
            }
        });
    }

    public final void d() {
        View b = this.e.b(false);
        LayoutParams layoutParams = new LayoutParams(this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size), this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
        layoutParams.rightMargin = agn.a(this.a.getContext(), 4.0f);
        layoutParams.topMargin = agn.a(this.a.getContext(), 3.0f);
        c(b, layoutParams);
        View c = this.e.c(false);
        LayoutParams layoutParams2 = new LayoutParams(this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size), this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
        layoutParams2.rightMargin = agn.a(this.a.getContext(), 4.0f);
        c(c, layoutParams2);
    }

    public final void f() {
        ZoomView l = this.e.l();
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.rightMargin = agn.a(this.b, 4.0f);
        layoutParams.topMargin = agn.a(this.b, 4.0f);
        layoutParams.bottomMargin = agn.a(this.b, 3.0f);
        d(l, layoutParams);
    }
}
