package defpackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.gps.GPSButton;
import com.autonavi.minimap.R;

/* renamed from: dib reason: default package */
/* compiled from: VoiceTrafficABViewHelper */
public final class dib extends sx {
    private ViewGroup a;

    public dib(IMapPage iMapPage) {
        super(iMapPage);
    }

    public final void b() {
        addWidget(this.g.l(), a(), 6);
        addWidget(this.g.a(), f(), 1);
        a((View) this.g.d());
        a((View) this.g.f());
        this.g.d().setVisibility(0);
        this.a = (ViewGroup) LayoutInflater.from(this.c).inflate(R.layout.route_car_result_map_gps_scale, null);
        ViewGroup viewGroup = this.a;
        GPSButton d = this.g.d();
        int a2 = agn.a(this.c, 48.0f);
        LayoutParams layoutParams = new LayoutParams(a2, a2);
        int a3 = agn.a(this.c, 2.0f);
        layoutParams.leftMargin = a3;
        layoutParams.bottomMargin = a3;
        viewGroup.addView(d, -1, layoutParams);
        this.a.addView(this.g.f(), -1, this.g.g());
        addWidget(this.a, new RelativeLayout.LayoutParams(-1, -2), 3);
    }

    public final void a(View view) {
        if (view != null && view.getParent() != null && (view.getParent() instanceof ViewGroup)) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    public final int c() {
        if (this.g == null || this.g.d() == null) {
            return -1;
        }
        return this.g.d().getState();
    }

    public final void a(int i) {
        if (this.g != null && this.g.d() != null) {
            this.g.d().setState(i);
        }
    }
}
