package defpackage;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.view.MapLayerView;
import com.autonavi.map.core.view.RouteLineView;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.suspend.SuspendPartitionView.LayoutParams;
import com.autonavi.map.suspend.SuspendViewContainer;
import com.autonavi.map.suspend.refactor.compass.CompassView;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.maphome.IVerifyUserService;
import com.autonavi.minimap.bundle.maphome.suspend.view.AutoRemoteView;

/* renamed from: dlc reason: default package */
/* compiled from: SuspendViewDefaultPageManager */
public final class dlc implements czc {
    protected IMapPage a;
    protected Context b;
    protected MapManager c;
    public SuspendViewContainer d;
    public bse e;
    public dam f;
    public MapLayerView g;
    public ScaleView h;
    public AutoRemoteView i;
    public CompassView j;
    public Runnable k = new Runnable() {
        public final void run() {
            if (dlc.this.e != null) {
                dlc.this.e.a();
            }
        }
    };
    private cdh l;
    private cyv m;
    private RouteLineView n;
    private int o;

    public dlc(IMapPage iMapPage) {
        IVerifyUserService iVerifyUserService;
        this.a = iMapPage;
        this.b = iMapPage.getContext();
        this.c = iMapPage.getMapManager();
        this.o = this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
        this.d = new SuspendViewContainer(this.b);
        this.j = new CompassView(this.b);
        this.j.setVisibility(8);
        this.l = new cdh(this.a.getContext(), this.a.getMapView(), this.a.getSuspendManager().d());
        this.l.a(this.j);
        this.l.c = new OnClickListener() {
            public final void onClick(View view) {
                LogManager.actionLogV2("P00001", "B006");
            }
        };
        this.j.setContentDescription(null);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.leftMargin = this.b.getResources().getDimensionPixelSize(R.dimen.main_compass_margin_left);
        layoutParams.topMargin = this.b.getResources().getDimensionPixelSize(R.dimen.main_compass_margin_top);
        layoutParams.b = 3.0f;
        this.d.addView(this.j, layoutParams, 1);
        this.i = new AutoRemoteView(this.b);
        this.f = new dam();
        dam dam = this.f;
        AutoRemoteView autoRemoteView = this.i;
        dam.a = autoRemoteView;
        dam.a.setAutoViewOnClickListener(dam);
        dam.a.setTipClickListener(dam);
        dam.a();
        autoRemoteView.setVisibility(8);
        vp vpVar = (vp) a.a.a(vp.class);
        if (vpVar != null) {
            vpVar.a(new vq() {
                public final void onHeadunitLoginStateChanged(int i) {
                    dam.this.a();
                }

                public final void onHeadunitWifiConnectStateChanged(boolean z) {
                    dam.this.a();
                }
            });
        }
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.leftMargin = this.o;
        layoutParams2.b = 4.0f;
        this.d.addView(this.i, layoutParams2, 3);
        int dimension = (int) this.b.getResources().getDimension(R.dimen.my_route_height);
        ScaleView scaleView = new ScaleView(this.b);
        scaleView.setMapManager(this.c);
        if (scaleView.getScaleLineView() != null) {
            scaleView.getScaleLineView().mAlignRight = false;
        }
        this.h = scaleView;
        this.h.setAmapLogoVisibility(true);
        this.h.setContentDescription(null);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, dimension);
        this.h.setPadding(0, 0, 0, agn.a(this.b, 7.0f));
        layoutParams3.leftMargin = this.o + ((int) this.b.getResources().getDimension(R.dimen.map_container_btn_size));
        this.d.addView(this.h, layoutParams3, 7);
        this.n = new RouteLineView(this.b);
        this.m = new cyv();
        cyv cyv = this.m;
        cyv.a = this.n;
        cyv.a.setPathClickListener(cyv);
        LayoutParams layoutParams4 = new LayoutParams(-1, dimension);
        layoutParams4.a = 1;
        layoutParams4.bottomMargin = 0;
        this.d.addView(this.n, layoutParams4, 7);
        cqc cqc = (cqc) ank.a(cqc.class);
        if (cqc != null) {
            if (bno.c) {
                iVerifyUserService = cqc.b();
            } else {
                iVerifyUserService = cqc.a();
            }
            this.n.getContext();
            if (iVerifyUserService.a() == 1) {
                this.n.setOnLongClickListener(new OnLongClickListener() {
                    public final boolean onLongClick(View view) {
                        if (dlc.this.a instanceof bid) {
                            ((bid) dlc.this.a).startPage((String) "amap.basemap.action.verifyuser_page", (PageBundle) null);
                        }
                        return true;
                    }
                });
            }
        }
        this.g = new MapLayerView(this.b);
        this.e = new bse(this.a.getSuspendManager(), this.c);
        this.e.a((cem) this.g);
        this.g.setContentDescription(null);
        LayoutParams layoutParams5 = new LayoutParams(-2, this.b.getResources().getDimensionPixelSize(R.dimen.map_container_btn_size));
        layoutParams5.rightMargin = this.o;
        this.d.addView(this.g, layoutParams5, 4);
    }

    public final void a(int i2) {
        if (this.m != null) {
            cyv cyv = this.m;
            if (cyv.a != null) {
                cyv.a.setVisibility(i2);
            }
        }
    }

    public final void a(View view, ViewGroup.LayoutParams layoutParams) {
        this.d.addView(view, layoutParams, 3);
    }
}
