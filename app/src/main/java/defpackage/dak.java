package defpackage;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.view.MapLayerView;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.SuspendViewCommonTemplate;
import com.autonavi.map.suspend.refactor.gps.GPSButton;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.map.suspend.refactor.zoom.ZoomView;
import com.autonavi.map.suspend.refactor.zoom.ZoomViewPresenter;
import com.autonavi.minimap.R;

/* renamed from: dak reason: default package */
/* compiled from: SuspendWidgetHelperImpl */
public class dak implements ccu {
    cdi a;
    View b;
    LayoutParams c;
    int d;
    View e;
    boolean f;
    ViewGroup g;
    ScaleView h;
    MapLayerView i;
    MvpImageView j;
    ZoomView k;
    private IMapPage l;
    private cdp m;
    private cde n;
    private MapManager o;
    private cdh p;
    private bse q;
    private bsh r;

    public final void a(@NonNull IMapPage iMapPage) {
        this.l = iMapPage;
        this.n = this.l.getSuspendManager();
        this.o = this.l.getMapManager();
    }

    public final View a() {
        return a(true);
    }

    public final View a(boolean z) {
        if (this.a != null) {
            return this.a.getView();
        }
        if (this.n.e() != null) {
            this.p = this.n.e().a(z, this.l.getContext());
        }
        this.a = this.p.a;
        return this.a.getView();
    }

    public final void b() {
        if (this.p != null) {
            this.p.paintCompass();
        }
    }

    public final LinearLayout.LayoutParams c() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.leftMargin = this.l.getContext().getResources().getDimensionPixelSize(R.dimen.compass_margin_left);
        layoutParams.topMargin = this.l.getContext().getResources().getDimensionPixelSize(R.dimen.compass_margin_top);
        return layoutParams;
    }

    public final GPSButton d() {
        return (GPSButton) this.n.d().a();
    }

    public final void a(View view, View view2, LayoutParams layoutParams, int i2) {
        this.b = view;
        this.c = layoutParams;
        this.d = i2;
    }

    public final void a(GPSButton gPSButton) {
        a(gPSButton, false);
    }

    private void a(GPSButton gPSButton, boolean z) {
        boolean a2 = (!(this.l instanceof bid) || ((bid) this.l).getContentView() == null) ? true : a((View) gPSButton, ((bid) this.l).getContentView());
        if (!z || a2) {
            ViewParent parent = gPSButton.getParent();
            if (parent != null && ViewGroup.class.isInstance(parent)) {
                ((ViewGroup) parent).removeView(gPSButton);
            }
        }
    }

    private static boolean a(View view, View view2) {
        if (view == null || view2 == null) {
            return false;
        }
        if (view == view2) {
            return true;
        }
        for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
            if (parent == view2) {
                return true;
            }
        }
        return false;
    }

    public final void a(a aVar) {
        if (this.n != null && this.n.d() != null) {
            this.n.d().a(aVar);
        }
    }

    public final LinearLayout.LayoutParams e() {
        int dimensionPixelSize = this.l.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
        int a2 = agn.a(this.l.getContext(), 4.0f);
        layoutParams.leftMargin = a2;
        layoutParams.bottomMargin = a2;
        return layoutParams;
    }

    public final ScaleView f() {
        if (this.h != null) {
            this.h.setScaleStatus(0);
            return this.h;
        }
        if (this.n.f() != null) {
            this.h = this.n.f().a();
        }
        return this.h;
    }

    public final LinearLayout.LayoutParams g() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, this.l.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
        layoutParams.leftMargin = agn.a(this.l.getContext(), 6.0f);
        return layoutParams;
    }

    public final View a(boolean z, a aVar) {
        if (this.i != null) {
            return this.i;
        }
        this.i = new MapLayerView(this.l.getContext());
        this.i.setContentDescription("图层");
        if (aVar != null) {
            this.q = new bse(this.n, this.o, z, aVar);
        } else {
            this.q = new bse(this.n, this.o, z);
        }
        this.q.a((cem) this.i);
        return this.i;
    }

    public final View b(boolean z) {
        return a(z, (a) null);
    }

    public final LinearLayout.LayoutParams i() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, this.l.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
        layoutParams.rightMargin = this.l.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
        return layoutParams;
    }

    public final View j() {
        return c(true);
    }

    public final View c(boolean z) {
        if (this.j != null) {
            return this.j;
        }
        this.j = new MvpImageView(this.l.getContext());
        this.r = new bsh(this.o, z);
        this.r.a(this.j);
        this.j.setContentDescription("路况");
        return this.j;
    }

    public final void d(boolean z) {
        this.r.a(z);
    }

    public final LinearLayout.LayoutParams k() {
        int dimensionPixelSize = this.l.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.rightMargin = this.l.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
        return layoutParams;
    }

    public final ZoomView l() {
        if (this.k != null) {
            return this.k;
        }
        this.k = new ZoomView(this.l.getContext());
        ZoomViewPresenter zoomViewPresenter = new ZoomViewPresenter(this.l.getContext(), this.n, this.o);
        zoomViewPresenter.a = this.k;
        zoomViewPresenter.b = zoomViewPresenter.a.getZoomInTip();
        zoomViewPresenter.c = zoomViewPresenter.a.getZoomOutTip();
        zoomViewPresenter.e = zoomViewPresenter.a.getZoomInTipText();
        zoomViewPresenter.f = zoomViewPresenter.a.getZoomOutTipText();
        zoomViewPresenter.g = zoomViewPresenter.a.getZoomInBtn();
        zoomViewPresenter.h = zoomViewPresenter.a.getZoomOutBtn();
        zoomViewPresenter.a.setOnZoomClickListener(zoomViewPresenter);
        zoomViewPresenter.a.setTouchListener(zoomViewPresenter.i);
        zoomViewPresenter.a.setTag(R.id.tag_zoom_view_presenter, zoomViewPresenter);
        zoomViewPresenter.b();
        return this.k;
    }

    public final LinearLayout.LayoutParams m() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.rightMargin = this.l.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
        layoutParams.topMargin = agn.a(this.l.getContext(), 4.0f);
        layoutParams.bottomMargin = agn.a(this.l.getContext(), 4.0f);
        return layoutParams;
    }

    public final View n() {
        return e(true);
    }

    public final View e(boolean z) {
        if (this.e != null) {
            return this.e;
        }
        this.e = new RelativeLayout(this.l.getContext());
        this.f = z;
        return this.e;
    }

    public final void a(View view) {
        ViewParent parent = view.getParent();
        if (parent != null && ViewGroup.class.isInstance(parent)) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    public final void b(View view) {
        ViewParent parent = view.getParent();
        if (parent != null && ViewGroup.class.isInstance(parent)) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    @Nullable
    public final ViewGroup o() {
        if (this.g != null) {
            return this.g;
        }
        this.g = new RelativeLayout(this.l.getContext());
        return this.g;
    }

    public final LinearLayout.LayoutParams p() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.leftMargin = this.l.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
        return layoutParams;
    }

    public final void a(cdp cdp) {
        this.m = cdp;
    }

    public final void a(Configuration configuration) {
        if (this.p != null) {
            cdh cdh = this.p;
            boolean z = configuration.orientation == 2;
            if (cdh.a != null && cdh.a.getCompassWidget() != null) {
                if (z) {
                    cdh.a.setCompassLayerTipVisibility(false);
                }
                if (cdh.a.getCompassWidget() != null) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) cdh.a.getCompassWidget().getLayoutParams();
                    int dimension = (int) cdh.b.getResources().getDimension(R.dimen.compass_margin_left);
                    if (z) {
                        layoutParams.setMargins(0, 0, dimension, 0);
                    } else {
                        layoutParams.setMargins(0, 0, 0, 0);
                    }
                    cdh.a.getCompassWidget().setLayoutParams(layoutParams);
                }
            }
        }
    }

    public final void u() {
        if (this.q != null) {
            this.q.b();
        }
    }

    public final View h() {
        return a(true, (a) null);
    }

    public final LinearLayout.LayoutParams q() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.leftMargin = this.l.getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
        return layoutParams;
    }

    public final void r() {
        if (this.m != null) {
            cdn c2 = this.n.c();
            if (c2 != null) {
                c2.a(this.m);
            }
        }
        if (this.b != null) {
            a(d(), false);
            ((SuspendViewCommonTemplate) this.b).addView(d(), this.c, this.d);
        }
        if (this.e != null) {
            ViewGroup viewGroup = (ViewGroup) this.e;
            if (this.n.c() != null) {
                this.n.c().a(viewGroup);
                this.n.c().a(this.f);
            }
        }
        LinearLayout linearLayout = null;
        if (this.n.g() != null) {
            linearLayout = this.n.g().b();
        }
        ViewGroup viewGroup2 = this.g;
        if (linearLayout != null && viewGroup2 != null) {
            if (linearLayout.getParent() == null || linearLayout.getParent() != viewGroup2) {
                b((View) linearLayout);
                if (linearLayout.getParent() == null) {
                    viewGroup2.addView(linearLayout);
                }
            }
        }
    }

    public final void s() {
        if (this.m != null) {
            cdn c2 = this.n.c();
            if (c2 != null) {
                c2.b(this.m);
            }
        }
    }

    public final void t() {
        if (!(this.p == null || this.a == null)) {
            cdh cdh = this.p;
            if (cdh.a != null) {
                cdh.a.setOnClickListener(null);
                cdh.a.getCompassWidget().setAngleListener(null);
                cdh.a = null;
            }
            this.p = null;
            this.a = null;
        }
        a(d(), true);
        a(e(true));
    }
}
