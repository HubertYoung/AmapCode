package defpackage;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.refactor.scale.ScaleView;

/* renamed from: ecw reason: default package */
/* compiled from: FootNaviSvHelper */
public final class ecw {
    public ccv a = new ccv(this.b);
    private Context b;
    private ccy c;

    public ecw(IMapPage iMapPage) {
        this.b = iMapPage.getContext();
        this.c = iMapPage.getSuspendWidgetHelper();
        a(62);
    }

    public final void a(int i) {
        ScaleView f = this.c.f();
        if (!(f == null || f.getParent() == null || !(f.getParent() instanceof ViewGroup))) {
            ((ViewGroup) f.getParent()).removeView(f);
        }
        LayoutParams layoutParams = new LayoutParams(-1, agn.a(this.b, 48.0f));
        layoutParams.leftMargin = agn.a(this.b, (float) i);
        layoutParams.bottomMargin = agn.a(this.b, 62.0f);
        this.a.addWidget(f, layoutParams, 7);
    }
}
