package defpackage;

import android.content.Context;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.SuspendViewDefaultTemplate;
import com.autonavi.map.suspend.refactor.scale.ScaleLineView;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;

/* renamed from: efq reason: default package */
/* compiled from: FootRunMapSVManager */
public final class efq {
    public SuspendViewDefaultTemplate a = new SuspendViewDefaultTemplate(this.e);
    public efr b;
    public efs c;
    public MvpImageView d;
    public Context e;
    public int f = agn.a(this.e, 4.0f);
    private ScaleView g;
    private MapManager h;

    public efq(IMapPage iMapPage) {
        this.e = iMapPage.getContext();
        this.h = iMapPage.getMapManager();
        MvpImageView mvpImageView = new MvpImageView(this.e);
        this.b = new efr();
        this.b.a(mvpImageView);
        mvpImageView.setContentDescription("跟随/指北模式");
        LayoutParams a2 = a();
        int i = this.f;
        a2.leftMargin = i;
        a2.bottomMargin = i;
        this.a.addView(mvpImageView, a2, 3);
        ScaleView scaleView = new ScaleView(this.e);
        scaleView.setMapManager(this.h);
        ScaleLineView scaleLineView = scaleView.getScaleLineView();
        if (scaleLineView != null) {
            scaleLineView.mAlignRight = false;
        }
        this.g = scaleView;
        this.g.setContentDescription("比例尺");
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, (int) this.e.getResources().getDimension(R.dimen.my_route_height));
        this.g.setPadding(0, 0, 0, agn.a(this.e, 6.0f));
        layoutParams.leftMargin = agn.a(this.e, 2.0f) + ((int) this.e.getResources().getDimension(R.dimen.map_container_btn_new_size));
        this.a.addView(this.g, layoutParams, 7);
    }

    public final LayoutParams a() {
        int a2 = agn.a(this.e, 48.0f);
        return new LayoutParams(a2, a2);
    }

    public final boolean b() {
        return this.c != null && this.c.b;
    }

    public final void c() {
        if (this.c != null) {
            this.c.b();
        }
    }
}
