package defpackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.gps.GPSButton;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;

/* renamed from: qc reason: default package */
/* compiled from: RouteCarBrowserViewHelper */
public final class qc extends sx {
    public OnClickListener a;
    private ViewGroup b;
    private ScaleView h;

    public qc(IMapPage iMapPage) {
        super(iMapPage);
        c();
    }

    public final void b() {
        if (this.g != null) {
            if (this.g.j() != null) {
                this.g.j().setVisibility(8);
            }
            if (this.g.l() != null) {
                this.g.l().setVisibility(8);
            }
            if (this.g.a() != null) {
                this.g.a().setVisibility(8);
            }
            if (e() != null) {
                e().setVisibility(8);
            }
        }
    }

    public final void c() {
        addWidget(this.g.j(), g(), 4);
        addWidget(this.g.l(), a(), 6);
        addWidget(this.g.a(), this.g.c(), 1);
        addWidget(e(), f(), 4);
        a(this.a);
        h();
    }

    private void h() {
        GPSButton d = this.g.d();
        a(d);
        this.b = (ViewGroup) LayoutInflater.from(this.c).inflate(R.layout.route_car_result_map_gps_scale, null);
        if (d != null) {
            d.setVisibility(0);
            this.b.addView(d, -1, i());
        }
        this.h = this.g.f();
        if (this.h != null) {
            a(this.h);
            this.b.addView(this.h, -1, this.g.g());
        }
        addWidget(this.b, new LayoutParams(-1, -2), 3);
    }

    public final void a(View view) {
        if (view != null && view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private LinearLayout.LayoutParams i() {
        int a2 = agn.a(this.c, 44.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(a2, a2);
        int a3 = agn.a(this.c, 4.0f);
        layoutParams.leftMargin = a3;
        layoutParams.bottomMargin = a3;
        return layoutParams;
    }

    public final void d() {
        GPSButton d = this.g.d();
        if (!(d == null || this.b == d.getParent())) {
            a(d);
            this.b.addView(d, 0, i());
        }
        if (this.b != this.h.getParent()) {
            a(this.h);
            this.b.addView(this.h, 1, this.g.g());
        }
    }
}
