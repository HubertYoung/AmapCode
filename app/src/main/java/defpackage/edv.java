package defpackage;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.scale.ScaleView;

/* renamed from: edv reason: default package */
/* compiled from: RideNaviSvHelper */
public final class edv {
    public ccv a = new ccv(this.b);
    private Context b;
    private ccy c;

    public edv(IMapPage iMapPage) {
        this.b = iMapPage.getContext();
        this.c = iMapPage.getSuspendWidgetHelper();
        a(58);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.rightMargin = agn.a(this.b, 4.0f);
        layoutParams.bottomMargin = agn.a(this.b, 59.0f);
        this.a.addWidget(this.c.l(), layoutParams, 6);
    }

    public final void a(int i) {
        ScaleView f = this.c.f();
        if (!(f == null || f.getParent() == null || !(f.getParent() instanceof ViewGroup))) {
            ((ViewGroup) f.getParent()).removeView(f);
        }
        LayoutParams layoutParams = new LayoutParams(-1, agn.a(this.b, 48.0f));
        layoutParams.leftMargin = agn.a(this.b, (float) i);
        layoutParams.bottomMargin = agn.a(this.b, 59.0f);
        this.a.addWidget(f, layoutParams, 7);
    }
}
